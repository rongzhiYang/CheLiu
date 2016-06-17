package com.ccvee.cheliu.app.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.ccvee.cheliu.BuildConfig;
import com.ccvee.cheliu.R;
import com.ccvee.cheliu.app.AppConfig;
import com.ccvee.cheliu.app.AppContext;
import com.ccvee.cheliu.app.AppManager;
import com.ccvee.cheliu.app.URLs;
import com.ccvee.cheliu.app.service.HttpService;
import com.ccvee.cheliu.app.widgets.CommonDialog;
import com.loopj.android.http.TextHttpResponseHandler;


import com.ccvee.cheliu.app.bean.Update;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 应用程序更新工具包
 * @author  rongzhi.Yang
 * @data    2016-5-23 
 * @version 1.0
 */
public class UpdateManager {

	private static final int DOWN_NOSDCARD = 0;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
	
    private static final int DIALOG_TYPE_LATEST = 0;
    private static final int DIALOG_TYPE_FAIL   = 1;
    
	private static UpdateManager updateManager;
	
	private Activity mContext;
	//通知对话框
	private CommonDialog noticeDialog;
	//下载对话框
	private CommonDialog downloadDialog;
	//'已经是最新' 或者 '无法获取最新版本' 的对话框
	private CommonDialog latestOrFailDialog;
    //进度条
    private ProgressBar mProgress;
    //显示下载数值
    private TextView mProgressText;
    //查询动画
    private Dialog mProDialog;
    //进度值
    private int progress;
    //下载线程
    private Thread downLoadThread;
    //终止标记
    private boolean interceptFlag;
	//提示语
	private String updateMsg = "";
	//返回的安装包url
	private String apkUrl = "";
	//下载包保存路径
    private String savePath = "";
	//apk保存完整路径
	private String apkFilePath = "";
	//临时下载文件路径
	private String tmpFilePath = "";
	//下载文件大小
	private String apkFileSize;
	//已下载文件大小
	private String tmpFileSize;
	
	@SuppressWarnings("unused")
	private String curVersionName = "";
	private int curVersionCode;
	private Update mUpdate;
    
    @SuppressLint("HandlerLeak") 
    private Handler mHandler = new Handler(){
    	@SuppressLint("ShowToast") 
    	public void handleMessage(Message msg) {
    		switch (msg.what) {
			case DOWN_UPDATE:
				mProgress.setProgress(progress);
				mProgressText.setText(tmpFileSize + "/" + apkFileSize);
				break;
			case DOWN_OVER:
				downloadDialog.dismiss();
				installApk();
				break;
			case DOWN_NOSDCARD:
				downloadDialog.dismiss();
				Toast.makeText(mContext, "无法下载安装文件，请检查SD卡是否挂载", 3000).show();
				break;
			}
    	};
    };
    
	public static UpdateManager getUpdateManager() {
		if(updateManager == null){
			updateManager = new UpdateManager();
		}
		updateManager.interceptFlag = false;
		return updateManager;
	}
	
