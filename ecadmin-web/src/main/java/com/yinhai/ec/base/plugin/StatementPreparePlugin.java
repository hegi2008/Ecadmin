package com.yinhai.ec.base.plugin;  

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ReflectHelper;
  
/**
* @package com.yinhai.ec.base.plugin
* <p>Title: StatementPreparePlugin.java</p>
* <p>Description: Mybatis分页插件 分页拦截器 拦截的4中对象Executor ParameterHandler ResultSetHandler StatementHandler</p>
* 分页拦截器，用于拦截需要进行分页查询的操作，然后对其进行分页处理。 
* 利用拦截器实现Mybatis分页的原理： 
* 要利用JDBC对数据库进行操作就必须要有一个对应的Statement对象，Mybatis在执行qSl语句前就会产生一个包含Sql语句的Statement对象，而且对应的Sql语句 
* 是在Statement之前产生的，所以我们就可以在它生成Statement之前对用来生成Statement的Sql语句下手。在Mybatis中Statement语句是通过RoutingStatementHandler对象的 
* prepare方法生成的。所以利用拦截器实现Mybatis分页的一个思路就是拦截StatementHandler接口的prepare方法，然后在拦截器方法中把Sql语句改成对应的分页查询Sql语句，之后再调用 
* StatementHandler对象的prepare方法，即调用invocation.proceed()。 
* 对于分页而言，在拦截器里面我们还需要做的一个操作就是统计满足当前条件的记录一共有多少，这是通过获取到了原始的Sql语句后，把它改为对应的统计语句再利用Mybatis封装好的参数和设 
* 置参数的功能把Sql语句中的参数进行替换，之后再执行查询记录数的Sql语句进行总记录数的统计。
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: 陈瓜瓜</p>
* @author cjh
* @date 2016-1-12 上午10:14:57
* @version 1.0
 */
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})
public class StatementPreparePlugin implements Interceptor{
	private static String dialect = "";	//数据库方言
	private static String pageSqlId = ""; //mapper.xml中需要拦截的ID(正则匹配)
	private static final Logger logger = LoggerFactory.getLogger(com.yinhai.ec.base.plugin.StatementPreparePlugin.class);
	
	/** 
     * 拦截后要执行的方法 
     */ 
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		PageParam pageParam = null;
		RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
	    StatementHandler delegate = (StatementHandler)ReflectHelper.getValueByFieldName(handler, "delegate");
	    MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");
	    BoundSql boundSql = delegate.getBoundSql();
	    Object parameterObject = boundSql.getParameterObject();
		if (invocation.getTarget() instanceof StatementHandler){
		    if (mappedStatement.getId().matches(pageSqlId)) {
			    if (parameterObject == null) {
					if (logger.isErrorEnabled()) {
						logger.error("mybatis page plugin:parameterObject尚未实例化！");
					}
				}else{
					Connection connection = (Connection) invocation.getArgs()[0];
					String sql = boundSql.getSql();
					String countSql = "select count(1) from ("+sql+") tmp_count";
					PreparedStatement countStmt = connection.prepareStatement(countSql);
					BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(),countSql,boundSql.getParameterMappings(),parameterObject);
					setParameters(countStmt,mappedStatement,countBS,parameterObject);
					ResultSet rs = countStmt.executeQuery();
					long count = 0;
					if (rs.next()) {
						count = rs.getLong(1);
					}
					rs.close();
					countStmt.close();
					if (parameterObject instanceof PageParam) {
						pageParam = (PageParam) parameterObject;
						pageParam.setTotal(count);
						String pageSql = generatePageSql(sql,pageParam);
						ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql); //将分页sql语句反射回BoundSql.
					}
				}
			}
		}
		return invocation.proceed();
	}

	/** 
     * 拦截器对应的封装原始对象的方法 
     */  
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties p) {
		dialect = p.getProperty("dialect");
		if (StringUtils.isEmpty(dialect)) {
			if (logger.isErrorEnabled()) {
				logger.error("mybatis page plugin:dialect property is not found!");
			}
		}
		pageSqlId = p.getProperty("pageSqlId");
		if (StringUtils.isEmpty(pageSqlId)) {
			if (logger.isErrorEnabled()) {
				logger.error("mybatis page plugin:pageSqlId property is not found!");
			}
		}
	}
	
	/**
	  * @package com.yinhai.ec.base.plugin
	  * @method setParameters 方法 
	  * @describe <p>方法说明:对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler</p>
	  * @return void 
	  * @author cjh
	  * @date 2016-1-12
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setParameters(PreparedStatement ps,MappedStatement mappedStatement,BoundSql boundSql,Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null: configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)&& boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException("There was no TypeHandler found for parameter "+ propertyName + " of statement "+ mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}
	
	/**
	  * @package com.yinhai.ec.base.plugin
	  * @method generatePageSql 方法 
	  * @describe <p>方法说明:根据数据库方言，生成特定的分页sql</p>
	  * @return String 
	  * @author cjh
	  * @date 2016-1-12
	 */
	private String generatePageSql(String sql,PageParam page){
		if(page!=null && !StringUtils.isEmpty(dialect)){
			StringBuffer pageSql = new StringBuffer();
			if("mysql".equals(dialect)){
				pageSql.append(sql);
				pageSql.append(" limit "+page.getStart()+","+page.getPageSize());
			}else if("oracle".equals(dialect)){
				pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
				pageSql.append(sql);
				pageSql.append(") tmp_tb where ROWNUM<=");
				pageSql.append(page.getStart()+page.getPageSize());
				pageSql.append(") where row_id>");
				pageSql.append(page.getStart());
			}
			return pageSql.toString();
		}else{
			return sql;
		}
	}
	
}
 