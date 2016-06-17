package com.ccvee.cheliu.app.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 应用程序更新实体类
 * @version 1.0
 * @created 2012-3-21
 */
public class Update implements Parcelable{
	public final static String UTF8 = "UTF-8";
	public final static String NODE_ROOT = "oschina";
	
	private String minVersion;
	private String versionCode;
	private String versionName;
	private String downloadUrl;
	private String updateLog;
	private String versionDesc;
	private String id;
	private String weiboUrl;
	private String weiboAction;
	private String weixinUrl;
	private String weixinAction;
	
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getUpdateLog() {
		return updateLog;
	}
	public void setUpdateLog(String updateLog) {
		this.updateLog = updateLog;
	}
	
	public String getVersionDesc() {
		return versionDesc;
	}
	public void setVersionDesc(String versionDesc) {
		this.versionDesc = versionDesc;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWeiboUrl() {
		return weiboUrl;
	}
	public void setWeiboUrl(String weiboUrl) {
		this.weiboUrl = weiboUrl;
	}
	public String getWeiboAction() {
		return weiboAction;
	}
	public void setWeiboAction(String weiboAction) {
		this.weiboAction = weiboAction;
	}
	public String getWeixinUrl() {
		return weixinUrl;
	}
	public void setWeixinUrl(String weixinUrl) {
		this.weixinUrl = weixinUrl;
	}
	public String getWeixinAction() {
		return weixinAction;
	}
	public void setWeixinAction(String weixinAction) {
		this.weixinAction = weixinAction;
	}
	
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	
	public String getMinVersion() {
		return minVersion;
	}
	public void setMinVersion(String minVersion) {
		this.minVersion = minVersion;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(versionCode);
		dest.writeString(minVersion);
		dest.writeString(versionName);
		dest.writeString(downloadUrl);
		dest.writeString(updateLog);
		dest.writeString(versionDesc);
		dest.writeString(weiboUrl);
		dest.writeString(weiboAction);
		dest.writeString(weixinUrl);
		dest.writeString(weixinAction);
	}
}
