package com.ccvee.cheliu.app.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;

import com.ccvee.cheliu.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.assist.ImageScaleType;
//import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * DP、SP 转换为 PX 的工具类
 * @author  rongzhi.Yang
 * @data    2016-5-23 
 * @version 1.0
 */
public class UIUtil {
	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变
	 * 
	 * @param pxValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 * 
	 * @param dipValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 获取手机状态栏高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		java.lang.reflect.Field field = null;
		int x = 0;
		int statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
			return statusBarHeight;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusBarHeight;
	}

	/**
	 * 判断手机号码
	 */
	public static boolean isMobileNO(String mobiles) {

		Pattern pattern = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher matcher = pattern.matcher(mobiles);

		return matcher.matches();

	}
	
	public static int winWidth;
	public static int winHight;
	
	public static void setWinWidthHight(Activity activity){
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		winWidth = dm.widthPixels;
		winHight = dm.heightPixels;
	}
	
	/**
	 * 获取屏幕大小
	 * @return
	 */
	public static DisplayMetrics getWinSize(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm;
	}
	
	/**
	 * 根据屏幕尺寸计算 等分控件的 宽、高
	 * @param <T>
	 * @param <T>
	 * @param appContext 上下文
	 * @param margin 控件移动 dp
	 * @param horizNum 横向控件数
	 * @param w_di_h 宽 高 比值
	 * @param widgets 控件List
	 * @return 
	 */
	public static <T extends View> Map<String, Integer> setWidgetSize(Activity activity, int margin, int horizNum, double w_di_h, View[] views){
		DisplayMetrics dm = getWinSize(activity);
		int width = dm.widthPixels;
		int marginPX = UIUtil.dip2px(activity, margin);
		int widgetWidth = (width-marginPX*(horizNum+1))/horizNum;
		int widgetHeight = (int) (widgetWidth/w_di_h);
		Map<String, Integer> whm = new HashMap<String, Integer>();
		whm.put("w", widgetWidth);
		whm.put("h", widgetHeight);
		whm.put("m", marginPX);
		for (View view : views) {
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(widgetWidth, (w_di_h==0.0)?LinearLayout.LayoutParams.WRAP_CONTENT:widgetHeight);
			lp.setMargins(marginPX, marginPX, 0, 0); 
			view.setLayoutParams(lp);
		}
		return whm;
	}
	
	/**
	 * 根据屏幕尺寸计算 等分控件的 宽、高
	 * @param <T>
	 * @param <T>
	 * @param appContext 上下文
	 * @param margin 控件移动 dp
	 * @param horizNum 横向控件数
	 * @param w_di_h 宽 高 比值
	 * @param widgets 控件List
	 * @return 
	 */
	public static <T extends View> Map<String, Integer> setWidgetSize(Activity activity, int margin, int horizNum, double w_di_h, View[] views, boolean setMargin){
		DisplayMetrics dm = getWinSize(activity);
		int width = dm.widthPixels;
		int marginPX = UIUtil.dip2px(activity, margin);
		int widgetWidth = (width-marginPX*(horizNum+1))/horizNum;
		int widgetHeight = (int) (widgetWidth/w_di_h);
		Map<String, Integer> whm = new HashMap<String, Integer>();
		whm.put("w", widgetWidth);
		whm.put("h", widgetHeight);
		whm.put("m", marginPX);
		for (View view : views) {
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(widgetWidth, (w_di_h==0.0)?LinearLayout.LayoutParams.WRAP_CONTENT:widgetHeight);
			if(setMargin){lp.setMargins(marginPX, marginPX, 0, 0); }
			view.setLayoutParams(lp);
		}
		return whm;
	}
	
	/**
	 * 根据 父控件尺寸 计算 等分控件的 宽、高
	 * @param <T>
	 * @param <T>
	 * @param appContext 上下文
	 * @param margin 控件移动 dp
	 * @param horizNum 横向控件数
	 * @param w_di_h 宽 高 比值
	 * @param widgets 控件List
	 * @return 
	 */
	public static <T extends View> Map<String, Integer> setWidgetSize(Activity activity, int pWidth, int margin, int horizNum, double w_di_h, View[] views){
		int width = pWidth;
		int marginPX = UIUtil.dip2px(activity, margin);
		int widgetWidth = (width-marginPX*(horizNum+1))/horizNum;
		int widgetHeight = (int) (widgetWidth/w_di_h);
		Map<String, Integer> whm = new HashMap<String, Integer>();
		whm.put("w", widgetWidth);
		whm.put("h", widgetHeight);
		whm.put("m", marginPX);
		for (View view : views) {
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(widgetWidth, (w_di_h==0.0)?LinearLayout.LayoutParams.WRAP_CONTENT:widgetHeight);
			lp.setMargins(marginPX, marginPX, 0, 0); 
			view.setLayoutParams(lp);
		}
		return whm;
	}
	
	/**
	 * 根据屏幕尺寸计算 等分控件的 宽、高
	 * @param <T>
	 * @param <T>
	 * @param appContext 上下文
	 * @param margin 控件移动 dp
	 * @param horizNum 横向控件数
	 * @param w_di_h 宽 高 比值
	 * @param widgets 控件List
	 * @return 
	 */
	public static <T extends View> Map<String, Integer> setWidgetSize(Activity activity, int margin, int horizNum, double w_di_h){
		DisplayMetrics dm = getWinSize(activity);
		int width = dm.widthPixels;
		int marginPX = UIUtil.dip2px(activity, margin);
		int widgetWidth = (width-marginPX*(horizNum+1))/horizNum;
		int widgetHeight = (int) (widgetWidth/w_di_h);
		Map<String, Integer> whm = new HashMap<String, Integer>();
		whm.put("w", widgetWidth);
		whm.put("h", widgetHeight);
		whm.put("m", marginPX);
		return whm;
	}
	
	/**
	 * 隐藏输入法
	 * @param view
	 */
	public static void hideSoftInputFromWindow(EditText view, Activity context) {
		//if(context.getWindow().getAttributes().softInputMode!=WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		view.clearFocus();
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		//}
	}
	
	/**
	 * 显示输入法
	 * @param view
	 */
	public static void showSoftInputFromWindow(EditText view, Activity context) {
		//if(context.getWindow().getAttributes().softInputMode!=WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
		//}
	}
	
	/**
	 * 打开浏览器
	 * 
	 * @param context
	 * @param url
	 */
	public static void openBrowser(Context context, String url) {
		try {
			Uri uri = Uri.parse(url);
			Intent it = new Intent(Intent.ACTION_VIEW, uri);
			context.startActivity(it);
		} catch (Exception e) {
			e.printStackTrace();
			ToastMessage(context, "无法浏览此网页", 500);
		}
	}
	
	/**
	 * 退出应用程序
	 */
	public static void AppExit(Context context) {
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(startMain);
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}

	/**
	 * 退出程序
	 * 
	 * @param cont
	 */
	public static void Exit(final Context cont) {
//		CommonDialog dialog = new CommonDialog(cont, true);
//		dialog.show(true, false, true, "确定退出程序吗？", "", "确定", "取消", new android.view.View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				((CommonDialog)view.getTag()).dismiss();
//				AppManager.getAppManager().AppExit(cont);
//			}
//		}, new android.view.View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				((CommonDialog)view.getTag()).dismiss();
//			}
//		});
	}
	
	/**
	 * 弹出Toast消息
	 * 
	 * @param msg
	 */
	public static void ToastMessage(Context cont, String msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, int msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, String msg, int time) {
		Toast.makeText(cont, msg, time).show();
	}
	
	private static Dialog loadingDialog;
	public static Dialog showLoadingDialog(Context context, String msg, boolean cancelable){
		loadingDialog = createLoadingDialog(context, msg, cancelable);
		loadingDialog.show();
		return loadingDialog;
	}
	
	public static void closeLoadingDialog(){
		if(loadingDialog!=null&&loadingDialog.isShowing())loadingDialog.dismiss();
	}
	
	/**
	 * 得到自定义的progressDialog
	 * @param context
	 * @param msg
	 * @return
	 */
	public static Dialog createLoadingDialog(Context context, String msg, boolean cancelable) {
		LayoutInflater inflater = LayoutInflater.from(context);
		int layoutId = R.layout.loading_dialog;
		View v = inflater.inflate(layoutId, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.anim.loading_animation);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setText(msg);// 设置加载信息

		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
		loadingDialog.setCancelable(true);// 不可以用“返回键”取消
		loadingDialog.setCanceledOnTouchOutside(cancelable);
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		return loadingDialog;

	}
	
	public static void openActivity(Activity activity,Class<? extends Activity> activityClass,Bundle data){
		Intent intent = new Intent(activity, activityClass);
		if(data!=null)intent.putExtras(data);
		activity.startActivity(intent);
	}
	
	public static void openActivity(Activity activity,Class<? extends Activity> activityClass,Bundle data, int requestCode){
		Intent intent = new Intent(activity, activityClass);
		if(data!=null)intent.putExtras(data);
		activity.startActivityForResult(intent, requestCode);
	}
	
	public static void openActivity(Fragment activity,Class<? extends Activity> activityClass,Bundle data, int requestCode){
		Intent intent = new Intent(activity.getActivity(), activityClass);
		if(data!=null)intent.putExtras(data);
		activity.startActivityForResult(intent, requestCode);
	}
	
	public static void openActivity(Fragment activity,Class<? extends Activity> activityClass,Bundle data){
		Intent intent = new Intent(activity.getActivity(), activityClass);
		if(data!=null)intent.putExtras(data);
		activity.startActivity(intent);
	}
	
