package com.ccvee.cheliu.app.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccvee.cheliu.R;

/**
 * CommonDialog
 * @author  rongzhi.Yang
 * @data    2016-5-23 
 * @version 1.0
 */
public class CommonDialog extends Dialog implements OnClickListener{
	@SuppressWarnings("unused")
	private Context activity;
	private LinearLayout conentLayout;
	private View titleLine,sureLine;
	private TextView title,content,ok,cancel;

	public CommonDialog(Activity activity) {
		super(activity);
		this.activity = activity;
		this.setCanceledOnTouchOutside(false);
	}

	public CommonDialog(Context cont, boolean cancelable) {
		super(cont, R.style.styleDialog);
		this.activity = cont;
		this.setCanceledOnTouchOutside(cancelable);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.common_dialog);
		
		conentLayout = (LinearLayout) findViewById(R.id.common_dialog_content_layout);
		titleLine = findViewById(R.id.common_dialog_titlepart);
		sureLine = findViewById(R.id.common_dialog_surepart);
		title = (TextView) findViewById(R.id.common_dialog_title);
		content = (TextView) findViewById(R.id.common_dialog_content);
		ok = (TextView) findViewById(R.id.common_dialog_ok);
		cancel = (TextView) findViewById(R.id.common_dialog_cancel);
		ok.setTag(this);
		cancel.setTag(this);
	}
	
	public void addContent(View view){
		conentLayout.removeAllViewsInLayout();
		conentLayout.addView(view, conentLayout.getChildCount());
	}
	
	public void show(String title,String content,String ok,String cancel, android.view.View.OnClickListener okl,android.view.View.OnClickListener cancell) {
		show(true, true, true, title, content, ok, cancel, okl, cancell);
	}
	
	public void show(boolean titlevi, boolean contentvi, boolean cancelvi,String title,String content,String ok,String cancel, android.view.View.OnClickListener okl,android.view.View.OnClickListener cancell) {
		super.show();
		this.title.setText(title);
		this.content.setText(Html.fromHtml(content));
		this.ok.setText(ok);
		this.cancel.setText(cancel);
		this.ok.setOnClickListener(okl);
		this.cancel.setOnClickListener(cancell);
		if(!titlevi){this.title.setVisibility(View.GONE);this.titleLine.setVisibility(View.INVISIBLE);}
		if(!contentvi){this.conentLayout.setVisibility(View.GONE);this.titleLine.setVisibility(View.INVISIBLE);}
		if(!cancelvi){this.cancel.setVisibility(View.GONE);this.sureLine.setVisibility(View.GONE);}
		
	}	

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		default:
			this.cancel();
			break;
		}
		
	}

}
