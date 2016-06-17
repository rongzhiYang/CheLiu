package com.ccvee.cheliu.app.utils;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 
 * @author  rongzhi.Yang
 * @data    2016-5-23 
 * @version 1.0
 */
public class BaseTools {
	
	public static int getWindowWidth(Context context){
		// 获取屏幕分辨率
		WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		int mScreenWidth = dm.widthPixels;
		return mScreenWidth;
	}
	
	public static int getWindowHeigh(Context context){
		// 获取屏幕分辨率
		WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		int mScreenHeigh = dm.heightPixels;
		return mScreenHeigh;
	}
	
	/**
	 * 格式化毫秒->00:00
	 * */
	public static String formatSecondTime(int millisecond) {
		if (millisecond == 0) {
			return "00:00";
		}
		millisecond = millisecond / 1000;
		int m = millisecond / 60 % 60;
		int s = millisecond % 60;
		return (m > 9 ? m : "0" + m) + ":" + (s > 9 ? s : "0" + s);
	}
	
	@SuppressWarnings("rawtypes")
	public static String getFrom(String path,Map map){
		String[] arr = path.split("\\.");
		Object result = null;
		if(arr.length>1&&map!=null){
			Object temp = map;
			for(String str : arr){
				if(temp instanceof java.util.HashMap 
						|| temp instanceof java.util.HashSet 
						|| temp instanceof java.util.HashSet || temp instanceof java.util.concurrent.ConcurrentHashMap){
					temp = ((Map) temp).get(str);
				} else {
					break;
				}
			}
			result = temp;
		} else {
			result = map!=null?map.get(path):"";
			
		}
		return result!=null?result.toString():"";
	}
	
	public static String getFrom(String path,JSONObject json,String defaultStr){
		String ret = getFrom(path, json);
		return StringUtil.isEmpty(ret)?defaultStr:ret;
	}
	
	public static String getFrom(String path,JSONObject json){
		String[] arr = path.split("\\.");
		String result = null;
		if(arr.length>0&&json!=null){
			try {
				JSONObject temp = json;
				Object tval = null;
				for (int i = 0; i < arr.length; i++) {
					tval = temp.get(arr[i]);
					if(tval instanceof JSONObject){
						temp = (JSONObject) tval;
					}else if(tval instanceof JSONArray&&(i+1)<arr.length){
						temp = ((JSONArray)tval).getJSONObject(Integer.parseInt(arr[i+1]));
						i++;
					}else{
						break;
					}
				}
				result = tval==null?null:tval.toString();
			} catch (Exception e) {}
			
		} else {
			result = "";
			
		}
		return result!=null?result:"";
	}
	
}
