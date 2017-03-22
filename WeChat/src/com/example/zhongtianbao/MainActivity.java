package com.example.zhongtianbao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;

public class MainActivity extends Activity {
	
	public static final String APP_ID = "wxa05bafe85b9e803f";
	public IWXAPI api;
	CheckBox mShareFriends;
/**/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		api.registerApp(APP_ID);
		mShareFriends = (CheckBox)findViewById(R.id.checkBox);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
/*	
 * 	public void onClick_Launch_WeChat(View arg0) {
		System.out.println("啊啊啊啊啊啊啊");
		api.openWXApp();
	}
	
	public void onClick_Send_Text(View arg0) {
		final EditText editor = new EditText(this);
		editor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
		editor.setText("默认的分享文本");
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(android.R.drawable.ic_dialog_alert);
		builder.setTitle("共享文本");
		builder.setView(editor);
		builder.setMessage("请输入要分享的文件");
		System.out.println("10");
		builder.setPositiveButton("分享", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				System.out.println("11");
				String text = editor.getText().toString();
				if(text == null){
					return;
				}
				System.out.println("哈哈哈哈哈");
				WXTextObject textObj = new WXTextObject();
				textObj.text = text;
				System.out.println("1");
				WXMediaMessage msg = new WXMediaMessage();
				msg.mediaObject = textObj;
				msg.description = text;
				System.out.println("2");
				SendMessageToWX.Req req = new SendMessageToWX.Req();
				req.message = msg;
				System.out.println("3");
				req.transaction = buildTransaction("text");
				System.out.println("4");
				req.scene = mShareFriends.isChecked()?SendMessageToWX.Req.WXSceneTimeline:SendMessageToWX.Req.WXSceneSession;
				System.out.println("5");
				api.sendReq(req);
			}
		});
		builder.setNegativeButton("取消", null);
		final AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void onClick_Send_Bitmap(View arg0){
		System.out.println("#####################");
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.send_img);
		WXImageObject imgObj = new WXImageObject(bmp);
		System.out.println("*************************");
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj;
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&");
		Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 70, 130, true);
		bmp.recycle();
		msg.thumbData = bmpToByteArray(thumbBmp, true);  // 设置缩略图
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("img");
		req.message = msg;
		req.scene = mShareFriends.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
		api.sendReq(req);
		System.out.println("(((((((((((((((())))))))))))))");
		finish();
	}
	
	public void onClick_Send_Localimg(View arg0) {
		String path = SDCARD_ROOT + "/test.png";
		File file = new File(path);
		if (!file.exists()) {
			Toast.makeText(this,  " 文件不存在，path = " + path, Toast.LENGTH_LONG).show();
			return;
		}
		
		WXImageObject imgObj = new WXImageObject();
		imgObj.setImagePath(path);
		
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj;
		
		Bitmap bmp = BitmapFactory.decodeFile(path);
		Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 70, 130, true);
		bmp.recycle();
		msg.thumbData = bmpToByteArray(thumbBmp, true);
		
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("img");
		req.message = msg;
		req.scene = mShareFriends.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
		api.sendReq(req);
		
		finish();
	}
*/	
	public void onClick_Send_Url(View arg0) {
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "http://www.baidu.com";
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = "WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
		msg.description = "WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
		Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		msg.thumbData = bmpToByteArray(thumb, true);
		
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = mShareFriends.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
		api.sendReq(req);
		
		finish();
	}
	
}
