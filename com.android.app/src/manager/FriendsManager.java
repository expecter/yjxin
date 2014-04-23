package manager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Object.FriendObject;

import com.android.app.http.HttpConnection;

import support.CallBackListener;
import support.Observer;

public class FriendsManager {
	public FriendsManager(){
		Observer.getObserver().addObserver(MessageType.Friend_request_Type, handlefriendList);
		Observer.getObserver().addObserver(MessageType.Friend_Detail_request, handleFriendDetail);
	}
	
	public void requestFriendList(){
		JSONObject obj = new JSONObject();
		HttpConnection.getConnection().post(obj,MessageType.Friend_request_Type);
	}
	public void requestFriendDetail(int id){
		JSONObject obj = new JSONObject();
		try {
			obj.put("id", id);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpConnection.getConnection().post(obj,MessageType.Friend_Detail_request);
	}
	private CallBackListener handlefriendList = new CallBackListener(){

		@Override
		public void callback(Object object) {
			// TODO Auto-generated method stub
			List<FriendObject> friendlist = new ArrayList<FriendObject>();
			JSONObject jsonObject =(JSONObject)object;
			try {
				JSONArray array = jsonObject.getJSONArray("friendList");
				for(int i = 0;i<array.length();i++){
					FriendObject obj = new FriendObject();
					obj.load(array.optJSONObject(i));
					friendlist.add(obj);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Observer.getObserver().postNotification(MessageType.Request_Friend_List,friendlist);
			System.out.println("success");
		}
		
	};
	private CallBackListener handleFriendDetail = new CallBackListener(){

		@Override
		public void callback(Object object) {
			// TODO Auto-generated method stub
			FriendObject obj = new FriendObject();
			obj.load((JSONObject)object);
			Observer.getObserver().postNotification(MessageType.Request_Friend_Detail,obj);
		}
		
	};
}
