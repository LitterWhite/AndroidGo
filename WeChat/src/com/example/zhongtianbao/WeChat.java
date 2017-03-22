//必看！！
//Activity中
//	public void test(){
//		CheckBox mShareFriends = (CheckBox)findViewById(R.id.checkBox);
//		WeChat wechat = new WeChat(this, mShareFriends);
//		Button btn = (Button)findViewById(R.id.sendUrlBtn);
//		btn.setOnClickListener(wechat);
//	}

//AndroidManifest中
//	<uses-permission android:name="android.permission.INTERNET"/>
//	<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
//	<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
//	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
//	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

//xml中
//    <CheckBox
//       android:id="@+id/checkBox"
//        android:layout_width="wrap_content"
//        android:layout_height="wrap_content"
//        android:layout_alignParentLeft="true"
//        android:layout_alignParentTop="true"
//        android:layout_marginTop="66dp"
//        android:text="发送到朋友圈" />

//    <Button
//        android:id="@+id/sendUrlBtn"
//        android:layout_width="wrap_content"
//        android:layout_height="wrap_content"
//        android:layout_alignParentLeft="true"
//        android:layout_alignParentRight="true"
//        android:layout_below="@+id/linearLayout1"
//        android:text="发送网页" />

//网址，标题，描述，缩略图等在下方注释处修改

package com.example.zhongtianbao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WeChat implements OnClickListener {
	
	public static final String APP_ID = "wxa05bafe85b9e803f";
	public IWXAPI api;
	View mView;
	Context mContext;
	CheckBox mShareFriends;
	
	WeChat(Context context, CheckBox checkbox){
		mShareFriends =checkbox;
		mContext = context;
		api = WXAPIFactory.createWXAPI(mContext, APP_ID);
		api.registerApp(APP_ID);
	}
	
	private String buildTransaction(final String type){
		return(type == null)?String.valueOf(System.currentTimeMillis()):type+System.currentTimeMillis();
	}
	
	private byte[] bmpToByteArray(final Bitmap bitmap,final boolean needRecycle){
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG,100,output);
		if(needRecycle){
			bitmap.recycle();
		}
		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void onClick(View arg0) {
		WXWebpageObject webpage = new WXWebpageObject();
		//设置url的网址
		webpage.webpageUrl = "http://www.baidu.com";
		WXMediaMessage msg = new WXMediaMessage(webpage);
		//设置网站描述信息的标题
		msg.title = "WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
		//设置网站描述信息
		msg.description = "WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
		//设置网站的缩略图
		Bitmap thumb = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
		msg.thumbData = bmpToByteArray(thumb, true);
		
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = mShareFriends.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
		api.sendReq(req);
	}

}
