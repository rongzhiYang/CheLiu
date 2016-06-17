package com.ccvee.cheliu.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 应用程序配置类：用于保存用户相关信息及设置
 * @author rongzhi.Yang
 * @data   2016-5-23  下午5:16:48
 */
public class AppConfig {
	
	private final static String APP_CONFIG = "config";
	
	public final static String APP_ROOT = "CHELIU";
	
	public final static String CONF_CHECKUP = "check_up";
	public final static String CONF_APP_UNIQUEID = APP_ROOT+"_unique_id";
	
	public final static String APP_UPFATE = APP_ROOT+File.separator+"update";
	public final static String APP_IMAGE = APP_ROOT+File.separator+"images";
	public final static String APP_DATA = APP_ROOT+File.separator+"data";
	public final static String APP_CAMERA = APP_ROOT+File.separator+"camera";
	public final static String APP_EWM = APP_ROOT+File.separator+"二维码";
	
	public final static String APP_ADS_YOUMI_APPID="a86f77060f9ea19e";
	public final static String APP_ADS_YOUMI_APPSECRET="658ab17faa94975e";
	
	/**
	 * SD卡下载歌曲目录
	 * */
	public static final String DOWNLOAD_MUSIC_DIRECTORY=APP_ROOT+File.separator+"download_music/";
	/**
	 * SD卡下载歌词目录
	 * */
	public static final String DOWNLOAD_LYRIC_DIRECTORY=APP_ROOT+File.separator+"download_lyric/";
	/**
	 * SD卡下载专辑图片目录
	 * */
	public static final String DOWNLOAD_ALBUM_DIRECTORY=APP_ROOT+File.separator+"download_album/";
	/**
	 * SD卡下载歌手图片目录
	 * */
	public static final String DOWNLOAD_ARTIST_DIRECTORY=APP_ROOT+File.separator+"download_artist/";
	
	public static final String KEY_SKINID = "skin_id";
	

	private Context mContext;
	private static AppConfig appConfig;
	
	
	public static AppConfig getAppConfig(Context context) {
		if (appConfig == null) {
			appConfig = new AppConfig();
			appConfig.mContext = context;
		}
		return appConfig;
	}

	/**
	 * 获取Preference设置
	 */
	public static SharedPreferences getSharedPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	public String get(String key) {
		Properties props = get();
		return (props != null) ? props.getProperty(key) : null;
	}

	public Properties get() {
		FileInputStream fis = null;
		Properties props = new Properties();
		try {
			File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
			fis = new FileInputStream(dirConf.getPath() + File.separator+ APP_CONFIG);
			props.load(fis);
		} catch (Exception e) {
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return props;
	}

	private void setProps(Properties p) {
		FileOutputStream fos = null;
		try {
			File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
			File conf = new File(dirConf, APP_CONFIG);
			fos = new FileOutputStream(conf);
			p.store(fos, null);
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
			}
		}
	}

	public void set(Properties ps) {
		Properties props = get();
		props.putAll(ps);
		setProps(props);
	}

	public void set(String key, String value) {
		Properties props = get();
		props.setProperty(key, value);
		setProps(props);
	}

	public void remove(String... key) {
		Properties props = get();
		for (String k : key)
			props.remove(k);
		setProps(props);
	}
	
}
