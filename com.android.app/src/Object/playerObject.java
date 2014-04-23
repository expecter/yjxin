package Object;

import org.json.JSONException;
import org.json.JSONObject;


public class playerObject {
	private String userName="";
	public String getUserName() {
		return userName;
	}
	public String getUserword() {
		return userword;
	}
	public int getId() {
		return id;
	}
	private String userword="";
	private int id=0;
	public void load(JSONObject change){
		try {
			if(!change.isNull("username")){
			userName = change.getString("username");
			}
			if(!change.isNull("password")){
			userword = change.getString("password");
			}
			if(!change.isNull("userId")){
			id = change.getInt("userId");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
