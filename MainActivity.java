package com.example.yanzm;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	public static final int MSG_RECEIVED_CODE = 1;
	
	private EditText et_ValidateCode = null;
	
	TextView v;
	Button btn;
	SmsObserver mObserver;
	Handler mHandler = new Handler(){
		
		@Override
		public void handleMessage(Message msg){
			if(msg.what == MSG_RECEIVED_CODE);
			String code = (String)msg.obj;
			et_ValidateCode = (EditText)findViewById(R.id.et_ValidateCode);
			et_ValidateCode.setText(code);
		}
		
	};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yanzm); 
        btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(this);
        
        mObserver = new SmsObserver(MainActivity.this,mHandler);
        Uri uri = Uri.parse("content://sms");
        getContentResolver().registerContentObserver(uri, true, mObserver);
  
		v = (TextView)findViewById(R.id.textView1);
		v.setText("fuck");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		TimeCountUtil timeCountUtil = new TimeCountUtil(this,60000,1000,btn);
		timeCountUtil.start();
		Yanzm send = new Yanzm();
		Thread t1 = new Thread(send);
		t1.start();
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		getContentResolver().unregisterContentObserver(mObserver);
	}

}
