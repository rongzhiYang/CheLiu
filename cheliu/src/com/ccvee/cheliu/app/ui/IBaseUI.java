package com.ccvee.cheliu.app.ui;

import java.util.Map;

import com.ccvee.cheliu.app.AppContext;

import android.app.Activity;
import android.view.View.OnClickListener;

public interface IBaseUI extends OnClickListener{	
	
	public AppContext getAppContext();
	
	public Activity getTActivity();
	
	public void setFreshPage(boolean pageFresh);
	
	public void loadData(Map<String, String> param);
	
	public void clearData();
}
