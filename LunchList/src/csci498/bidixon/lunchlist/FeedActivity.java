/**
 * @author Billy Dixon
 * @version 0.1.6
 */

package csci498.bidixon.lunchlist;

import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSItem;
import org.mcsoxford.rss.RSSReader;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/*
 * Activity that displays a restaurant's RSS feed 
 */
@SuppressWarnings("deprecation")
public class FeedActivity extends ListActivity {
	
	public static final String FEED_URL = "csci498.bidixon.lunchlist.FEED_URL";
	private InstanceState state = null;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		state = (InstanceState) getLastNonConfigurationInstance();
		if (state == null) {
			state = new InstanceState();
			state.task = new FeedTask(this);
			state.task.execute(getIntent().getStringExtra(FEED_URL));
		} else {
			if (state.task != null) {
				state.task.attach(this);
			}
			
			if (state.feed != null) {
				setFeed(state.feed);
			}
		}		
	}
	
	@Override
	public Object onRetainNonConfigurationInstance() {
		if (state.task != null) {
			state.task.detach();
		}
		return state;
	}
	
	public void setFeed(RSSFeed feed) {
		state.feed = feed;
		setListAdapter(new FeedAdapter(feed));
	}
	
	public void raiseError(Throwable t) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setTitle("Exception!").setMessage(t.toString()).setPositiveButton("OK", null).show();
	}
	
	private static class InstanceState {
		
		RSSFeed feed = null;
		FeedTask task = null;
	
	}
	
	private static class FeedHandler extends Handler {
		
		private FeedActivity activity = null;
		
		FeedHandler(FeedActivity activity) {
			attach(activity);
		}
		
		private void attach(FeedActivity activity) {
			this.activity = activity;
		}
		
		private void detach() {
			this.activity = null;
		}
		
		public void handleMessage(Message msg) {
			if (msg.arg1 == RESULT_OK) {
				activity.setFeed((RSSFeed) msg.obj);
			} else {
				activity.raiseError((Exception) msg.obj);
			}
		}
		
	}

	private class FeedAdapter extends BaseAdapter {
		
		RSSFeed feed = null;
		
		FeedAdapter(RSSFeed feed) {
			super();
			this.feed = feed;
		}
		
		public int getCount() {
			return feed.getItems().size();
		}
		
		public Object getItem(int position) {
			return feed.getItems().get(position);
		}
		
		public long getItemId(int position) {
			return position;
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			
			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
			}
			RSSItem item = (RSSItem) getItem(position);
			
			((TextView) row).setText(item.getTitle());
			
			return row;
		}
		
	}

}
