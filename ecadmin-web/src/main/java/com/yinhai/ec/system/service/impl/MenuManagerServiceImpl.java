package com.yinhai.ec.system.service.impl;  

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.yinhai.ec.base.exception.BaseUpdateException;
import com.yinhai.ec.base.service.impl.BaseServiceImpl;
import com.yinhai.ec.base.util.HYConst;
import com.yinhai.ec.base.util.PageParam;
import com.yinhai.ec.base.util.ResultBean;
import com.yinhai.ec.common.domain.MenuDomain;
import com.yinhai.ec.common.domain.UserDomain;
import com.yinhai.ec.system.service.MenuManagerService;
  
@Service
@Transactional
@SuppressWarnings("rawtypes")
public class MenuManagerServiceImpl extends BaseServiceImpl implements MenuManagerService{

	@Override
	public void queryMenuList(PageParam pageParam) throws Exception {
		List list = sqlSession.selectList("hy.menu.manager.queryMenuList");
		pageParam.setTotal(Long.valueOf(list.size()));
		pageParam.setList(list);
	}

	@Override
	public void saveMenu(Map<String, Object> param, UserDomain user,
			ResultBean bean) throws Exception {
		bean.setError(true);
		if (param.get("menu_name") == null || "".equals(param.get("menu_name").toString())) {
			bean.setError_msg("菜单名称不能空");
		}
		if (StringUtils.isEmpty(bean.getError_msg())) {
			bean.setError(false);
			MenuDomain menu = new MenuDomain();
			menu.setMenuName(param.get("menu_name").toString());
			if (param.get("menu_url") != null && !"".equals(param.get("menu_url").toString())) {
				menu.setMenuUrl(param.get("menu_url").toString());
			}
			if (param.get("menu_icon") != null && !"".equals(param.get("menu_icon").toString())) {
				menu.setMenuIcon(param.get("menu_icon").toString());
			}
			Object menu_id = param.get("menu_id");
			if (menu_id == null || Integer.valueOf(menu_id.toString()) < 0) {
				// 新增
				menu.setMenuCreater(user.getUserId());
				Object _parentId = param.get("_parentId");
				if (_parentId == null || Integer.valueOf(_parentId.toString()) == 0) {
					menu.setParentId(HYConst.STATUS_NO);// 0 顶级菜单
				}else{
					menu.setParentId(Integer.valueOf((String)_parentId));
				}
				sqlSession.insert("hy.menu.manager.insertMenu", menu);
				bean.setSuccess_msg("新增菜单成功");
			}else{
				// 编辑
				menu.setMenuId(Integer.valueOf((String)menu_id));
				int count = sqlSession.update("hy.menu.manager.updateMenuByMenuId", menu);
				if (count != 1) {
					throw new BaseUpdateException(count,"更新菜单时出错");
				}
				bean.setSuccess_msg("菜单保存成功");
			}
		}
	}

	@Override
	public void deleteMenu(PageParam pageParam, ResultBean bean)
			throws Exception {
		if (pageParam.get("menu_id") == null || "".equals(pageParam.get("menu_id").toString())) {
			bean.setError(true);
			bean.setError_msg("删除失败,未获取到需要删除的菜单");
		}
		if (StringUtils.isEmpty(bean.getError_msg())) {
			bean.setError(false);
			MenuDomain menu = new MenuDomain();
			menu.setMenuId(Integer.valueOf(pageParam.getString("menu_id").toString()));
			menu.setMenuStatus(HYConst.STATUS_NO);
			int count = sqlSession.update("hy.menu.manager.updateMenuByMenuId", menu);
			if (count != 1) {
				throw new BaseUpdateException(count,"更新菜单时出错");
			}
			deleteMenuAuthorityByMenuid(menu);
			removeChildMenu(menu);
			bean.setSuccess_msg("删除菜单成功");
		}
	}
	
	/**
	  * @package com.yinhai.ec.system.service.impl
	  * @method deleteMenuAuthorityByMenuid 方法 
	  * @describe <p>方法说明:删除菜单时 要删除权限中的菜单信息</p>
	  * @return void 
	  * @author cjh
	  * @date 2016年3月28日 下午1:27:09
	 */
	private void deleteMenuAuthorityByMenuid(MenuDomain menu) {
		sqlSession.delete("hy.menu.manager.deleteMenuAuthorityByMenuid", menu);
	}

	/**
	  * @package com.yinhai.ec.system.service.impl
	  * @method removeChildMenu 方法 
	  * @describe <p>方法说明:迭代删除子菜单</p>
	  * @return void 
	  * @author cjh
	  * @date 2016-1-20
	 */
	private void removeChildMenu(MenuDomain menu) throws Exception {
		List<MenuDomain> children = sqlSession.selectList("hy.menu.manager.findChildrenMenu", menu);
		if (!CollectionUtils.isEmpty(children)) {
			for (int i = 0; i < children.size(); i++) {
				MenuDomain mm = children.get(i);
				mm.setMenuStatus(HYConst.STATUS_NO);
				int count = sqlSession.update("hy.menu.manager.updateMenuByMenuId", mm);
				if (count != 1) {
					throw new BaseUpdateException(count,"更新菜单时出错");
				}
				deleteMenuAuthorityByMenuid(mm);
				removeChildMenu(mm);
			}
		}
	}
}
 