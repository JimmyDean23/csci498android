/**
 * @author Billy Dixon
 * @version 0.2.1
 */

package csci498.bidixon.lunchlist;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.RemoteViews;

/*
 * IntentService that handles updating the widget
 */
public class WidgetService extends IntentService {

	public WidgetService() {
		super("WidgetService");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		ComponentName me = new ComponentName(this, AppWidget.class); 
		RemoteViews widgetViews = new RemoteViews("apt.tutorial", R.layout.widget);
		RestaurantHelper restaurantHelper = new RestaurantHelper(this); 
		AppWidgetManager manager = AppWidgetManager.getInstance(this);
		
		try {
			Cursor cursor = restaurantHelper.getReadableDatabase().rawQuery("SELECT COUNT(*) FROM restaurants", null);
			cursor.moveToFirst();
			int count = cursor.getInt(0); 
			cursor.close();
			if (count > 0) {
				int offset = (int) (count * Math.random()); 
				String args[] = { String.valueOf(offset) };
				cursor = restaurantHelper.getReadableDatabase().rawQuery("SELECT _ID, name FROM restaurants LIMIT 1 OFFSET ?", args);
				cursor.moveToFirst();
				widgetViews.setTextViewText(R.id.name, cursor.getString(1));
				Intent i = new Intent(this, DetailForm.class);
				i.putExtra(LunchList.ID_EXTRA, cursor.getString(0));
				PendingIntent pi = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
				widgetViews.setOnClickPendingIntent(R.id.name, pi); 
				cursor.close();
			} else {
				widgetViews.setTextViewText(R.id.title, this.getString(R.string.empty));
			} 
		} finally { 
			restaurantHelper.close();
		}
		
		Intent i = new Intent(this, WidgetService.class); 
		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		widgetViews.setOnClickPendingIntent(R.id.next, pi);
		manager.updateAppWidget(me, widgetViews);
	}

}
