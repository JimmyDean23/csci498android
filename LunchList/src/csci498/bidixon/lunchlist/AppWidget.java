/**
 * @author Billy Dixon
 * @version 0.2.1
 */

package csci498.bidixon.lunchlist;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class AppWidget extends AppWidgetProvider {
	@Override
	public void onUpdate(Context ctxt, AppWidgetManager mgr, int[] appWidgetIds) { 
		ctxt.startService(new Intent(ctxt, WidgetService.class));
	}
}
