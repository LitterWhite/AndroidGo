package com.example.zhongtianbao;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class ListViewProvider extends AppWidgetProvider {

	private static final String TAG = "SKYWANG";

	public static final String BT_REFRESH_ACTION = "com.skywang.test.BT_REFRESH_ACTION";
	public static final String COLLECTION_VIEW_ACTION = "com.skywang.test.COLLECTION_VIEW_ACTION";
	public static final String COLLECTION_VIEW_EXTRA = "com.skywang.test.COLLECTION_VIEW_EXTRA";
	
	
    @Override  
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,  
            int[] appWidgetIds) {  

        Log.d(TAG, "GridWidgetProvider onUpdate");
        
    	for (int appWidgetId:appWidgetIds) {
	        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.listview);

	        Intent serviceIntent = new Intent(context, ListViewService.class);        
	        rv.setRemoteAdapter(R.id.ListView, serviceIntent);	        	        
	        appWidgetManager.updateAppWidget(appWidgetId, rv);
    	}
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
    
    @Override
    public void onReceive(Context context, Intent intent) {
    	System.out.println("()()()()()()()((()()()(");
        super.onReceive(context, intent);
    }
}
