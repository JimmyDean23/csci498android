/**
 * @author Billy Dixon
 * @version 0.2.8
 */

package csci498.bidixon.lunchlist;

import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSReader;
import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

/*
 * Service that handles RSSFeeds 
 */
public class FeedService extends IntentService {
	
	public static final String EXTRA_URL = "csci498.bidixon.lunchlist.EXTRA_URL";
	public static final String EXTRA_MESSENGER = "csci498.bidixon.lunchlist.EXTRA_MESSENGER";
	
	public FeedService() {
		super("FeedService");
	}
	
	@Override
	public void onHandleIntent(Intent i) {
		RSSReader reader = new RSSReader();
		Messenger messenger = (Messenger) i.getExtras().get(EXTRA_MESSENGER);
		Message msg = Message.obtain();
		
		try {
			RSSFeed result = reader.load(i.getStringExtra(EXTRA_URL));
			
			msg.arg1 = Activity.RESULT_OK;
			msg.obj = result;
		} catch (Exception e) {
			Log.e("LunchList", "Exception parsing feed", e);
			msg.arg1 = Activity.RESULT_CANCELED;
			msg.obj = e;
		}
		
		try {
			messenger.send(msg);
		} catch (Exception e) {
			Log.w("LunchList", "Exception sending result to activity", e);
		}
		
		reader.close();
	}
	
}
