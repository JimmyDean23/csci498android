/**
 * @author Billy Dixon
 * @version 0.2.8
 */

package csci498.bidixon.lunchlist;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class ListViewsFactory implements RemoteViewsService.RemoteViewsFactory {

	private Context context = null;
	private RestaurantHelper helper = null;
	private Cursor restaurants = null;
	
	public ListViewsFactory(Context context, Intent intent) { 
		this.context = context;
	}
	
	public void onCreate() {
		helper = new RestaurantHelper(context);
		restaurants = helper.getReadableDatabase().rawQuery("SELECT _ID, name FROM restaurants", null);
	}
	
	public void onDestroy() { 
		restaurants.close(); 
		helper.close();
	}
	
	public int getCount() { 
		return restaurants.getCount();
	}
	
	public RemoteViews getViewAt(int position) {
		RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.widget_row);
		restaurants.moveToPosition(position); 
		row.setTextViewText(android.R.id.text1, restaurants.getString(1));
		Intent i = new Intent();
		Bundle extras = new Bundle();
		extras.putString(LunchList.ID_EXTRA, String.valueOf( restaurants.getInt(0) ));
		i.putExtras(extras); 
		row.setOnClickFillInIntent(android.R.id.text1, i);
		return row;
	}
	
	public RemoteViews getLoadingView() { 
		return null;
	}
	
	public int getViewTypeCount() { 
		return 1;
	}
	
	public long getItemId(int position) {
		restaurants.moveToPosition(position); 
		return restaurants.getInt(0);
	}
	
	public boolean hasStableIds() { 
		return true;
	}
	
	public void onDataSetChanged() { 
		// no-op
	}
	
}
