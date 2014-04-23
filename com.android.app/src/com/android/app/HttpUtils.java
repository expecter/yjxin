package com.android.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;


public class HttpUtils {
	public static String getData(String url,HttpEntity entity){
		StringBuilder sb = new StringBuilder();
		HttpPost request = new HttpPost(url);
		HttpClient httpClient =new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 3000);
		request.setEntity(entity);		
		HttpResponse response;
		try{
			
			response = httpClient.execute(request);
			
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
			{
				InputStream instream = response.getEntity().getContent();
				BufferedReader readers = new BufferedReader(new InputStreamReader(instream,"UTF-8"));
				String line = null;
				while ((line = readers.readLine())!=null)
				{
					sb.append(line);
				}
				return sb.toString();
			}
			else
			{
				return "login fail";
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return "server out of time";
	}
	public static String getDataWithparam(String url,List<NameValuePair> params)
	{
		try {
			HttpEntity entity= new UrlEncodedFormEntity(params,"UTF-8");
			return getData(url, entity);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
