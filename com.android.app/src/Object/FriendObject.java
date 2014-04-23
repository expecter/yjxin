package Object;

import org.json.JSONException;
import org.json.JSONObject;

public class FriendObject {
	public void load(JSONObject change){
		try {
			if(!change.isNull("name")){
			setName(change.getString("name"));
			}
			if(!change.isNull("account")){
			setAccount(change.getInt("account"));
			}
			if(!change.isNull("id")){
			setId(change.getInt("id"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private int id=0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAccount() {
		return account;
	}
	public void setAccount(int account) {
		this.account = account;
	}
	private String name="";
	private int account=0;
}
