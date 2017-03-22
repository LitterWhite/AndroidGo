package com.example.zhongtianbao;

import java.util.ArrayList;
import java.util.HashMap;

//import com.skywang.test.GridWidgetProvider;
//import com.skywang.test.R;
//import com.skywang.test.GridWidgetService.GridRemoteViewsFactory;


//import com.skywang.test.GridWidgetService.GridRemoteViewsFactory;


import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

public class ListViewService extends RemoteViewsService {
	
	@Override
	public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
		Log.d(TAG, "ListWidgetService");
		return new ListRemoteViewsFactory(this, intent);
	}

	private static final String TAG = "SKYWANG";

	private class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

		private Context mContext;
		private int mAppWidgetId;		

	    private ArrayList<String> s1;
	    private ArrayList<String> s2;
	    
		public ListRemoteViewsFactory(Context context, Intent intent) {
			mContext = context;
	        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
	                AppWidgetManager.INVALID_APPWIDGET_ID);
	        Log.d(TAG, "GridRemoteViewsFactory mAppWidgetId:"+mAppWidgetId);
		}
		
		public RemoteViews getViewAt(int position) { 
			
	        Log.d(TAG, "ListRemoteViewsFactory getViewAt:"+position);
			RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.list_item);
			rv.setTextViewText(R.id.tv_time, s1.get(position).toString());
			System.out.println(s1.get(position));
			rv.setTextViewText(R.id.tv_content, s2.get(position));
			
			return rv;
		}		

	    private void initGridViewData() {
	    	s1 = new ArrayList<String>();
	    	s2 = new ArrayList<String>();
	    	s1.add("清明前后");
	    	s1.add("5月");
	    	s1.add("6月");
	    	s1.add("7月");
	    	s1.add("8月");
	    	s1.add("9月");
	    	s1.add("10月");
	    	s2.add("灌水，下底肥");
	    	s2.add("播种");
	    	s2.add("出苗");
	    	s2.add("除草");
	    	s2.add("追肥");
	    	s2.add("防虫");
	    	s2.add("晒田，开始收获");
	    }
		
		public void onCreate() {
			Log.d(TAG, "onCreate");
			initGridViewData();
		}
		
		public int getCount() {
			return s1.size();
		}
				
		public long getItemId(int position) {
			return position;
		}

		public RemoteViews getLoadingView() {
			return null;
		}
		
		public int getViewTypeCount() {
			// 鍙湁涓�绫� GridView
			return 1;
		}

		public boolean hasStableIds() {
			return true;
		}		

		public void onDataSetChanged() {			
		}
		
		public void onDestroy() {
			s1.clear();
			s2.clear();
		}
	}
}
