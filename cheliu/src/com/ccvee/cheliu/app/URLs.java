package com.ccvee.cheliu.app;
/**
 * 
 * @author rongzhi.Yang
 * @data   2016-5-23  下午5:21:46
 */
public class URLs {
	public final static String  BASE_ADDR="api.51zlhq.com";//"hlh.cktd.net:8787";//"192.168.0.115:8787";//"hlh.cktd.net:8787";
	public final static String  BASE_WEB_ADDR="hlh.cktd.net";
	public final static String  BASE_HTTP="http://";
	
	public static String IMAGE_HOST = "http://118.123.13.224/imagecache";
	public static final String APP_WEB = BASE_HTTP+"m.51zlhq.com/post/";
	
	public final static String CONDITION = BASE_HTTP+BASE_ADDR+"/api/work/options";
	public final static String MAIN_LINGGAN = BASE_HTTP+BASE_ADDR+"/api/work";
	public final static String MAIN_LINGGAN_SEARCH = BASE_HTTP+BASE_ADDR+"/api/work/search";
	public final static String MAIN_SEARC_HOT = BASE_HTTP+BASE_ADDR+"/api/work/topn";

	public final static String MAIN_FAXIAN = BASE_HTTP+BASE_ADDR+"/api/team";
	public final static String MAIN_FAXIAN_CONDITION = BASE_HTTP+BASE_ADDR+"/api/team/options";
	
	public final static String MAIN_TANDIAN = BASE_HTTP+BASE_ADDR+"/v1/mes/cashindex";
	
	public final static String USER_REGISTURE_VCODE = BASE_HTTP+BASE_ADDR+"/api/0/member/send_valid_sms";
	public final static String USER_REGISTURE_SUBMIT = BASE_HTTP+BASE_ADDR+"/api/0/member/registerByPhone";
	public final static String USER_FORGET_VCODE = BASE_HTTP+BASE_ADDR+"/api/member/send_findpassword_sms";
	public final static String USER_FORGET_SUBMIT = BASE_HTTP+BASE_ADDR+"/api/0/member/resetPasswordBySMS";

	public final static String USER_COMMENT_DIANPING = BASE_HTTP+BASE_ADDR+"/api/comment/team/me";
	public final static String USER_COMMENT_PINGLUN = BASE_HTTP+BASE_ADDR+"/api/comment/work/me";

	public final static String SHARE_WORK = BASE_HTTP+BASE_ADDR+"/api/share/work";
	public final static String SHARE_TEAM = BASE_HTTP+BASE_ADDR+"/api/share/team";
	public final static String SHARE_SHOWSHOP = BASE_HTTP+BASE_ADDR+"/api/share/showshop";
	public final static String SHARE_APP = BASE_HTTP+BASE_ADDR+"/api/share/app";
	
	public final static String COMMENT_WORK = BASE_HTTP+BASE_ADDR+"/api/comment/work/create";
	public final static String COMMENT_TEAM = BASE_HTTP+BASE_ADDR+"/api/comment/team/create";
	public final static String COMMENT_SHOWSHOP = BASE_HTTP+BASE_ADDR+"/api/comment/showshop/create";
	
	public final static String USER_COMMENT_WORK = BASE_HTTP+BASE_ADDR+"/api/collect/work/me";
	public final static String USER_COMMENT_TEAM = BASE_HTTP+BASE_ADDR+"/api/collect/team/me";
	public final static String COLLECT_WORK = BASE_HTTP+BASE_ADDR+"/api/collect/work/put";
	public final static String COLLECT_TEAM = BASE_HTTP+BASE_ADDR+"/api/collect/team/put";
	public final static String COLLECT_CANCEL_WORK = BASE_HTTP+BASE_ADDR+"/api/collect/work/cancel";
	public final static String COLLECT_CANCEL_TEAM = BASE_HTTP+BASE_ADDR+"/api/collect/team/cancel";

	public final static String USER_INFO = BASE_HTTP+BASE_ADDR+"/api/0/member/me";
	public final static String USER_INFO_IMAGE = BASE_HTTP+BASE_ADDR+"/api/member/modify/logo_url";
	public final static String USER_INFO_NAME = BASE_HTTP+BASE_ADDR+"/api/member/modify/name";
	public final static String USER_INFO_SIGN = BASE_HTTP+BASE_ADDR+"/api/member/modify/description";
	public final static String USER_INFO_LOCATE = BASE_HTTP+BASE_ADDR+"/api/member/modify/locate";
	public final static String USER_INFO_PWD = BASE_HTTP+BASE_ADDR+"/api/member/modify/password";

