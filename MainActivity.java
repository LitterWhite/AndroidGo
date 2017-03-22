package com.zhongtianbao.mysocketclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.chat);
		btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent();
		intent.setClass(MainActivity.this, MySocketClientAcitivity.class);
		this.startActivity(intent);	
	}
}
