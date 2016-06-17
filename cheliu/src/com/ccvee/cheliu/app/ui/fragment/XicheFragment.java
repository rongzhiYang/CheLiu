package com.ccvee.cheliu.app.ui.fragment;

import java.util.HashMap;
import java.util.Map;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.ccvee.cheliu.R;
import com.ccvee.cheliu.app.ui.fragment.CheweiFragment.MyLocationListenner;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class XicheFragment extends BaseFragment {
	public LocationClient mLocationClient = null;
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	public BDLocationListener myListener = new MyLocationListenner();
	private boolean isFirstLoc = true; // 是否首次定位
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		TextView tvTitle = (TextView) pageView
				.findViewById(R.id.content_header_title_text);
		tvTitle.setText("洗车啦");

		 // 地图初始化
        mMapView = (MapView) pageView.findViewById(R.id.bmapView);        
        mBaiduMap = mMapView.getMap();
        
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        
		mLocationClient = new LocationClient(appContext); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener); // 注册监听函数

		LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        
		return pageView;
	}

	@Override
	public void onResume() {
		mMapView.onResume();
		if (urlParam == null)
			urlParam = new HashMap<String, String>();
		urlParam.put("auth_token", appContext.getLoginUid());
		urlParam.put("page", String.valueOf(pageIndex));
		super.onResume();
	}
	
	@Override
	public void onDestroy() {
		 // 退出时销毁定位
		mLocationClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
	}

	@Override
	public void onPause() {
		mMapView.onPause();
		super.onPause();
	}
	@Override
	public void loadData(Map<String, String> param) {

	}

	@Override
	public void clearData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}
	}

	@Override
	protected int getPageView() {
		return R.layout.main_xiche;
	}

	/**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
	
	 
	
}