//	public static LinkedList<View> autoTag(Context context, JSONArray jsonArr, String mkey, String keyd, ViewGroup container, OnTagClick onTagClick){
//		LinkedList<String> items = new LinkedList<String>();
//		for (int i = 0;jsonArr!=null&& i < jsonArr.length(); i++) {
//			try {
//				items.add(jsonArr.getString(i));
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//		}
//		return autoTag(context, items, mkey, keyd, container, onTagClick);
//	}
	
//	public static LinkedList<View> autoTag(Context context, LinkedList<String> items, String mkey, String keyd, ViewGroup container, final OnTagClick onTagClick) {
//		int winWidth = UIUtil.winWidth;
//		int ml = 0,mt = 0,rt = 0;
//		LinkedList<View> views = new LinkedList<View>();
//		LinkedList<Map<String, Integer>> bList = new LinkedList<Map<String,Integer>>();
//		Map<String, Object> allmap = new HashMap<String, Object>();
//		
//		int cdtdIndex = -1;
//		
//		for (int i = 0;items!=null && i < items.size(); i++) {
//			LinearLayout rl = new LinearLayout(context);
//			LinearLayout ll = new LinearLayout(context);
//			LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//			llp.setMargins(0, 0, UIUtil.dip2px(context, 10), UIUtil.dip2px(context, 5));
//			ll.setLayoutParams(llp);
//			rl.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//			TextView textView = new TextView(context);
//			textView.setTag("tag");
//			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//			lp.setMargins(UIUtil.dip2px(context, 10), UIUtil.dip2px(context, 5), UIUtil.dip2px(context, 10), UIUtil.dip2px(context, 5));
//			textView.setLayoutParams(lp);
//			String textCon = items.get(i);
//			if(!StringUtil.isEmpty(keyd)&&!StringUtil.isEmpty(textCon)&&textCon.equals(keyd)){
//				cdtdIndex = i;
//			}
//			textView.setText(textCon);
//			ll.addView(textView);
//			ll.setBackgroundResource(R.drawable.bg_text_tag_white);
//			rl.addView(ll);
//			
//			Map<String, Integer> map = new HashMap<String, Integer>();
//			int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);  
//		    int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);  
//		    rl.measure(w, h);  
//		    int height =rl.getMeasuredHeight();  
//		    int width =rl.getMeasuredWidth();  
//		    if(!bList.isEmpty()){
//		    	Map<String, Integer> bMap = bList.get(bList.size()-1);
//		    	if(bMap.get("x")+width>(winWidth-UIUtil.dip2px(context, 20))){
//		    		ml = 0;
//		    		mt = bMap.get("ty")+rt;
//		    		rt = 0;
//		    	}else{
//		    		ml = bMap.get("x");
//		    		mt = bMap.get("ty")-bMap.get("h");
//		    	}
//		    }else{
//		    	ml = 0;
//		    	mt = 0;
//		    }
//			map.put("x", width+ml);
//			map.put("y", height+mt);
//			map.put("ty", mt+height);
//			map.put("w", width);
//			map.put("h", height);
//			bList.add(map);
//			//lp.setMargins(ml, mt, 50, 50);
//			//LinearLayout.LayoutParams elp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//			llp.setMargins(ml, mt, UIUtil.dip2px(context, 10), UIUtil.dip2px(context, 5));
//			rl.removeAllViewsInLayout();
//			rl.addView(ll);
//			container.addView(rl);
//			views.add(ll);
//			allmap.put("views", views);
//			allmap.put("selected", -1);
//			allmap.put("mkey", mkey);
//			ll.setTag(allmap);
////			ll.setTag(1,i);
////			ll.setTag(2,0);
//			ll.setOnClickListener(new OnClickListener() {
//				@SuppressWarnings("unchecked")
//				@Override
//				public void onClick(View v) {
//					Map<String, Object> bts = (Map<String, Object>) v.getTag();
//					LinkedList<View> views = (LinkedList<View>) bts.get("views");
//					int mid = (Integer) bts.get("selected");
//					int tid = views.indexOf(v);
//					TextView tag = (TextView) v.findViewWithTag("tag");
//					if(mid==tid){
//						views.get(tid).setBackgroundResource(R.drawable.bg_text_tag_white);
//						if(onTagClick!=null){
//							Map<String, String> rmap = new HashMap<String, String>();
//							rmap.put(bts.get("mkey").toString(), "");
//							onTagClick.onTagClick(rmap);
//						}
//						tid = -1;
//					}else{
//						if(mid>-1)views.get(mid).setBackgroundResource(R.drawable.bg_text_tag_white);
//						v.setBackgroundResource(R.drawable.bg_text_tag_red);
//						if(onTagClick!=null){
//							Map<String, String> rmap = new HashMap<String, String>();
//							rmap.put(bts.get("mkey").toString(), tag!=null?tag.getText().toString():"");
//							onTagClick.onTagClick(rmap);
//						}
//					}
//					for (View viwe : views) {
//						Map<String, Object> fmap = (Map<String, Object>)viwe.getTag();
//						fmap.put("selected", tid);
//						viwe.setTag(fmap);
//					}
//				}
//			});
//			if(cdtdIndex>-1){
//				views.get(cdtdIndex).performClick();
//			}
//		}
//		return views;
//	}
	
	public interface OnTagClick{
		public void onTagClick(Map<String,String> v);
	}
	
//	public static DisplayImageOptions getImageLoaderDisplayOptions(int image){
//		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//		.showImageOnLoading(image)
//		.showImageForEmptyUri(image)
//		.showImageOnFail(image)
//		.showImageForEmptyUri(image)
//		.cacheOnDisk(true)
//		.cacheInMemory(true)
//		.displayer(new FadeInBitmapDisplayer(0))
//		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示  
//		.bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//  
//		.build();
//		
//		return defaultOptions;
//	}
}