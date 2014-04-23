package manager;

import Object.noticeObject;
import Object.playerObject;

public class Manager {
	private static Manager instance;
	private Manager(){}
	public static Manager getInstance(){
		if(instance == null)
		{
			instance = new Manager();
			instance.init();
			return instance;
		}
		return instance;
	}
	private PlayerManager playerManager;
	private FriendsManager friendsManager;
	private playerObject player;
	public FriendsManager getFriendsManager(){
		return friendsManager;
	}
	public PlayerManager getPlayerManager() {
		return playerManager;
	}
	public playerObject getPlayer(){
		return player;
	}
	private boolean init(){
		playerManager = new PlayerManager();
		friendsManager = new FriendsManager();
		player = new playerObject();
		return true;
	}
	public void purgeManager(){
		instance = null;
	}
}
