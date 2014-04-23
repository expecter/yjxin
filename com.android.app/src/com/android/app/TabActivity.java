package com.android.app;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabActivity extends ActivityGroup {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabslayout);
		TabHost tabhost = (TabHost)findViewById(android.R.id.tabhost);
		tabhost.setup();
		tabhost.setup(this.getLocalActivityManager());  
        tabhost.addTab(tabhost.newTabSpec("tab1").setIndicator("选项卡一")
        		.setContent(new Intent(this,noticeActivity.class)));
        tabhost.addTab(tabhost.newTabSpec("tab2").setIndicator("选项卡二")
        		.setContent(new Intent(this,IndexActivity.class)));
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}
}
