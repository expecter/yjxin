package com.android.app;

import java.util.List;

import support.CallBackListener;
import support.Observer;
import manager.Manager;
import manager.MessageType;
import Object.FriendObject;
import Object.playerObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class IndexActivity extends Activity {
	private TextView UserNameText;
	private TextView PassWordText;
	private TableLayout m_tableLayout;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.indexlayout);
		context = this;
		playerObject player = Manager.getInstance().getPlayer();
		UserNameText = (TextView)findViewById(R.id.username);
		UserNameText.setText(player.getUserName());
		PassWordText = (TextView)findViewById(R.id.password);
		PassWordText.setText(player.getUserword());
		m_tableLayout = (TableLayout)findViewById(R.id.messageTl);
		Observer.getObserver().addObserver(MessageType.Request_Friend_List, updateFriendList);
		Observer.getObserver().addObserver(MessageType.Request_Friend_Detail, FriendDetail);
		Manager.getInstance().getFriendsManager().requestFriendList();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}
	
	private CallBackListener updateFriendList = new CallBackListener(){

		@Override
		public void callback(Object object) {
			// TODO Auto-generated method stub
			List<FriendObject> friendlist= (List)object;
			for(FriendObject obj:friendlist){
				TableRow row = new TableRow(IndexActivity.this);
				m_tableLayout.addView(row);
				TextView idView = new TextView(IndexActivity.this);
				idView.setText(Integer.toString(obj.getId()));  
				row.addView(idView);	
				TextView nameView = new TextView(IndexActivity.this);
				nameView.setText(obj.getName());  
				row.addView(nameView);
				TextView accountView = new TextView(IndexActivity.this);
				accountView.setText(Integer.toString(obj.getAccount()));  
				row.addView(accountView);
				Button button = new Button(IndexActivity.this);
				button.setText(R.string.detail);
				button.setTag(obj.getId());
				button.setOnClickListener(clicklistener);
				row.addView(button);
				System.out.println(obj.getName());
			}
		}
		
	};
	private CallBackListener FriendDetail = new CallBackListener() {
		
		@Override
		public void callback(Object object) {
			// TODO Auto-generated method stub
			System.out.println(((FriendObject)object).getName());
			FriendObject obj = (FriendObject)object;
			String str = "id = "+obj.getId()+"\n"
						+"username="+obj.getName()+"\n"
						+"account="+obj.getAccount();
			AlertDialog alertDialog = new AlertDialog.Builder(IndexActivity.this).
					setTitle(R.string.detail).
					setMessage(str).
					setPositiveButton("确定", null).
					create();
			alertDialog.show();
			
		}
	};
	private OnClickListener clicklistener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = (Integer) v.getTag();
			Manager.getInstance().getFriendsManager().requestFriendDetail(id);
		}
		
	};
	protected void onDestroy() {
		System.out.println("================= IndexActivity onDestroy");
		super.onDestroy();
		Observer.getObserver().removeObserver(MessageType.Request_Friend_List);
		Observer.getObserver().removeObserver(MessageType.Request_Friend_Detail);
	};

}
