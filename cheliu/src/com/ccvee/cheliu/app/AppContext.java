package com.ccvee.cheliu.app;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.apache.http.Header;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.ccvee.cheliu.BuildConfig;
import com.ccvee.cheliu.app.bean.Update;
import com.ccvee.cheliu.app.service.HttpService;
import com.ccvee.cheliu.app.utils.BaseTools;
import com.ccvee.cheliu.app.utils.MethodsCompat;
import com.ccvee.cheliu.app.utils.StringUtil;
import com.ccvee.cheliu.app.utils.UIUtil;
import com.loopj.android.http.TextHttpResponseHandler;


/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 * @author rongzhi.Yang
 * @date   2016-5-23  下午5:16:48
 */
public class AppContext extends Application{
	public static final int NETTYPE_WIFI = 0x01;
	public static final int NETTYPE_CMWAP = 0x02;
	public static final int NETTYPE_CMNET = 0x03;
	
	public static final int HTTP_TYPE_GET = 0x11;
	public static final int HTTP_TYPE_POST = 0x12;
	
	private boolean login = false;	//登录状态
	private String loginUid;	//登录用户的id
	
	public String cityId="";
	public String cityName="全国";
	public String cityCode="";
	
	@SuppressLint("HandlerLeak")
	public static Handler showMsg = new Handler(){
		public void handleMessage(Message msg) {
			UIUtil.ToastMessage((Context) msg.getData().getSerializable("context"), msg.getData().getString("msg"));
		}		
	};
	
	@Override
	public void onCreate() {
		super.onCreate();
		//初始化百度地图   注意该方法要再setContentView方法之前实现   
        SDKInitializer.initialize(getApplicationContext()); 
        //注册App异常崩溃处理器
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());
   	}
	
