package com.ccvee.cheliu.app.ui.fragment;

import java.util.Map;

import com.ccvee.cheliu.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BaoyangFragment extends BaseFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		TextView tvTitle = (TextView) pageView.findViewById(R.id.content_header_title_text);
		tvTitle.setText("保养啦");
		return pageView;
	}

	@Override
	public void loadData(Map<String, String> param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected int getPageView() {
		// TODO Auto-generated method stub
		return R.layout.main_baoyang;
	}


}
