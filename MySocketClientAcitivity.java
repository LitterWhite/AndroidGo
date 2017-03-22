//所需权限：<uses-permission android:name="android.permission.INTERNET"/>
//UI的操作在下方注释处
package com.zhongtianbao.mysocketclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MySocketClientAcitivity extends Activity {

	Button sendbtn;
	TextView connect;
//	EditText ipedit;
	EditText sendedit;
	TextView t;
	String time;
//	TextView textView;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private MsgAdapter adapter;  
    private List<Msg> msgList = new ArrayList<Msg>();
    private ListView msgListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_socket_client);
		init();
		adapter = new MsgAdapter(MySocketClientAcitivity.this, R.layout.list_item, msgList);
        msgListView = (ListView) findViewById(R.id.msg_list_view);  
        msgListView.setAdapter(adapter); 
		connect();
		//发送按钮的点击事件
		sendbtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				send();
			}

		});
		//连接按钮的点击事件
	}

	//各种控件的初始化
	public void init(){
		sendbtn = (Button)findViewById(R.id.sendbtn);
		connect = (TextView)findViewById(R.id.connect);
		sendedit = (EditText)findViewById(R.id.sendEdit);
		t = (TextView)findViewById(R.id.time);
//		ipedit = (EditText)findViewById(R.id.ipEdit);
//		textView = (TextView)findViewById(R.id.textv1);
		time = sdf.format(new Date());
//		textView.setText(time);
	}

	//******************************************************************************

	Socket socket = null;
	OutputStream os = null;
	InputStream is = null;
	BufferedWriter writer = null;
	BufferedReader reader = null;

	public void connect(){

		AsyncTask<Void, String, Void> read = new AsyncTask<Void, String, Void>(){

			@Override
			protected Void doInBackground(Void... arg0) {
				try {
					socket = new Socket("192.168.56.1", 12345);
					os = socket.getOutputStream();
					is = socket.getInputStream();
					writer = new BufferedWriter(new OutputStreamWriter(os));
					reader = new BufferedReader(new InputStreamReader(is));
					publishProgress("@success");
				} catch (UnknownHostException e1) {
					Toast.makeText(MySocketClientAcitivity.this, "链接失败", Toast.LENGTH_SHORT).show();
					e1.printStackTrace();
				} catch (IOException e1) {
					Toast.makeText(MySocketClientAcitivity.this, "链接失败", Toast.LENGTH_SHORT).show();
					e1.printStackTrace();
				}
				try {
					String line = null;
					while((line = reader.readLine())!=null){
						publishProgress(line);

					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onProgressUpdate(String... values) {
				String recive = values[0];
				if(values[0].equals("@success")){
					Toast.makeText(MySocketClientAcitivity.this, "链接成功", Toast.LENGTH_SHORT).show();
					t.setText(time);
				}
				//显示别人的信息
				
//				else{
//					textView.append("客服："+values[0]+"\n");
//				}				
				else if(!"".equals(recive)){  
	                Msg msg = new Msg(recive, Msg.RECEIVED);  
	                msgList.add(msg);  
	                adapter.notifyDataSetChanged();//有新消息时，刷新ListView中的显示  
	                msgListView.setSelection(msgList.size());//将ListView定位到最后一行  
	            } 
				//*********************************************************************
				super.onProgressUpdate(values);
			}

		};
		read.execute();
	}

	public void send(){
		try {
			//显示自己的信息
			String content = sendedit.getText().toString();
            if(!"".equals(content)){  
                Msg msg = new Msg(content, Msg.SENT);  
                msgList.add(msg);  
                adapter.notifyDataSetChanged();//有新消息时，刷新ListView中的显示  
                msgListView.setSelection(msgList.size());//将ListView定位到最后一行  
            }  
			
//            textView.append("我："+sendedit.getText().toString()+"\n");
			//****************************************************************************
			writer.write(sendedit.getText().toString()+"\n");
			writer.flush();
			sendedit.setText("");//清空输入框的内容
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	//*************************************************************************************
	
    public class Msg{  
        
        public static final int RECEIVED = 0;//收到一条消息  
          
        public static final int SENT = 1;//发出一条消息  
          
        private String  content;//消息的内容  
          
        private int type;//消息的类型  
          
        public  Msg(String content,int type){  
            this.content = content;  
            this.type = type;  
        }  
          
        public String getContent(){  
            return content;  
        }  
          
        public int getType(){  
            return type;  
        }  
    }  
      
    public class MsgAdapter extends ArrayAdapter<Msg>{  
        private int resourceId;  
  
        public MsgAdapter(Context context, int textViewresourceId, List<Msg> objects) {  
            super(context, textViewresourceId, objects);  
            resourceId = textViewresourceId;  
        }  
          
        @Override  
        public View getView(int position, View convertView, ViewGroup parent) {  
            Msg msg = getItem(position);  
            View view;  
            ViewHolder viewHolder;  
              
            if(convertView == null){  
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);  
            viewHolder = new ViewHolder();  
            viewHolder.leftLayout = (LinearLayout)view.findViewById(R.id.left_layout);  
            viewHolder.rightLayout = (LinearLayout)view.findViewById(R.id.right_Layout);  
            viewHolder.leftMsg = (TextView)view.findViewById(R.id.left_msg);  
            viewHolder.rightMsg = (TextView)view.findViewById(R.id.right_msg);  
            view.setTag(viewHolder);  
            }else{  
                view = convertView;  
                viewHolder = (ViewHolder) view.getTag();  
            }  
              
            if(msg.getType()==Msg.RECEIVED){  
                //如果是收到的消息，则显示左边消息布局，将右边消息布局隐藏  
                viewHolder.leftLayout.setVisibility(View.VISIBLE);  
                viewHolder.rightLayout.setVisibility(View.GONE);  
                viewHolder.leftMsg.setText(msg.getContent());  
            }else if(msg.getType()==Msg.SENT){  
                //如果是发出去的消息，显示右边布局的消息布局，将左边的消息布局隐藏  
                viewHolder.rightLayout.setVisibility(View.VISIBLE);  
                viewHolder.leftLayout.setVisibility(View.GONE);  
                viewHolder.rightMsg.setText(msg.getContent());  
            }  
            return view;  
        }  
          
        class ViewHolder{  
            LinearLayout leftLayout;  
            LinearLayout rightLayout;  
            TextView leftMsg;  
            TextView rightMsg;  
        }  
    }  

}
