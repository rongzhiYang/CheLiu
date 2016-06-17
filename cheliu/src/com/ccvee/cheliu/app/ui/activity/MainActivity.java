package com.ccvee.cheliu.app.ui.activity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.ccvee.cheliu.R;
import com.ccvee.cheliu.app.ui.fragment.BaoyangFragment;
import com.ccvee.cheliu.app.ui.fragment.CheweiFragment;
import com.ccvee.cheliu.app.ui.fragment.WodeFragment;
import com.ccvee.cheliu.app.ui.fragment.XicheFragment;
import com.ccvee.cheliu.app.utils.PrefUtils;

public class MainActivity extends BaseFragmentActivity {
	private int currIndex;
	private TextView textView1, textView2, textView3, textView4;
	private ViewPager appSplash;
	
	private static CheweiFragment fragment1;
	private static XicheFragment fragment2;
	private static BaoyangFragment fragment3;
	private static WodeFragment fragment4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		 
        
		setContentView(R.layout.activity_main);
		appSplash = (ViewPager) findViewById(R.id.splash_viewpager);
		if (!PrefUtils.getBoolean(appContext, "APP_IS_FIRST_START", false)) {
			PrefUtils.setBoolean(appContext, "APP_IS_FIRST_START", true);
			appSplash.setVisibility(View.VISIBLE);
			initSplashData();
		}
		
		textView1 = (TextView) findViewById(R.id.main_fragment_chewei);
		textView2 = (TextView) findViewById(R.id.main_fragment_xiche);
		textView3 = (TextView) findViewById(R.id.main_fragment_baoyang);
		textView4 = (TextView) findViewById(R.id.main_fragment_wode);

