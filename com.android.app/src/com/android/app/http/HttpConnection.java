package com.android.app.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import support.Observer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class HttpConnection implements Runnable {

	private String httpUrl = "http://192.168.1.102:8080/androidserver/servlet/friendfship";
	public static final int DID_START = 0;
	public static final int DID_ERROR = 1;
	public static final int DID_SUCCEED = 2;
	private static final int GET = 0;
	private static final int POST = 1;
	private static final int PUT = 2;
	private static final int DELETE = 3;
	private static final int BITMAP = 4;
	private String url;
	private int method;
	private JSONObject data;
	private int messageId=1;
	private HttpClient httpClient;
	private HttpConnection(){};
	private static HttpConnection connection;
	public static HttpConnection getConnection(){
		if(connection==null){
			connection = new HttpConnection();
			return connection;
		}
		return connection;
	}
	public void create(int method,String url,JSONObject data,int messageId){
		this.method = method;
		this.url = url;
		this.data = data;
		this.messageId = messageId;
		ConnectionManager.getInstance().push(this);
	}
	public void get(int messageId){
		create(GET,httpUrl,null,messageId);
	}
	public void post(JSONObject data,int messageId){
		create(POST,httpUrl,data,messageId);
	}
	public void put(JSONObject data,int messageId){
		create(PUT,httpUrl,data,messageId);
	}
	private static final Handler handler = new Handler(){
		public void handleMessage(Message message) {
			switch(message.what){
			case HttpConnection.DID_START:{
				break;
			}
			case HttpConnection.DID_SUCCEED:{
				Object data = message.getData();
				if(data != null){
					Bundle bundle = (Bundle)data;
					String result = bundle.getString("callbackkey");
					System.out.println("result"+result);
					JSONObject json=null ;
					try {
						json= new JSONObject(result);
						int messageId = json.getInt("mId");
						Observer.getObserver().postNotification(messageId, json.getJSONObject("data"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			case HttpConnection.DID_ERROR:{
				break;
			}
			}
		};
	};
	public void run() {
		// TODO Auto-generated method stub
		JSONObject obj = new JSONObject();
		try {
			obj.put("data",data);
			obj.put("messageId", messageId);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String jsonData = obj.toString();
		httpClient = getHttpClient();
		try{
			HttpResponse httpResponse = null;
			switch(method){
			case GET:
				httpResponse = httpClient.execute(new HttpGet(
						url));
				break;
			case POST:
				HttpPost httpPost = new HttpPost(url);
				StringBuilder sb = new StringBuilder();
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				
				BasicNameValuePair valuesPair = new BasicNameValuePair("arg", jsonData);
				params.add(valuesPair);
				httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
				httpResponse = httpClient.execute(httpPost);
				if(isHttpSuccessExcuted(httpResponse)){
					//String result = EntityUtils.toString(httpResponse.getEntity());
					InputStream instream = httpResponse.getEntity().getContent();
					BufferedReader readers = new BufferedReader(new InputStreamReader(instream,"UTF-8"));
					String line = null;
					while ((line = readers.readLine())!=null)
					{
						sb.append(line);
					}
					this.sendMessage(sb.toString());
				}else{
					this.sendMessage("fail");
				}
				break;
			}
			
		}catch(Exception e){
			this.sendMessage("fail");
		}
		ConnectionManager.getInstance().didComplete(this);
	}
	
	private void sendMessage(String result){
		Message message = Message.obtain(handler,DID_SUCCEED);
		Bundle data = new Bundle();
		data.putString("callbackkey", result);
		message.setData(data);
		handler.sendMessage(message);
	}
	
	public static boolean isHttpSuccessExcuted(HttpResponse response){
		int statusCode = response.getStatusLine().getStatusCode();
		return(statusCode > 199)&&(statusCode < 400);
	}
    public static DefaultHttpClient getHttpClient(){
    	HttpParams httpParams = new BasicHttpParams();
    	HttpConnectionParams.setConnectionTimeout(httpParams, 20000);
    	HttpConnectionParams.setSoTimeout(httpParams, 20000);
    	DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
    	return httpClient;
    }
}
