/**
 * @author Billy Dixon
 * @version 0.2.8
 */

package csci498.bidixon.lunchlist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.RemoteViewsService;

@SuppressLint("NewApi")
public class ListWidgetService extends RemoteViewsService {

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		return (new ListViewsFactory(this.getApplicationContext(), intent));
	}

}
