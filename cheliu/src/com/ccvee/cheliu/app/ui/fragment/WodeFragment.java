package com.ccvee.cheliu.app.ui.fragment;

import java.util.Map;

import com.ccvee.cheliu.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WodeFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		TextView tvTitle = (TextView) pageView.findViewById(R.id.content_header_title_text);
		tvTitle.setText("个人中心");
		return pageView;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);		
	}
	
	
	public void initDown(){
       
    }
	

	@Override
	public void loadData(Map<String, String> param) {
		
	}

	

	@Override
	public void onClick(View v) {
		
	}

	@Override
	protected int getPageView() {
		return  R.layout.main_wode;
	}

    @Override
    public void clearData() {
        // TODO Auto-generated method stub
        
    }

 
    

}