	/**
	 * 检查App更新
	 * @param context
	 * @param isShowMsg 是否显示提示消息
	 */
	@SuppressLint("HandlerLeak") 
	public void checkAppUpdate(Activity context, final boolean isShowMsg){
		this.mContext = context;
		final AppContext appContext = (AppContext) context.getApplication();
		getCurrentVersion();
		if(isShowMsg){
			if(mProDialog == null){
				mProDialog = UIUtil.showLoadingDialog(mContext, "正在检测，请稍后...", false);
				//mProDialog = ProgressDialog.show(mContext, null, "正在检测，请稍后...", true, true);
			}else if(mProDialog.isShowing() || (latestOrFailDialog!=null && latestOrFailDialog.isShowing())){
				return;
			}
		}
		final Handler handler = new Handler(){
			public void handleMessage(Message msg) {
				//进度条对话框不显示 - 检测结果也不显示
				if(mProDialog != null && !mProDialog.isShowing()){
					return;
				}
				//关闭并释放释放进度条对话框
				if(isShowMsg && mProDialog != null){
					mProDialog.dismiss();
					mProDialog = null;
				}
				//显示检测结果
				if(msg.what == 1){
					mUpdate = (Update)msg.obj;
					System.out.println("更新信息：" + mUpdate.toString());
					AppConfig.getAppConfig(mContext).set("adsp", mUpdate.getUpdateLog());
	//				((MainActivity)mContext).initDown();                                     //这里需要修稿，车流
					
					if(mUpdate != null){
						int uploadversion = 0;
						
						try {
							uploadversion = Integer.parseInt(mUpdate.getVersionCode());
							System.out.println("更新信息uploadversion：" + uploadversion);
							System.out.println("更新信息curVersionCode：" + curVersionCode);
						} catch (Exception e) {}
						if(curVersionCode < uploadversion){
							System.out.println("更新........" );
							apkUrl = mUpdate.getDownloadUrl();
							updateMsg = mUpdate.getVersionDesc();
							showNoticeDialog(mUpdate.getMinVersion().equals("true"));
						}else if(isShowMsg){
							showLatestOrFailDialog(DIALOG_TYPE_LATEST);
						}
					}
				}else if(isShowMsg){
					showLatestOrFailDialog(DIALOG_TYPE_FAIL);
				}
			}
		};
		//new Thread(){
		//	public void run() {
				
				try {
					Map<String, String> map = new HashMap<String, String>();
					map.put("auth-token", appContext.getLoginUid());
					map.put("phone_type", "0");
					final Update update = new Update();
					HttpService.getByText(URLs.APP_VERESION, map, new TextHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, Header[] arg1, String resultStr) {
							try {
								if(BuildConfig.DEBUG)Log.i("==result get==", resultStr);
								JSONObject result = new JSONObject(resultStr);
								Log.i("YRZ", result.toString());
								if(result.getInt("status")==0){
									update.setId(BaseTools.getFrom("data._id", result));
									update.setVersionDesc(BaseTools.getFrom("data.version.desc", result,"赚零花钱有更新啦！"));
									update.setDownloadUrl(BaseTools.getFrom("data.version.url", result));
									update.setVersionCode(BaseTools.getFrom("data.version.latest", result));
									update.setMinVersion(BaseTools.getFrom("data.version.force_update", result,"false"));
									
									update.setUpdateLog(BaseTools.getFrom("data.adsp", result));
									URLs.IMAGE_HOST = BaseTools.getFrom("data.image_servers."+(System.currentTimeMillis()%2), result,"http://182.254.153.36/imagecache");
								}
								Message msg = new Message();
								msg.what = 1;
								msg.obj = update;
								handler.sendMessage(msg);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						
						@Override
						public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
							
						}
					});
					
					
//					JSONObject updateJson = CommendJsonService.getInstance((AppContext)mContext.getApplication()).loadPageJson(HttpType.GET, URLs.MAIN_VERSON_CHECK_JSONDATURL, map, null);
//					JsonObject data = DataUtil.jsonIsObject(updateJson, "data")?updateJson.getAsJsonObject("data"):null;
//					Update update = new Update();
//					update.setVersionName("bniyou_"+DataUtil.getJsonAsInt(data, "version"));
//					update.setVersionCode(DataUtil.getJsonAsInt(data, "version"));
//					update.setDownloadUrl(DataUtil.getJsonAsString(data, "download_url"));
//					update.setUpdateLog(DataUtil.getJsonAsString(data, "version_desc"));
//					JsonObject oauth_list = DataUtil.jsonIsObject(data, "oauth_list")?data.getAsJsonObject("oauth_list"):null;
//					Editor editor = preferences.edit();
//					if(!StringUtils.isEmpty(DataUtil.getJsonAsString(oauth_list, "weibo_oauth_loginurl")))editor.putString("weibo_oauth_loginurl", DataUtil.getJsonAsString(oauth_list, "weibo_oauth_loginurl"));  
//					if(!StringUtils.isEmpty(DataUtil.getJsonAsString(oauth_list, "weixin_oauth_loginurl")))editor.putString("weixin_oauth_loginurl", DataUtil.getJsonAsString(oauth_list, "weixin_oauth_loginurl"));  
//					if(!StringUtils.isEmpty(DataUtil.getJsonAsString(oauth_list, "qq_oauth_loginurl")))editor.putString("qq_oauth_loginurl", DataUtil.getJsonAsString(oauth_list, "qq_oauth_loginurl"));  
//					if(!DataUtil.getJsonAsBoolean(data, "is_login")){
//						editor.putString("account", "");   
//						editor.putString("password", "");   
//						editor.putString("token", "");   
//						editor.putString("userimg", "");   
//						
//					};
//					editor.commit();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
		//	}			
		//}.start();		
	}	
	
