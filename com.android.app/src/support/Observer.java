package support;

import java.util.ArrayList;

public class Observer {
	private static Observer observer;
	private Observer(){};
	private ArrayList<ObserverObject> notifylist = new ArrayList<ObserverObject>();
	public static Observer getObserver(){
		if(observer == null){
			observer = new Observer();
			return observer;
		}
		return observer;
	}
	public void addObserver(String Notification,CallBackListener m_Listener){
		for(ObserverObject observer:notifylist){
			if(observer.getName().equals(Notification)){
				return;
			}
		}
		ObserverObject notifyObject = new ObserverObject(Notification,m_Listener);		
		notifylist.add(notifyObject);
	}
	public void addObserver(int Notification,CallBackListener listener){
		addObserver(Integer.toString(Notification), listener);
	}
	
	public void removeObserver(String Notification){
		for(ObserverObject observer:notifylist){
			if(observer.getName().equals(Notification)){
				notifylist.remove(observer);
				return;
			}
		}
	}
	public void postNotification(int Notification,Object object){
		postNotification(Integer.toString(Notification),object);
	}
	public void postNotification(String Notification){
		postNotification(Notification, null);
	}
	public void postNotification(String Notification,Object object){
		for(ObserverObject observer:notifylist){
			if(observer.getName().equals(Notification)){
				observer.getListener().callback(object);
			}
		}
	}
	
	public class ObserverObject{
		private String m_Name;
		private CallBackListener m_Listener;
		public ObserverObject(String name,CallBackListener listener){
			this.m_Name = name;
			this.m_Listener = listener;
		}
		public String getName(){
			return m_Name;
		}
		public CallBackListener getListener(){
			return m_Listener;
		}
	}
}

