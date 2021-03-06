package com.dusto.mobile.common.constant;

public class SAPContant {
	public static final String SAP_FUNCTION_RES_SUCCESS = "S";
	public static final String SAP_FUNCTION_RES_FAILED = "F";

	/** 入库绑定 **/
	public static final String SAP_FUNCTION_BINDING = "ZWM006";

	/** 入库取消绑定 **/
	public static final String SAP_FUNCTION_UNBINDING = "ZWM007";

	/** 托盘上架 **/
	public static final String SAP_FUNCTION_GROUNDING = "ZWM021A";

	/** 拆箱 **/
	public static final String SAP_FUNCTION_DEVANNING = "ZWM033";

	/** 获取推荐仓位 **/
	public static final String SAP_FUNCTION_POSITION = "ZWM021";

	/** 主数据同步 **/
	public static final String SAP_FUNCTION_MASTERDATA = "ZWM999";
	public static final String SAP_FUNCTION_MASTERDATA_CONFIRM = "ZWM999A";

	/** 库存查询 **/
	public static final String SAP_FUNCTION_STOCKCHECK = "ZWM020";
	public static final String SAP_FUNCTION_STOCKCHECK_CDC_A = "ZWM038A";
	public static final String SAP_FUNCTION_STOCKCHECK_CDC_B = "ZWM038B";
			

	/** 装箱 **/
	public static final String SAP_FUNCTION_PACKED = "ZWM031";
	public static final String SAP_FUNCTION_CHECK_PACKED = "ZWM032A";
	public static final String SAP_FUNCTION_CONFIRM_PACKED = "ZWM032B";
	public static final String SAP_FUNCTION_CHECK_DELIVERY = "ZWM032C";
	public static final String SAP_FUNCTION_CONFIRM_DELIVERY = "ZWM032D";
	
	/** 库存盘点 **/
	public static final String SAP_FUNCTION_GETTASKNUMB = "ZWM026";
	public static final String SAP_FUNCTION_INVENTORY = "ZWM026A";
	
	/** 移库 **/
	public static final String SAP_FUNCTION_TRANSFERTP = "ZWM023A";
	public static final String SAP_FUNCTION_TRANSFERXH = "ZWM023B";
	public static final String SAP_FUNCTION_TRANSFERSM = "ZWM023C";
	
	/** CDC拣货 **/
	public static final String SAP_FUNCTION_CDC_PICK = "ZWM011";
	/** CDC拣货到暂存区 **/
	public static final String SAP_FUNCTION_CDC_PICK_TO_TEMP = "ZWM011A";
	/** CDC暂存区到发货区 **/
	public static final String SAP_FUNCTION_CDC_PICK_TO_DELIVERY = "ZWM011B";
	
	/** 整托盘移库 CDC&    RDC**/
	public static final String SAP_FUNCTION_TOTAL_TRAY_MOVE= "ZWM023A";
	/** 托盘区理货 CDC **/
    public static final String SAP_FUNCTION_TRAY_TALLY = "ZWM023B";
    /** 托盘区理货 整托 CDC **/
    public static final String SAP_FUNCTION_TRAY_TALLY_TOTAL = "ZWM023B1";
    /** 非托盘区理货 RDC  **/
    public static final String SAP_FUNCTION_NONE_TRAY_TALLY = "ZWM023C";
    
    /** 单数据采集  **/
    public static final String SAP_FUNCTION_COLLECTION_SINGLE = "ZWM001";
    /** 双数据采集  **/
    public static final String SAP_FUNCTION_COLLECTION_GROUP = "ZWM002";
	
    /** RDC拣货 **/
	public static final String SAP_FUNCTION_RDC_PICK_GET = "ZWM029A";
	/** RDC拣货确认 **/
	public static final String SAP_FUNCTION_RDC_PICK_COMMIT = "ZWM029B";
	/** RDC分货 **/
	public static final String SAP_FUNCTION_RDC_DISTRIBUTE = "ZWM029C";
	/** RDC分货确认 **/
	public static final String SAP_FUNCTION_RDC_DISTRIBUTE_COMMIT = "ZWM029D";
}