	/**
	 * 显示'已经是最新'或者'无法获取版本信息'对话框
	 */
	@SuppressLint("NewApi") 
	private void showLatestOrFailDialog(int dialogType) {
		if (latestOrFailDialog != null) {
			//关闭并释放之前的对话框
			latestOrFailDialog.dismiss();
			latestOrFailDialog = null;
		}
		latestOrFailDialog = new CommonDialog(mContext, true);
//		AlertDialog.Builder builder = new Builder(mContext,R.style.shareDialog);
//		builder.setTitle("系统提示");
		String  content = "";
		if (dialogType == DIALOG_TYPE_LATEST) {
			content = "您当前已经是最新版本";
		} else if (dialogType == DIALOG_TYPE_FAIL) {
			content = "无法获取版本更新信息";
		}
//		builder.setPositiveButton("确定", null);
//		latestOrFailDialog = builder.create();
//		latestOrFailDialolg.show();
		android.view.View.OnClickListener l = new android.view.View.OnClickListener() {
			@Override
			public void onClick(View view) {
				((CommonDialog)view.getTag()).dismiss();
			}
		};
		latestOrFailDialog.show(true, true, false, "系统提示", content, "确定", "取消", l, l);
	}

	/**
	 * 获取当前客户端版本信息
	 */
	private void getCurrentVersion(){
        try { 
        	PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        	curVersionName = info.versionName;
        	curVersionCode = info.versionCode;
        } catch (NameNotFoundException e) {    
			e.printStackTrace(System.err);
		} 
	}
	
	/**
	 * 显示版本更新通知对话框
	 */
	private void showNoticeDialog(final boolean force){
//		AlertDialog.Builder builder = new Builder(mContext);
//		builder.setTitle("软件版本更新");
//		builder.setMessage(updateMsg);
//		builder.setPositiveButton("立即更新", new OnClickListener() {			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//				showDownloadDialog();			
//			}
//		});
//		builder.setNegativeButton("以后再说", new OnClickListener() {			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();				
//			}
//		});
		noticeDialog = new CommonDialog(mContext, false);
		noticeDialog.setCancelable(!force);
		noticeDialog.show(true, true, true, "软件版本更新", updateMsg, "立即更新", force?"退出应用":"以后再说", new android.view.View.OnClickListener() {
			@Override
			public void onClick(View view) {
				((CommonDialog)view.getTag()).dismiss();
				showDownloadDialog(force);	
			}
		}, new android.view.View.OnClickListener() {
			@Override
			public void onClick(View view) {
				((CommonDialog)view.getTag()).dismiss();
				if(force){
					AppManager.getAppManager().AppExit(mContext);
				}
			}
		});
	}
	
	/**
	 * 显示下载对话框
	 */
	private void showDownloadDialog(final boolean force){
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.update_progress, null);
		mProgress = (ProgressBar)v.findViewById(R.id.update_progress);
		mProgressText = (TextView) v.findViewById(R.id.update_progress_text);
		downloadDialog = new CommonDialog(mContext, false);
		downloadDialog.setCancelable(!force);
		downloadDialog.show(true, true, false, "正在下载新版本", "", force?"下载中":"取消", "", new android.view.View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(!force){
					((CommonDialog)view.getTag()).dismiss();
					interceptFlag = true;
				}
				
			}
		}, null);
		downloadDialog.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				if(!force){
				dialog.dismiss();
				interceptFlag = true;
				}
			}
		});
		downloadDialog.addContent(v);
