package com.yinhai.ec.common.excel;  

public class DemoExcelBean {
	/** 房源ID */
	private Integer house_id;

	/** 楼盘ID */
	private Integer build_id;
	
	/** 子楼盘ID */
	private Integer sub_build_id;

	/** 房源编号 */
	private String house_code;

	/** 房源名称 */
	@ExcelVOAttribute(name = "(*必填)房源名称", column = "A", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")
	private String house_name;

	/** 房源类型。住宅、商铺、公寓、车位、杂物间、独栋、联排、叠拼、写字楼、复式楼 */
	@ExcelVOAttribute(name = "(*必填)商品房类型", column = "B", isExport = false ,combo={"1-住宅","2-公寓","3-商铺","4-车位","5-杂物间","6-独栋","7-联排","8-叠拼","9-写字楼","10-复式楼"})
	private Integer p_type;

	/** 房源类型。1一手房2二手房3出租房 */
	@ExcelVOAttribute(name = "(*必填)房源类型", column = "C", isExport = false,combo={"1-一手房","2-二手房","3-出租房","4-可租可售"})
	private Integer house_belong;
	
	/** 销售状态。待售、已售、预留、定购 */
	@ExcelVOAttribute(name = "(*必填)销售状态", column = "D", isExport = false,combo = {"1-待售","2-预留","3-订购","4-签约"})
	private String sale_status;
	
	/** 户型。附属表，X室X厅X卫 */
	@ExcelVOAttribute(name = "户型", column = "E", isExport = false,combo={"" +
			"00-单间配套","01-临街商铺","02-住宅底商","06-2室1厅1卫","07-2室2厅1卫","08-2室2厅2卫","09-2室1厅2卫",
			"10-3室1厅1卫","11-3室1厅2卫","12-3室1厅3卫","13-3室2厅1卫","14-3室2厅2卫","15-3室2厅3卫","17-4室2厅1卫","18-4室2厅2卫","19-4室2厅3卫",
			"20-4室3厅2卫","22-5室2厅2卫","23-5室2厅3卫","24-5室2厅4卫","25-5室3厅2卫","26-5室3厅3卫",
			"30-6室2厅2卫","31-6室2厅3卫","32-6室3厅3卫"})
	private String house_type;

	/** 楼栋 */
	@ExcelVOAttribute(name = "楼栋", column = "F", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")
	private String build_no;

	/** 单元 */
	@ExcelVOAttribute(name = "单元", column = "G", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")
	private String unit_no;

	/** 所在楼层 */
	@ExcelVOAttribute(name = "所在楼层", column = "H", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")
	private Integer h_floor;

	/** 房号 */
	@ExcelVOAttribute(name = "房号", column = "I", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")
	private String house_no;
	
	/** 几梯 */
	@ExcelVOAttribute(name = "几梯", column = "J", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")
	private String stair;

	/** 几户 */
	@ExcelVOAttribute(name = "几户", column = "K", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")
	private String door;
	
	/** 总层数 */
	@ExcelVOAttribute(name = "总楼层", column = "L", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")
	private Integer total_floor;

	/** 房间结构。跃层/复式/错层 */
	@ExcelVOAttribute(name = "房间结构", column = "M", isExport = false,combo={"1-平层","2-复式","3-跃层","4-错层","5-商铺"})
	private Integer house_struct;

	/** 预售建筑面积 */
	@ExcelVOAttribute(name = "预售建筑面积", column = "N", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")
	private String pre_cove_area;

	/** 实测建筑面积 */
	@ExcelVOAttribute(name = "实测建筑面积", column = "O", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")
	private String real_cove_area;

	/** 预售套内面积 */
	@ExcelVOAttribute(name = "预售套内面积", column = "P", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")
	private String pre_inside_area;

	/** 实测套内面积 */
	@ExcelVOAttribute(name = "实测套内面积", column = "Q", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")
	private String real_inside_area;

	/** 建筑面积 */
	@ExcelVOAttribute(name = "建筑面积", column = "R", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")
	private String covered_area;

	/** 套内面积 */
	@ExcelVOAttribute(name = "套内面积", column = "S", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")
	private String inside_area;

	/** 建筑单价 */
	@ExcelVOAttribute(name = "建面单价", column = "T", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")
	private String covered_price;

	/** 套内单价 */
	@ExcelVOAttribute(name = "套内单价", column = "U", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")
	private String inside_price;

	/** 计价方式。建筑面积、套内面积、 */
	@ExcelVOAttribute(name = "计价方式", column = "V", isExport = false,combo={"1-建筑面积","2-套内面积"})
	private Integer price_mode;

	/** 清水房总价 */
	@ExcelVOAttribute(name = "清水房总价", column = "W", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")
	private String house_amount;
	
	/** 装修标准。毛胚，精装，简装 */
	@ExcelVOAttribute(name = "装修标准", column = "X", isExport = false,combo={"1-毛胚","2-精装","3-简装"})
	private String decoration;

	/** 装修单价 */
	@ExcelVOAttribute(name = "装修单价", column = "Y", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")
	private String deco_price;

	/** 装修总价 */
	@ExcelVOAttribute(name = "装修总价", column = "Z", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")
	private String deco_amount;

	/** 是否样板房 */
	@ExcelVOAttribute(name = "是否样板房", column = "AA", isExport = false,combo={"0-不是","1-是"})
	private Integer model_house;

	/** 附属部分。无、花园、阳台、夹层 */
	/*@ExcelVOAttribute(name = "附属部分", column = "Y", isExport = false,combo = {"0-无","1-花园","2-阳台","3-夹层"})*/
	private String appendage;

	/** 附属部分面积 */
	/*@ExcelVOAttribute(name = "附属部分面积", column = "Z", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")*/
	private String append_area;

	/** 附属部分单价 */
	/*@ExcelVOAttribute(name = "附属部分单价", column = "AA", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")*/
	private String append_pirce;

	/** 附属部分总价 */
	/*@ExcelVOAttribute(name = "附属部分总价", column = "AB", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")*/
	private String append_amount;

	/** 朝向 */
	//@ExcelVOAttribute(name = "朝向", column = "AG", isExport = false,combo={"1-东","2-西","3-南","4-北"})
	private String h_towards;

	/** 房屋总价 */
	//@ExcelVOAttribute(name = "房屋总价", column = "AF", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")
	private String total_amount;
	
	/** 添加时间 */
	//@ExcelVOAttribute(name = "房屋总价", column = "AF", isExport = false,prompt="请设置表格的单元格格式为文本格式,否则导入失败!")
	private java.sql.Timestamp add_time;

	public DemoExcelBean() {
	}

	public DemoExcelBean(Integer house_id) {
		this.house_id = house_id;
	}

	/**
	 * 设置 house_id 房源ID
	 * @param house_id 房源ID
	 */
	public void setHouse_id(Integer house_id) {
		this.house_id = house_id;
	}

	/**
	 * 获取 house_id 房源ID
	 * @return 房源ID
	 */
	public Integer getHouse_id() {
		return this.house_id;
	}

	/**
	 * 设置 build_id 楼盘ID
	 * @param build_id 楼盘ID
	 */
	public void setBuild_id(Integer build_id) {
		this.build_id = build_id;
	}

	/**
	 * 获取 build_id 楼盘ID
	 * @return 楼盘ID
	 */
	public Integer getBuild_id() {
		return this.build_id;
	}

	/**
	 * 设置 house_code 房源编号
	 * @param house_code 房源编号
	 */
	public void setHouse_code(String house_code) {
		this.house_code = house_code;
	}

	/**
	 * 获取 house_code 房源编号
	 * @return 房源编号
	 */
	public String getHouse_code() {
		return this.house_code;
	}

	/**
	 * 设置 house_name 房源名称
	 * @param house_name 房源名称
	 */
	public void setHouse_name(String house_name) {
		this.house_name = house_name;
	}

	/**
	 * 获取 house_name 房源名称
	 * @return 房源名称
	 */
	public String getHouse_name() {
		return this.house_name;
	}

	/**
	 * 设置 p_type 产品类型。住宅、商铺、公寓、车位、杂物间、独栋、联排、叠拼、写字楼、复式楼
	 * @param p_type 产品类型。住宅、商铺、公寓、车位、杂物间、独栋、联排、叠拼、写字楼、复式楼
	 */
	public void setP_type(Integer p_type) {
		this.p_type = p_type;
	}

	/**
	 * 获取 p_type 产品类型。住宅、商铺、公寓、车位、杂物间、独栋、联排、叠拼、写字楼、复式楼
	 * @return 产品类型。住宅、商铺、公寓、车位、杂物间、独栋、联排、叠拼、写字楼、复式楼
	 */
	public Integer getP_type() {
		return this.p_type;
	}

	/**
	 * 设置 house_type 户型。附属表，X室X厅X卫
	 * @param house_type 户型。附属表，X室X厅X卫
	 */
	public void setHouse_type(String house_type) {
		this.house_type = house_type;
	}

	/**
	 * 获取 house_type 户型。附属表，X室X厅X卫
	 * @return 户型。附属表，X室X厅X卫
	 */
	public String getHouse_type() {
		return this.house_type;
	}

	/**
	 * 设置 build_no 楼栋
	 * @param build_no 楼栋
	 */
	public void setBuild_no(String build_no) {
		this.build_no = build_no;
	}

	/**
	 * 获取 build_no 楼栋
	 * @return 楼栋
	 */
	public String getBuild_no() {
		return this.build_no;
	}

	/**
	 * 设置 unit_no 单元
	 * @param unit_no 单元
	 */
	public void setUnit_no(String unit_no) {
		this.unit_no = unit_no;
	}

	/**
	 * 获取 unit_no 单元
	 * @return 单元
	 */
	public String getUnit_no() {
		return this.unit_no;
	}

	/**
	 * 设置 house_no 房号
	 * @param house_no 房号
	 */
	public void setHouse_no(String house_no) {
		this.house_no = house_no;
	}

	/**
	 * 获取 house_no 房号
	 * @return 房号
	 */
	public String getHouse_no() {
		return this.house_no;
	}

	/**
	 * 设置 stair 几梯
	 * @param stair 几梯
	 */
	public void setStair(String stair) {
		this.stair = stair;
	}

	/**
	 * 获取 stair 几梯
	 * @return 几梯
	 */
	public String getStair() {
		return this.stair;
	}

	/**
	 * 设置 door 几户
	 * @param door 几户
	 */
	public void setDoor(String door) {
		this.door = door;
	}

	/**
	 * 获取 door 几户
	 * @return 几户
	 */
	public String getDoor() {
		return this.door;
	}

	/**
	 * 设置 sale_status 销售状态。待售、已售、预留、定购
	 * @param sale_status 销售状态。待售、已售、预留、定购
	 */
	public void setSale_status(String sale_status) {
		this.sale_status = sale_status;
	}

	/**
	 * 获取 sale_status 销售状态。待售、已售、预留、定购
	 * @return 销售状态。待售、已售、预留、定购
	 */
	public String getSale_status() {
		return this.sale_status;
	}

	/**
	 * 设置 house_struct 房间结构。跃层/复式/错层
	 * @param house_struct 房间结构。跃层/复式/错层
	 */
	public void setHouse_struct(Integer house_struct) {
		this.house_struct = house_struct;
	}

	/**
	 * 获取 house_struct 房间结构。跃层/复式/错层
	 * @return 房间结构。跃层/复式/错层
	 */
	public Integer getHouse_struct() {
		return this.house_struct;
	}

	/**
	 * 设置 pre_cove_area 预售建筑面积
	 * @param pre_cove_area 预售建筑面积
	 */
	public void setPre_cove_area(String pre_cove_area) {
		this.pre_cove_area = pre_cove_area;
	}

	/**
	 * 获取 pre_cove_area 预售建筑面积
	 * @return 预售建筑面积
	 */
	public String getPre_cove_area() {
		return this.pre_cove_area;
	}

	/**
	 * 设置 real_cove_area 实测建筑面积
	 * @param real_cove_area 实测建筑面积
	 */
	public void setReal_cove_area(String real_cove_area) {
		this.real_cove_area = real_cove_area;
	}

	/**
	 * 获取 real_cove_area 实测建筑面积
	 * @return 实测建筑面积
	 */
	public String getReal_cove_area() {
		return this.real_cove_area;
	}

	/**
	 * 设置 pre_inside_area 预售套内面积
	 * @param pre_inside_area 预售套内面积
	 */
	public void setPre_inside_area(String pre_inside_area) {
		this.pre_inside_area = pre_inside_area;
	}

	/**
	 * 获取 pre_inside_area 预售套内面积
	 * @return 预售套内面积
	 */
	public String getPre_inside_area() {
		return this.pre_inside_area;
	}

	/**
	 * 设置 real_inside_area 实测套内面积
	 * @param real_inside_area 实测套内面积
	 */
	public void setReal_inside_area(String real_inside_area) {
		this.real_inside_area = real_inside_area;
	}

	/**
	 * 获取 real_inside_area 实测套内面积
	 * @return 实测套内面积
	 */
	public String getReal_inside_area() {
		return this.real_inside_area;
	}

	/**
	 * 设置 covered_area 建筑面积
	 * @param covered_area 建筑面积
	 */
	public void setCovered_area(String covered_area) {
		this.covered_area = covered_area;
	}

	/**
	 * 获取 covered_area 建筑面积
	 * @return 建筑面积
	 */
	public String getCovered_area() {
		return this.covered_area;
	}

	/**
	 * 设置 inside_area 套内面积
	 * @param inside_area 套内面积
	 */
	public void setInside_area(String inside_area) {
		this.inside_area = inside_area;
	}

	/**
	 * 获取 inside_area 套内面积
	 * @return 套内面积
	 */
	public String getInside_area() {
		return this.inside_area;
	}

	/**
	 * 设置 covered_price 建筑单价
	 * @param covered_price 建筑单价
	 */
	public void setCovered_price(String covered_price) {
		this.covered_price = covered_price;
	}

	/**
	 * 获取 covered_price 建筑单价
	 * @return 建筑单价
	 */
	public String getCovered_price() {
		return this.covered_price;
	}

	/**
	 * 设置 inside_price 套内单价
	 * @param inside_price 套内单价
	 */
	public void setInside_price(String inside_price) {
		this.inside_price = inside_price;
	}

	/**
	 * 获取 inside_price 套内单价
	 * @return 套内单价
	 */
	public String getInside_price() {
		return this.inside_price;
	}

	/**
	 * 设置 price_mode 计价方式。建筑面积、套内面积、
	 * @param price_mode 计价方式。建筑面积、套内面积、
	 */
	public void setPrice_mode(Integer price_mode) {
		this.price_mode = price_mode;
	}

	/**
	 * 获取 price_mode 计价方式。建筑面积、套内面积、
	 * @return 计价方式。建筑面积、套内面积、
	 */
	public Integer getPrice_mode() {
		return this.price_mode;
	}

	/**
	 * 设置 house_amount 房屋总价
	 * @param house_amount 房屋总价
	 */
	public void setHouse_amount(String house_amount) {
		this.house_amount = house_amount;
	}

	/**
	 * 获取 house_amount 房屋总价
	 * @return 房屋总价
	 */
	public String getHouse_amount() {
		return this.house_amount;
	}

	/**
	 * 设置 decoration 装修标准。毛胚，精装，简装
	 * @param decoration 装修标准。毛胚，精装，简装
	 */
	public void setDecoration(String decoration) {
		this.decoration = decoration;
	}

	/**
	 * 获取 decoration 装修标准。毛胚，精装，简装
	 * @return 装修标准。毛胚，精装，简装
	 */
	public String getDecoration() {
		return this.decoration;
	}

	/**
	 * 设置 deco_price 装修单价
	 * @param deco_price 装修单价
	 */
	public void setDeco_price(String deco_price) {
		this.deco_price = deco_price;
	}

	/**
	 * 获取 deco_price 装修单价
	 * @return 装修单价
	 */
	public String getDeco_price() {
		return this.deco_price;
	}

	/**
	 * 设置 deco_amount 装修总价
	 * @param deco_amount 装修总价
	 */
	public void setDeco_amount(String deco_amount) {
		this.deco_amount = deco_amount;
	}

	/**
	 * 获取 deco_amount 装修总价
	 * @return 装修总价
	 */
	public String getDeco_amount() {
		return this.deco_amount;
	}

	/**
	 * 设置 model_house 是否样板房
	 * @param model_house 是否样板房
	 */
	public void setModel_house(Integer model_house) {
		this.model_house = model_house;
	}

	/**
	 * 获取 model_house 是否样板房
	 * @return 是否样板房
	 */
	public Integer getModel_house() {
		return this.model_house;
	}

	/**
	 * 设置 appendage 附属部分。无、花园、阳台、夹层
	 * @param appendage 附属部分。无、花园、阳台、夹层
	 */
	public void setAppendage(String appendage) {
		this.appendage = appendage;
	}

	/**
	 * 获取 appendage 附属部分。无、花园、阳台、夹层
	 * @return 附属部分。无、花园、阳台、夹层
	 */
	public String getAppendage() {
		return this.appendage;
	}

	/**
	 * 设置 append_area 附属部分面积
	 * @param append_area 附属部分面积
	 */
	public void setAppend_area(String append_area) {
		this.append_area = append_area;
	}

	/**
	 * 获取 append_area 附属部分面积
	 * @return 附属部分面积
	 */
	public String getAppend_area() {
		return this.append_area;
	}

	/**
	 * 设置 append_pirce 附属部分单价
	 * @param append_pirce 附属部分单价
	 */
	public void setAppend_pirce(String append_pirce) {
		this.append_pirce = append_pirce;
	}

	/**
	 * 获取 append_pirce 附属部分单价
	 * @return 附属部分单价
	 */
	public String getAppend_pirce() {
		return this.append_pirce;
	}

	/**
	 * 设置 append_amount 附属部分总价
	 * @param append_amount 附属部分总价
	 */
	public void setAppend_amount(String append_amount) {
		this.append_amount = append_amount;
	}

	/**
	 * 获取 append_amount 附属部分总价
	 * @return 附属部分总价
	 */
	public String getAppend_amount() {
		return this.append_amount;
	}

	/**
	 * 设置 h_floor 所在楼层
	 * @param h_floor 所在楼层
	 */
	public void setH_floor(Integer h_floor) {
		this.h_floor = h_floor;
	}

	/**
	 * 获取 h_floor 所在楼层
	 * @return 所在楼层
	 */
	public Integer getH_floor() {
		return this.h_floor;
	}

	/**
	 * 设置 total_floor 总层数
	 * @param total_floor 总层数
	 */
	public void setTotal_floor(Integer total_floor) {
		this.total_floor = total_floor;
	}

	/**
	 * 获取 total_floor 总层数
	 * @return 总层数
	 */
	public Integer getTotal_floor() {
		return this.total_floor;
	}

	/**
	 * 设置 h_towards 朝向
	 * @param h_towards 朝向
	 */
	public void setH_towards(String h_towards) {
		this.h_towards = h_towards;
	}

	/**
	 * 获取 h_towards 朝向
	 * @return 朝向
	 */
	public String getH_towards() {
		return this.h_towards;
	}

	/**
	 * 设置 house_belong 房源类型。1一手房2二手房3出租房
	 * @param house_belong 房源类型。1一手房2二手房3出租房
	 */
	public void setHouse_belong(Integer house_belong) {
		this.house_belong = house_belong;
	}

	/**
	 * 获取 house_belong 房源类型。1一手房2二手房3出租房
	 * @return 房源类型。1一手房2二手房3出租房
	 */
	public Integer getHouse_belong() {
		return this.house_belong;
	}

	public String getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	
	public java.sql.Timestamp getAdd_time() {
		return add_time;
	}

	public void setAdd_time(java.sql.Timestamp add_time) {
		this.add_time = add_time;
	}
	
	public Integer getSub_build_id() {
		return sub_build_id;
	}

	public void setSub_build_id(Integer sub_build_id) {
		this.sub_build_id = sub_build_id;
	}
}
 