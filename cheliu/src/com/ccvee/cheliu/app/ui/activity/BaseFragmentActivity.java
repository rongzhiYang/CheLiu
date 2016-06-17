package com.ccvee.cheliu.app.ui.activity;

import java.util.Map;

import com.ccvee.cheliu.app.AppContext;
import com.ccvee.cheliu.app.AppManager;
import com.ccvee.cheliu.app.ui.IBaseUI;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Window;


public abstract class BaseFragmentActivity extends FragmentActivity implements IBaseUI {
	protected AppContext appContext;
	protected Activity activity;
	protected boolean pageFresh = false;
	protected boolean pageEnd = false;
	private boolean canDistroy = false;
	protected int pageIndex;
	protected Map<String,String> urlParam;
	 //用于对Fragment进行管理 
    protected FragmentManager fragmentManager;  
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 竖屏幕
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);
		fragmentManager = getSupportFragmentManager();
		appContext = getAppContext();
		activity = getTActivity();
			
	}

	@Override
	protected void onDestroy() {	
		if(canDistroy){
			super.onDestroy();
			AppManager.getAppManager().removeActivity(this);
		}
	}
	
	@Override
	public void finish() {
		super.finish();
		canDistroy = true;
	}
	
	@Override
	public void onResume() {
		super.onResume();	
		if(pageFresh){
			pageFresh = false;
			clearData();
			loadData(urlParam);
		}
	}
    
    @Override
    protected void onPause() {
        super.onPause();       
    }

	@Override
	public AppContext getAppContext() {
		return (AppContext) getApplication();
	}
	
	@Override
	public void setFreshPage(boolean pageFresh){
		this.pageFresh = pageFresh;
	}
	
	public void setPageEnd(boolean pageEnd){
		this.pageEnd = pageEnd;
	}
	
	@Override
	public Activity getTActivity() {
		return this;
	}

	public boolean isCanDistroy() {
		return canDistroy;
	}
	
}