	public final static String WORK_DETAIL = BASE_HTTP+BASE_ADDR+"/api/work/detail";
	public final static String TEAM_DETAIL = BASE_HTTP+BASE_ADDR+"/api/team/item";
	
	public final static String FILE_UPLOAD = BASE_HTTP+BASE_ADDR+"/api/runtimefile/create";

	public final static String OPEN_CITY = BASE_HTTP+BASE_ADDR+"/api/work/city";
	
	public final static String APP_VERESION = BASE_HTTP+BASE_ADDR+"/api/0/app/config";
	
	public final static String APP_HELP = "http://blog.likewed.com/";
	
	public final static String USER_THIRD_LOGIN = BASE_HTTP+BASE_ADDR+"/api/auth/oauth_login";
	
	public final static String MAIN_FAXIAN_SEARCH = BASE_HTTP+BASE_ADDR+"/v1/activities/financiallist";
	public final static String MAIN_WEIXIN_HONGBAO = BASE_HTTP+BASE_ADDR+"/v1/activities/weixinhblist";
	public final static String MAIN_ZHIFUB_HONGBAO = BASE_HTTP+BASE_ADDR+"/v1/activities/zhihubaohblist";
	public final static String MAIN_HUAFEI_HONGBAO = BASE_HTTP+BASE_ADDR+"/v1/activities/huafeilist";
	public final static String MAIN_HUODONG_LIST_WEIXIN = "/v1/activities/weixinhblis";
	public final static String USER_LOGIN_SUBMIT = BASE_HTTP+BASE_ADDR+"/api/0/member/login";
	public final static String MAIN_ZQDT = BASE_HTTP+BASE_ADDR+"/api/0/app/index";
	public final static String USER_BING_PHONE = BASE_HTTP+BASE_ADDR+"/api/0/member/bind_phone";
	public final static String MAIN_QIANDAO = BASE_HTTP+BASE_ADDR+"/api/0/gold/APP_DAY_SIGN/direct_earn";
	public final static String MAIN_TUIJIAN = BASE_HTTP+BASE_ADDR+"/v1/activities/recommendlist";
	public final static String USER_POINT_ADD = BASE_HTTP+BASE_ADDR+"/v1/users/downloadcoin";
	public final static String MAIN_JINBI_HISTORY = BASE_HTTP+BASE_ADDR+"/api/0/gold/history";
	public final static String MAIN_TIXIAN_HISTORY = BASE_HTTP+BASE_ADDR+"/api/0/gold/cash_history";
	public final static String MAIN_USER_ZFB = BASE_HTTP+BASE_ADDR+"/v1/cashes/zfbinfo";
	public final static String MAIN_USER_TIXIAN = BASE_HTTP+BASE_ADDR+"/api/0/gold/applyfees";
	public final static String MAIN_USER_BIND_ZFB = BASE_HTTP+BASE_ADDR+"/api/0/member/alipay_auth";
	public final static String MAIN_ADS_LIST = BASE_HTTP+BASE_ADDR+"/v1/activities/adlist";
	public final static String MAIN_HOW_EARN = "http://mp.weixin.qq.com/s?__biz=MzA5MzA4Mjg2MQ==&mid=400757417&idx=2&sn=6a4dc1b72d37a2ee232a1a91cfb6f299#rd";
	public final static String MAIN_LIULIANG_HONGBAO = BASE_HTTP+BASE_ADDR+"/v1/activities/liulianglist";
	public final static String USER_LOGOUT = BASE_HTTP+BASE_ADDR+"/api/0/member/logout";
	public static final String USER_FINDPASS_VCODE = BASE_HTTP+BASE_ADDR+"/api/0/member/send_findpassword_sms";

	public static final String MAIN_ARTICLE = BASE_HTTP+BASE_ADDR+"/api/0/article/list";
	public static final String USER_BIND_VCODE = BASE_HTTP+BASE_ADDR+"/api/0/member/send_bindphone_sms";
	public static final String HUODONG_DETAIL= BASE_HTTP+BASE_ADDR+"/api/0/article/detail";
	public static final String HUODONG_GETTOKEN= BASE_HTTP+BASE_ADDR+"/api/0/app/btoken";
	public static final String MAIN_FANXIAN_HISTORY = BASE_HTTP+BASE_ADDR+"/api/0/gold/activity_history";
	public static final String USER_ACTIVITY_APPLY = BASE_HTTP+BASE_ADDR+"/api/0/gold/apply_activity_fees";
	
	public static final String QIANDAO_TUIJIAN = BASE_HTTP + BASE_ADDR + "/api/0/app/recommends";//";//http://api.51zlhq.com/api/0/app/recommends
}
