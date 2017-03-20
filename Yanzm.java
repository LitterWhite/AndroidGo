package com.example.yanzm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class Yanzm implements Runnable {
	
	public void sendmessage(){
		
        String phonenum="15150666208";					Random yanzm = new Random();		
        StringBuffer content = new StringBuffer();		int i;
        
        //输入手机号码

        for( i=0;i<6;i++ )
        {
        	int j = yanzm.nextInt(10);
        	content.append(j);
        }
        
        //输出验证码content

        String httpUrl = "http://apis.baidu.com/kingtto_media/106sms/106sms";
        String httpArg = "mobile="+phonenum+"&content=%E3%80%90%E5%86%9C%E7%94%B0%E5%AE%9D%E3%80%91%E5%B0%8A%E6%95%AC%E7%9A%84%E7%94%A8%E6%88%B7%E6%82%A8%E5%A5%BD%EF%BC%8C%E6%82%A8%E7%9A%84%E9%AA%8C%E8%AF%81%E7%A0%81%E6%98%AF%EF%BC%9A"+content;
        
        String jsonResult = request(httpUrl, httpArg);
        System.out.println(jsonResult);
		
	}
	
	public static String request(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?" + httpArg;

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        // 填入apikey到HTTP header
	        connection.setRequestProperty("apikey",  "1b51fb09236edc4ed8c1eef6d0f2cd04");
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.sendmessage();
	}

}
