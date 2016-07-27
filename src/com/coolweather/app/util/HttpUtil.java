package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.coolweather.app.activity.WeatherActivity;

import android.os.Message;
import android.util.Log;


public class HttpUtil {
	
	public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
		
		new Thread(new Runnable() {	
			@Override
			public void run() {
				// TODO 自动生成的方法存根
				Log.d("HttpUtil", "start thread");     //**********************
				HttpURLConnection connection=null;
				try {
					URL url=new URL(address);
					connection=(HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setReadTimeout(8000);
					connection.setConnectTimeout(8000);
					InputStream in=connection.getInputStream();            //!!!!!!!!!!!!!!!!
					BufferedReader reader=new BufferedReader(new InputStreamReader(in));
					StringBuilder response=new StringBuilder();
					String line;
					while((line=reader.readLine())!=null){
						response.append(line);
						Log.d("HttpUtil", response.toString());
					}
					Log.d("HttpUtil", "9");
					if(listener!=null){
						//回调onFinish()方法
						listener.onFinish(response.toString());
					}	
	
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					Log.d("HttpUtil", e.toString());  //***********************
					if(listener!=null){
						//回掉onListener()方法
						listener.onError(e);
					}
				}finally{
					if(connection!=null){
						connection.disconnect();
					}
					Log.d("HttpUtil", "finish thread");  //***********************
				}
			}
		}).start();
	}
	
	public static void sendHttpRequestByHttpClient(final String address,final HttpCallbackListener listener){
		
		new Thread(new Runnable() {
			public void run() {
				
				try {
					HttpClient httpClient=new DefaultHttpClient();
					HttpGet httpGet=new HttpGet(address);
					HttpResponse httpResponse=httpClient.execute(httpGet);
					
					if(httpResponse.getStatusLine().getStatusCode()==200){
						HttpEntity entity=httpResponse.getEntity();
						String response=EntityUtils.toString(entity, "utf-8");
						listener.onFinish(response);
					}else{
						listener.onError(new Exception());
					}
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					listener.onError(e);
				} 
			}
		}).start();
		
	}
	
}
