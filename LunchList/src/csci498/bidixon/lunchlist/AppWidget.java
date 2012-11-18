/**
 * @author Billy Dixon
 * @version 0.2.2
 */

package csci498.bidixon.lunchlist;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/*
 * Widget for LunchList
 */
public class AppWidget extends AppWidgetProvider {
	
	@Override
	public void onUpdate(Context context, AppWidgetManager manager, int[] appWidgetIds) { 
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			onHCUpdate(context, manager, appWidgetIds);
		} else {
			context.startService(new Intent(context, WidgetService.class));
		}
	}
	
}
