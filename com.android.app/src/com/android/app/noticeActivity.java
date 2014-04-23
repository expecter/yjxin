package com.android.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class noticeActivity extends Activity {
	private LinearLayout mLayout;
	private ScrollView sView;
	private final Handler mHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.noticelayout);
		mLayout = (LinearLayout)findViewById(R.id.indexLinearLayout);
		sView = (ScrollView)findViewById(R.id.indexScrollView);
		Button mBtn = (Button)findViewById(R.id.Button);
		mBtn.setOnClickListener(mClickListener);
	}
	
	private OnClickListener mClickListener = new OnClickListener(){
		private int index =1;
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			TextView tView = new TextView(noticeActivity.this);
			tView.setText("TextView"+index);
			 //设置线性布局的属性  
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(  
                    LinearLayout.LayoutParams.FILL_PARENT,  
                    LinearLayout.LayoutParams.WRAP_CONTENT); 
            mLayout.addView(tView, params);//添加一个TextView控件  
            Button button = new Button(noticeActivity.this);//定义一个Button  
            button.setText("Button" + index);//设置Button的文本信息  
            button.setId(index++);  
            mLayout.addView(button, params);//添加一个Button控件  
            mHandler.post(mScrollToButton);//传递一个消息进行滚动  
		}
		
	};
	private Runnable mScrollToButton = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			 int off = mLayout.getMeasuredHeight() - sView.getHeight();  
	            if (off > 0) {  
	                sView.scrollTo(0, off);//改变滚动条的位置  
	            }  
	         
		}
		
	};
}
