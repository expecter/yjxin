package com.android.app;

import support.CallBackListener;
import support.Observer;

import manager.Manager;
import manager.MessageType;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity{

	private EditText passworldEdit;
	private EditText usernameEdit;
	private Button loginButton;
	private Button testButton;
	private static Context context;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.mainlayout);
		usernameEdit = (EditText)findViewById(R.id.editText1);
		passworldEdit = (EditText)findViewById(R.id.editText2);
		loginButton = (Button)findViewById(R.id.button1);
		loginButton.setOnClickListener(loginListener);
		testButton = (Button)findViewById(R.id.button2);
		testButton.setOnClickListener(testListener);
		context = this;
		
		Manager.getInstance();
		Observer.getObserver().addObserver(MessageType.Request_Login_Msg, callback);
	}
	private OnClickListener testListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//String result = Manager.getInstance().getNotice().getTitle();
			Toast.makeText(context, Manager.getInstance().getPlayer().getUserName(), Toast.LENGTH_LONG).show();
		}
	};
	private OnClickListener loginListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.out.println("LoginButton Clicked");
			Manager.getInstance().getPlayerManager().requestLogin(usernameEdit.getText().toString()
						, passworldEdit.getText().toString());
		}	
		
	};
	//登陆回调
	private CallBackListener callback = new CallBackListener() {
		
		@Override
		public void callback(Object object) {
			// TODO Auto-generated method stub
			String username = Manager.getInstance().getPlayer().getUserName();
			Toast.makeText(context, username, Toast.LENGTH_LONG).show();
			Intent intent = new Intent(MainActivity.this,TabActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			startActivity(intent);
			finish();
		}
	};
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Observer.getObserver().removeObserver(MessageType.Request_Login_Msg);
		System.out.println("=================onDestroy");
		Manager.getInstance().purgeManager();
	}
}
