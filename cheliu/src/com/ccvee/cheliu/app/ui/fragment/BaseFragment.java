package com.ccvee.cheliu.app.ui.fragment;

import java.util.Map;

import com.baidu.mapapi.SDKInitializer;
import com.ccvee.cheliu.app.AppContext;
import com.ccvee.cheliu.app.ui.IBaseUI;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment implements IBaseUI {
	protected AppContext appContext;
	protected View pageView;
	protected Activity activity;
	protected boolean pageFresh = false;
	protected boolean pageEnd = false;
	protected int pageIndex = 0;
	protected Map<String,String> urlParam;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		appContext = getAppContext();	
		pageView = inflater.inflate(getPageView(), container, false); 
		activity = getTActivity();
		
		return pageView;
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadData(Map<String, String> param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		
	}

	

	protected abstract int getPageView();

	@Override
	public AppContext getAppContext() {
		return (AppContext) getActivity().getApplication();
	}
	
	@Override
	public void setFreshPage(boolean pageFresh) {
		this.pageFresh = pageFresh;
	}
	
	public void setPageEnd(boolean pageEnd){
		this.pageEnd = pageEnd;
	}
	
	@Override
	public Activity getTActivity() {
		return getActivity();
	}
}