//	public void showShare(final String url) {
//		HttpService.getByText(url, null, new TextHttpResponseHandler() {
//			@Override
//			public void onSuccess(int arg0, Header[] arg1, String arg) {
//				try {
//					final JSONObject result = new JSONObject(arg);
//					if(result.getInt("status")==0){
//						ImageLoader.getInstance().loadImage(BaseTools.getFrom("data.image",result), new ImageLoadingListener() {
//							@Override
//							public void onLoadingStarted(String imageUri, View view) {}
//							@Override
//							public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//								File images = StorageUtils.getOwnCacheDirectory(AppContext.this, AppConfig.APP_CAMERA);
//								String fp = images.getAbsolutePath()+File.separator+"fx_error.jpg";
//								UIUtil.closeLoadingDialog();
//								final OnekeyShare oks = new OnekeyShare();
//								// 主题
//								oks.setTheme(OnekeyShareTheme.CLASSIC);
//								// 令编辑页面显示为Dialog模式
//								oks.setDialogMode();
//								// 关闭sso授权
//								oks.disableSSOWhenAuthorize();
//								// 分享时Notification的图标和文字
//								// oks.setNotification(R.drawable.ic_launcher,
//								// getString(R.string.app_name));
//								// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//								oks.setTitle(BaseTools.getFrom("data.title", result));//.getJsonAsString(result.get("data"),"title"));
//								// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//								oks.setTitleUrl(BaseTools.getFrom("data.url", result));
//								// oks.setImageUrl(DataUtil.getJsonAsString(result.get("data"),"image"));
//								// url仅在微信（包括好友和朋友圈）中使用
//								oks.setUrl(BaseTools.getFrom("data.url", result));
//								// text是分享文本，所有平台都需要这个字.段
//								oks.setText(BaseTools.getFrom("data.content", result));
//								// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//								 oks.setImagePath(fp);//确保SDcard下面存在此张图片
//								// site是分享此内容的网站名称，仅在QQ空间使用
//								oks.setSite(getString(R.string.app_name));
//								// siteUrl是分享此内容的网站地址，仅在QQ空间使用
//								oks.setSiteUrl(BaseTools.getFrom("data.url", result));
//								// 设置kakaoTalk分享链接时，点击分享信息时，如果应用不存在，跳转到应用的下载地址
//								oks.setInstallUrl("http://www.likewed.com");
//								// 设置kakaoTalk分享链接时，点击分享信息时，如果应用存在，打开相应的app
//								oks.setExecuteUrl("kakao48d3f524e4a636b08d81b3ceb50f1003://kakaolink");
//								oks.setOnShareButtonClickListener(new OnShareButtonClickListener() {
//									@Override
//									public void onClick(View v, List<Object> checkPlatforms) {
//										if(v.getTag() instanceof SinaWeibo&&!StringUtil.isEmpty(BaseTools.getFrom("data.content", result))&&BaseTools.getFrom("data.content", result).length()>140){
//											UIUtil.ToastMessage(AppContext.this, "新浪微博分享文字不能140个,请编辑!");
//										}
//									}
//								});
//								// 启动分享GUI
//								oks.show(AppContext.this);
//							}
//							@Override
//							public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//								File images = StorageUtils.getOwnCacheDirectory(AppContext.this, AppConfig.APP_CAMERA);
//								String fp = images.getAbsolutePath()+File.separator+System.currentTimeMillis()+"fx.jpg";
//								try {
//									ImageUtils.saveImageToSD(AppContext.this, fp, loadedImage, 100);
//								} catch (IOException e) {
//									e.printStackTrace();
//								}
//								UIUtil.closeLoadingDialog();
//								final OnekeyShare oks = new OnekeyShare();
//								// 主题
//								oks.setTheme(OnekeyShareTheme.CLASSIC);
//								// 令编辑页面显示为Dialog模式
//								oks.setDialogMode();
//								// 关闭sso授权
//								oks.disableSSOWhenAuthorize();
//								// 分享时Notification的图标和文字
//								// oks.setNotification(R.drawable.ic_launcher,
//								// getString(R.string.app_name));
//								// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//								oks.setTitle(BaseTools.getFrom("data.title", result));//.getJsonAsString(result.get("data"),"title"));
//								// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//								oks.setTitleUrl(BaseTools.getFrom("data.url", result));
//								// oks.setImageUrl(DataUtil.getJsonAsString(result.get("data"),"image"));
//								// url仅在微信（包括好友和朋友圈）中使用
//								oks.setUrl(BaseTools.getFrom("data.url", result));
//								// text是分享文本，所有平台都需要这个字.段
//								oks.setText(BaseTools.getFrom("data.content", result));
//								// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//								 oks.setImagePath(fp);//确保SDcard下面存在此张图片
//								// site是分享此内容的网站名称，仅在QQ空间使用
//								oks.setSite(getString(R.string.app_name));
//								// siteUrl是分享此内容的网站地址，仅在QQ空间使用
//								oks.setSiteUrl(BaseTools.getFrom("data.url", result));
//								// 设置kakaoTalk分享链接时，点击分享信息时，如果应用不存在，跳转到应用的下载地址
//								oks.setInstallUrl("http://www.likewed.com");
//								// 设置kakaoTalk分享链接时，点击分享信息时，如果应用存在，打开相应的app
//								oks.setExecuteUrl("kakao48d3f524e4a636b08d81b3ceb50f1003://kakaolink");
//								oks.setOnShareButtonClickListener(new OnShareButtonClickListener() {
//									@Override
//									public void onClick(View v, List<Object> checkPlatforms) {
//										if(v.getTag() instanceof SinaWeibo&&!StringUtil.isEmpty(BaseTools.getFrom("data.content", result))&&BaseTools.getFrom("data.content", result).length()>140){
//											UIUtil.ToastMessage(AppContext.this, "新浪微博分享文字不能140个,请编辑!");
//										}
//									}
//								});
//								// 启动分享GUI
//								oks.show(AppContext.this);
//							}
//							@Override
//							public void onLoadingCancelled(String imageUri, View view) {
//								UIUtil.ToastMessage(AppContext.this, "分享失败");
//								UIUtil.closeLoadingDialog();
//							}
//						});
//					}else{
//						UIUtil.ToastMessage(AppContext.this, "分享失败");
//						UIUtil.closeLoadingDialog();
//					}
//				} catch (Exception e) {
//					UIUtil.ToastMessage(AppContext.this, "分享失败");
//					UIUtil.closeLoadingDialog();
//				}
//			}
//			@Override
//			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
//				UIUtil.ToastMessage(AppContext.this, "分享失败");
//				UIUtil.closeLoadingDialog();
//			}
//		});
//	}
		
	/**
	 * 检测当前系统声音是否为正常模式
	 * @return
	 */
	public boolean isAudioNormal() {
		AudioManager mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE); 
		return mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL;
	}
	
	/**
	 * 应用程序是否发出提示音
	 * @return
	 */
	public boolean isAppSound() {
		return isAudioNormal();
	}
	
	/**
	 * 检测网络是否可用
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}
	
	
	/**
	 * 获取当前网络类型
	 * @return 0：没有网络   1：WIFI网络   2：WAP网络    3：NET网络
	 */
	@SuppressLint("DefaultLocale")
	public int getNetworkType() {
		int netType = 0;
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}		
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = networkInfo.getExtraInfo();
			if(!StringUtil.isEmpty(extraInfo)){
				if (extraInfo.toLowerCase().equals("cmnet")) {
					netType = NETTYPE_CMNET;
				} else {
					netType = NETTYPE_CMWAP;
				}
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = NETTYPE_WIFI;
		}
		return netType;
	}
	
	/**
	 * 判断当前版本是否兼容目标版本的方法
	 * @param VersionCode
	 * @return
	 */
	public static boolean isMethodsCompat(int VersionCode) {
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		return currentVersion >= VersionCode;
	}
	
	/**
	 * @return
	 */
	public Update getWeiboWeixin() {
		Update update = new Update();
		SharedPreferences sharedPreferences = getSharedPreferences("weiboweixin", Context.MODE_PRIVATE);   
		update.setWeiboUrl(sharedPreferences.getString("weibo", ""));   
		update.setWeiboAction(sharedPreferences.getString("weoboAction", ""));   
		update.setWeixinUrl(sharedPreferences.getString("weixin", ""));  
		update.setWeixinAction(sharedPreferences.getString("weixinAction","")); 
		return update;
	}
	
	/**
	 * @return
	 */
	public void setWeiboWeixin(Update update) {
		SharedPreferences sharedPreferences = getSharedPreferences("weiboweixin", Context.MODE_PRIVATE);   
		Editor editor = sharedPreferences.edit();
		editor.putString("weibo", update.getWeiboUrl());   
		editor.putString("weoboAction", update.getWeiboAction());   
		editor.putString("weixin", update.getWeixinUrl());  
		editor.putString("weixinAction", update.getWeixinAction());  
		editor.commit();
	}
	
	/**
	 * 获取App安装包信息
	 * @return
	 */
	public PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try { 
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (Exception e) {    
			//e.printStackTrace(System.err);
		} 
		if(info == null) info = new PackageInfo();
		return info;
	}
	
	/**
	 * 获取App唯一标识
	 * @return
	 */
	public String getAppId() {
		String uniqueID = getProperty(AppConfig.CONF_APP_UNIQUEID);
		if(StringUtil.isEmpty(uniqueID)){
			uniqueID = UUID.randomUUID().toString();
			setProperty(AppConfig.CONF_APP_UNIQUEID, uniqueID);
		}
		return uniqueID;
	}
	
	/**
	 * 用户是否登录
	 * @return
	 */
	public boolean isLogin() {
		login = false;
		loginUid = null;
		JSONObject user = null;
		try {
			SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);   
			String userStr = sharedPreferences.getString("userJsong", null);
			if(!StringUtil.isEmpty(userStr)){
				user = new JSONObject(userStr);
				loginUid = BaseTools.getFrom("auth_token", user, null); 			
			}
			login = !StringUtil.isEmpty(loginUid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return login;
	}
	
	/**
	 * 获取登录用户id
	 * @return
	 */
	public String getLoginUid() {
		login = false;
		JSONObject user = null;
		loginUid = null;
		try {
			SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);   
			String userStr = sharedPreferences.getString("userJsong", null);
			if(!StringUtil.isEmpty(userStr)){
				user = new JSONObject(userStr);
				loginUid = BaseTools.getFrom("auth_token", user, null); 			
			}
			login = !StringUtil.isEmpty(loginUid);
			if(!login){
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginUid;
	}
	
	/**
	 * 获取登录用户
	 * @return
	 */
	public JSONObject getLoginUser() {
		login = false;
		JSONObject user = null;
		loginUid = null;
		try {
			SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);   
			String userStr = sharedPreferences.getString("userJsong", null);
			if(!StringUtil.isEmpty(userStr)){
				user = new JSONObject(userStr);
				loginUid = BaseTools.getFrom("auth_token", user, null); 			
			}
			login = !StringUtil.isEmpty(loginUid);
			if(!login){
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * 用户注销
	 */
	public void Logout(String who) {
		if(BuildConfig.DEBUG)Log.i("==who logout==", who);
		SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);   
		Editor editor = sharedPreferences.edit();
		editor.putString("userJsong", "");
		editor.commit();
		Map<String, String> param = new HashMap<String, String>();
		param.put("auth_token", getLoginUid());
		HttpService.getByText(URLs.USER_LOGOUT, param, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String result) {
			}
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
			}
		});
	}
	
	/**
	 * 用户注销
	 */
	public void Login(String user) {
		login = true;
		SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);   
		Editor editor = sharedPreferences.edit();
		editor.putString("userJsong", user);
		editor.commit();
	}
	
	
	/**
	 * 是否启动检查更新
	 * @return
	 */
	public boolean isCheckUp()
	{
		String perf_checkup = getProperty(AppConfig.CONF_CHECKUP);
		//默认是开启
		if(StringUtil.isEmpty(perf_checkup))
			return true;
		else
			return StringUtil.toBool(perf_checkup);
	}
	
	public void clearAppCache()
	{
		//清除webview缓存
//		File file = CacheManager.getCacheFileBaseDir();  
//		if (file != null && file.exists() && file.isDirectory()) {  
//		    for (File item : file.listFiles()) {  
//		    	item.delete();  
//		    }  
//		    file.delete();  
//		}  		  
		deleteDatabase("webview.db");  
		deleteDatabase("webview.db-shm");  
		deleteDatabase("webview.db-wal");  
		deleteDatabase("webviewCache.db");  
		deleteDatabase("webviewCache.db-shm");  
		deleteDatabase("webviewCache.db-wal");  
		//清除数据缓存
		clearCacheFolder(getFilesDir(),System.currentTimeMillis());
		clearCacheFolder(getCacheDir(),System.currentTimeMillis());
		//2.2版本才有将应用缓存转移到sd卡的功能
		if(isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)){
			clearCacheFolder(MethodsCompat.getExternalCacheDir(this),System.currentTimeMillis());
		}
		//清除编辑器保存的临时内容
		Properties props = getProperties();
		for(Object key : props.keySet()) {
			String _key = key.toString();
			if(_key.startsWith("temp"))
				removeProperty(_key);
		}
	}
	
	/**
	 * 清除缓存目录
	 * @param dir 目录
	 * @param numDays 当前系统时间
	 * @return
	 */
	private int clearCacheFolder(File dir, long curTime) {          
	    int deletedFiles = 0;         
	    if (dir!= null && dir.isDirectory()) {             
	        try {                
	            for (File child:dir.listFiles()) {    
	                if (child.isDirectory()) {              
	                    deletedFiles += clearCacheFolder(child, curTime);          
	                }  
	                if (child.lastModified() < curTime) {     
	                    if (child.delete()) {                   
	                        deletedFiles++;           
	                    }    
	                }    
	            }             
	        } catch(Exception e) {       
	            e.printStackTrace();    
	        }     
	    }       
	    return deletedFiles;     
	}
	
	/**
	 * 设置启动检查更新
	 * @param b
	 */
	public void setConfigCheckUp(boolean b)
	{
		setProperty(AppConfig.CONF_CHECKUP, String.valueOf(b));
	}


	public boolean containsProperty(String key){
		Properties props = getProperties();
		 return props.containsKey(key);
	}
	
	public void setProperties(Properties ps){
		AppConfig.getAppConfig(this).set(ps);
	}

	public Properties getProperties(){
		return AppConfig.getAppConfig(this).get();
	}
	
	public void setProperty(String key,String value){
		AppConfig.getAppConfig(this).set(key, value);
	}
	
	public String getProperty(String key){
		return AppConfig.getAppConfig(this).get(key);
	}
	public void removeProperty(String...key){
		AppConfig.getAppConfig(this).remove(key);
	}

}
