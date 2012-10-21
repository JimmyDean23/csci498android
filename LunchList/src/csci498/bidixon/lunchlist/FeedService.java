/**
 * @author Billy Dixon
 * @version 0.1.6
 */

package csci498.bidixon.lunchlist;

import android.app.IntentService;
import android.content.Intent;

public class FeedService extends IntentService {
	
	public FeedService() {
		super("FeedService");
	}
	
	@Override
	public void onHandleIntent(Intent i) {
		// do stuff
	}
	
}