		textView1.setOnClickListener(this);
		textView2.setOnClickListener(this);
		textView3.setOnClickListener(this);
		textView4.setOnClickListener(this);
		setTabSelection(0);
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
		switch (v.getId()) {
		case R.id.main_fragment_chewei:
			if (currIndex != 0)
				setTabSelection(0);
			break;
		case R.id.main_fragment_xiche:
			if (currIndex != 1)
				setTabSelection(1);
			break;
		case R.id.main_fragment_baoyang:
			if (currIndex != 2)
				setTabSelection(2);
			break;
		case R.id.main_fragment_wode:
			if (currIndex != 3)
				setTabSelection(3);
			break;
		default:
			break;
		}
	}

	public void initSplashData() {
		int[] mImageIds = new int[] {};// R.drawable.guide
		List<View> linked = new LinkedList<View>();
		for (int j = 0; j < mImageIds.length; j++) {
			ImageView img = new ImageView(activity);
			img.setImageResource(mImageIds[j]);
			linked.add(img);
		}

		appSplash.setAdapter(new ViewPagerAdapter(linked));
	}
	
	private void setTabSelection(int index) {		
		currIndex = index;
		FragmentTransaction transaction = fragmentManager.beginTransaction();// 开启一个Fragment事务		
		hideFragments(transaction);// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		
		textView1.setTextColor(getResources().getColor(R.color.color_64));
		textView2.setTextColor(getResources().getColor(R.color.color_64));
		textView3.setTextColor(getResources().getColor(R.color.color_64));
		textView4.setTextColor(getResources().getColor(R.color.color_64));
		Drawable drawable1 = getResources().getDrawable(R.drawable.menu_1);
		drawable1.setBounds(0, 0, drawable1.getMinimumWidth(),
				drawable1.getMinimumHeight());
		textView1.setCompoundDrawables(null, drawable1, null, null);
		Drawable drawable2 = getResources().getDrawable(R.drawable.menu_2);
		drawable2.setBounds(0, 0, drawable2.getMinimumWidth(),
				drawable2.getMinimumHeight());
		textView2.setCompoundDrawables(null, drawable2, null, null);
		Drawable drawable3 = getResources().getDrawable(R.drawable.menu_3);
		drawable3.setBounds(0, 0, drawable3.getMinimumWidth(),
				drawable3.getMinimumHeight());
		textView3.setCompoundDrawables(null, drawable3, null, null);
		Drawable drawable4 = getResources().getDrawable(R.drawable.menu_4);
		drawable4.setBounds(0, 0, drawable4.getMinimumWidth(),
				drawable4.getMinimumHeight());
		textView4.setCompoundDrawables(null, drawable4, null, null);
		switch (index) {
		case 0:
			// 如果MessageFragment为空，则创建一个并添加到界面上
			// 如果MessageFragment不为空，则直接将它显示出来	
			if (fragment1 == null) {					
				fragment1 = new CheweiFragment();
				transaction.add(R.id.main_viewpager, fragment1);
			} else {				
				transaction.show(fragment1);
			}
			textView1.setTextColor(getResources()
					.getColor(R.color.color_e95847));
			drawable1 = getResources().getDrawable(R.drawable.menu_1_hover);
			drawable1.setBounds(0, 0, drawable1.getMinimumWidth(),
					drawable1.getMinimumHeight());
			textView1.setCompoundDrawables(null, drawable1, null, null);
			break;
		case 1:
			if (fragment2 == null) {				
				fragment2 = new XicheFragment();
				transaction.add(R.id.main_viewpager, fragment2);
			} else {				
				transaction.show(fragment2);
			}
			textView2.setTextColor(getResources()
					.getColor(R.color.color_e95847));
			drawable2 = getResources().getDrawable(R.drawable.menu_2_hover);
			drawable2.setBounds(0, 0, drawable2.getMinimumWidth(),
					drawable2.getMinimumHeight());
			textView2.setCompoundDrawables(null, drawable2, null, null);
			break;
		case 2:
			if (fragment3 == null) {				
				fragment3 = new BaoyangFragment();
				transaction.add(R.id.main_viewpager, fragment3);
			} else {				
				transaction.show(fragment3);
			}
			textView3.setTextColor(getResources()
					.getColor(R.color.color_e95847));
			drawable3 = getResources().getDrawable(R.drawable.menu_3_hover);
			drawable3.setBounds(0, 0, drawable3.getMinimumWidth(),
					drawable3.getMinimumHeight());
			textView3.setCompoundDrawables(null, drawable3, null, null);
			break;
		case 3:
			if (fragment4 == null) {
				fragment4 = new WodeFragment();
				transaction.add(R.id.main_viewpager, fragment4);
			} else {
				transaction.show(fragment4);
			}
			textView4.setTextColor(getResources()
					.getColor(R.color.color_e95847));
			drawable4 = getResources().getDrawable(R.drawable.menu_4_hover);
			drawable4.setBounds(0, 0, drawable4.getMinimumWidth(),
					drawable4.getMinimumHeight());
			textView4.setCompoundDrawables(null, drawable4, null, null);
			break;
		}
		transaction.commit();
	}
	
	private void hideFragments(FragmentTransaction transaction) {
		if (fragment1 != null) {
			transaction.hide(fragment1);
		}
		if (fragment2 != null) {
			transaction.hide(fragment2);
		}
		if (fragment3 != null) {
			transaction.hide(fragment3);
		}
		if (fragment4 != null) {
			transaction.hide(fragment4);
		}
	}

	public class ViewPagerAdapter extends PagerAdapter {		
		private List<View> views;// 界面列表
		public ViewPagerAdapter(List<View> views) {
			this.views = views;
		}		
		@Override	// 销毁arg1位置的界面
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(views.get(arg1));
		}		
		@Override	// 获得当前界面数
		public int getCount() {
			if (views != null) {
				return views.size();
			}
			return 0;
		}	
		@Override	// 初始化arg1位置的界面
		public Object instantiateItem(View arg0, int arg1) {

			((ViewPager) arg0).addView(views.get(arg1), 0);

			return views.get(arg1);
		}		
		@Override	// 判断是否由对象生成界面
		public boolean isViewFromObject(View arg0, Object arg1) {
			return (arg0 == arg1);
		}
	}
}
