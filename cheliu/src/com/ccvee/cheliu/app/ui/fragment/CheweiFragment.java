package com.ccvee.cheliu.app.ui.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.ccvee.cheliu.R;
import com.ccvee.cheliu.R.color;
import com.ccvee.cheliu.app.utils.UIUtil;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author rongzhi.Yang
 * @data 2016-5-27
 * @version 1.0
 * 
 * 
 *          坐标：104.070439,30.548166长虹 104.00771,30.573846 圣菲
 *          104.072235,30.663481 天府广场 104.069487,30.546822 104.070978,30.548222
 *          104.070798,30.549007
 */
public class CheweiFragment extends BaseFragment {
	public LocationClient mLocationClient = null;
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	public BDLocationListener myListener = new MyLocationListenner();
	
	
	public String[] data = {"长虹科技大厦，车位剩余500","圣菲TOWN,无车位","天府广场200","软件园F区758","携程信息大厦233","OCG国际256"};
	private boolean isFirstLoc = true; // 是否首次定位
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:

				break;
			default:
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		TextView tvTitle = (TextView) pageView
				.findViewById(R.id.content_header_title_text);
		tvTitle.setText("发现车位");

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
		HashMap map = new HashMap<String, String>();
		loadData(map);

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
		double[] latitude = { 104.070439, 104.00771, 104.072235, 104.069487,
				104.070978, 104.070798 };
		double[] longitude = { 30.548166, 30.573846, 30.663481, 30.546822,
				30.548222, 30.549007 };
		locationMarker(longitude, latitude);
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
		return R.layout.main_chewei;
	}

	public void locationMarker(double[] latitude, double[] longitude) {
		BitmapDescriptor bitmap;
		for (int i = 0; i < longitude.length; i++) {
			// 定义Maker坐标点 double latitude,double longitude
			LatLng point = new LatLng(latitude[i], longitude[i]);
			// 构建Marker图标

			View localView = View.inflate(appContext, R.layout.baidu_marker_1,
					null);
			TextView localTextView1 = (TextView) localView
					.findViewById(R.id.marker_title);

			localTextView1.setText(data[i]);

			bitmap = BitmapDescriptorFactory.fromView(localView);

			// 构建MarkerOption，用于在地图上添加Marker
			OverlayOptions option = new MarkerOptions().title("hahaha")
					.position(point).icon(bitmap);
			// 在地图上添加Marker，并显示
			// mBaiduMap.addOverlay(option);
			Marker marker = (Marker) (mBaiduMap.addOverlay(option));
			marker.setTitle("车位：10" + i);
			// 构建文字Option对象，用于在地图上添加文字
			// OverlayOptions textOption = new TextOptions()
			// .bgColor(Color.RED)
			// .fontSize(36)
			// .text("百度地图SDK")
			// .position(point);
			// //在地图上添加该文字对象并显示
			// mBaiduMap.addOverlay(textOption);

		}
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker mar) {
				// TODO Auto-generated method stub
				mar.getExtraInfo();
				UIUtil.ToastMessage(appContext, mar.getTitle()
						+ "marker clicked!");
				return false;
			}
		});
	}

	/**
	 * 定位SDK监听函数
	 * 
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

				mBaiduMap.animateMapStatus(MapStatusUpdateFactory
						.newMapStatus(builder.build()));
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {

		}
	}

}