//		AlertDialog.Builder builder = new Builder(mContext);
//		builder.setTitle("正在下载新版本");
//		
//		
//		
//		builder.setView(v);
//		builder.setNegativeButton("取消", new OnClickListener() {	
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//				interceptFlag = true;
//			}
//		});
//		builder.setOnCancelListener(new OnCancelListener() {
//			@Override
//			public void onCancel(DialogInterface dialog) {
//				dialog.dismiss();
//				interceptFlag = true;
//			}
//		});
//		downloadDialog = builder.create();
//		downloadDialog.setCanceledOnTouchOutside(false);
//		downloadDialog.show();
		
		downloadApk();
	}
	
	private Runnable mdownApkRunnable = new Runnable() {	
		@Override
		public void run() {
			try {
				String rd = StringUtil.currentRadom();
				String apkName = AppConfig.APP_ROOT+"_"+mUpdate.getVersionCode()+"_"+rd+".apk";
				String tmpApk = AppConfig.APP_ROOT+"_"+mUpdate.getVersionCode()+"_"+rd+".tmp";
				//判断是否挂载了SD卡
				String storageState = Environment.getExternalStorageState();		
				if(storageState.equals(Environment.MEDIA_MOUNTED)){
					savePath = Environment.getExternalStorageDirectory().getAbsolutePath() +File.separator+AppConfig.APP_UPFATE;
					File file = new File(savePath);
					if(!file.exists()){
						file.mkdirs();
					}
					apkFilePath = savePath + File.separator+apkName;
					tmpFilePath = savePath + File.separator+tmpApk;
				}
				
				//没有挂载SD卡，无法下载文件
				if(apkFilePath == null || apkFilePath == ""){
					mHandler.sendEmptyMessage(DOWN_NOSDCARD);
					return;
				}
				
				File ApkFile = new File(apkFilePath);
				
				//是否已下载更新文件
				if(ApkFile.exists()){
					downloadDialog.dismiss();
					installApk();
					return;
				}
				
				//输出临时下载文件
				File tmpFile = new File(tmpFilePath);
				FileOutputStream fos = new FileOutputStream(tmpFile);
				
				URL url = new URL(apkUrl);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();
				
				//显示文件大小格式：2个小数点显示
		    	DecimalFormat df = new DecimalFormat("0.00");
		    	//进度条下面显示的总文件大小
		    	apkFileSize = df.format((float) length / 1024 / 1024) + "MB";
				
				int count = 0;
				byte buf[] = new byte[1024];
				
				do{   		   		
		    		int numread = is.read(buf);
		    		count += numread;
		    		//进度条下面显示的当前下载文件大小
		    		tmpFileSize = df.format((float) count / 1024 / 1024) + "MB";
		    		//当前进度值
		    	    progress =(int)(((float)count / length) * 100);
		    	    //更新进度
		    	    mHandler.sendEmptyMessage(DOWN_UPDATE);
		    		if(numread <= 0){	
		    			//下载完成 - 将临时下载文件转成APK文件
						if(tmpFile.renameTo(ApkFile)){
							//通知安装
							mHandler.sendEmptyMessage(DOWN_OVER);
						}
		    			break;
		    		}
		    		fos.write(buf,0,numread);
		    	}while(!interceptFlag);//点击取消就停止下载
				
				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch(IOException e){
				e.printStackTrace();
			}
			
		}
	};
	
	/**
	* 下载apk
	* @param url
	*/	
	private void downloadApk(){
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}
	
	/**
    * 安装apk
    * @param url
    */
	private void installApk(){
		File apkfile = new File(apkFilePath);
        if (!apkfile.exists()) {
            return;
        }    
        String cmd="chmod 777 "+apkFilePath;
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (Exception e) {
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive"); 
        mContext.startActivity(i);
	}
}
