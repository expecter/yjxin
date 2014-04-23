package manager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import support.CallBackListener;
import support.Observer;
import Object.FriendObject;

import com.android.app.http.HttpConnection;

public class PlayerManager {
	PlayerManager(){
		Observer.getObserver().addObserver(MessageType.LoginType, callback);
	}
	public void requestLogin(String username,String password){
		JSONObject obj = new JSONObject();
		try {
			obj.put("username", username);
			obj.put("password", password);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpConnection.getConnection().post(obj,MessageType.LoginType);
	}
	private CallBackListener callback = new CallBackListener(){

		@Override
		public void callback(Object object) {
			// TODO Auto-generated method stub
			JSONObject jsonObject =(JSONObject)object;
			try {
				
				Manager.getInstance().getPlayer().load(jsonObject.getJSONObject("player"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Observer.getObserver().postNotification(MessageType.Request_Login_Msg);
			System.out.println("success");
		}
		
	};
	
}
