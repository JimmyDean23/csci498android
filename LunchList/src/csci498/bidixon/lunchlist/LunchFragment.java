/**
 * @author Billy Dixon
 * @version 0.2.3
 */

package csci498.bidixon.lunchlist;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

/*
 * Main activity that displays a list of Restaurants in database
 */
@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class LunchFragment extends ListFragment {
	
	public final static String ID_EXTRA = "csci498.bidixon.lunchlist._ID";
	
	private RestaurantAdapter restaurantAdapter;
	private RestaurantHelper helper;
	private Cursor model;
	private SharedPreferences prefs;
	private OnRestaurantListener listener;
	
	@Override
	public void onCreate(Bundle state) {
		super.onCreate(state); 
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onResume() { 
		super.onResume();
		helper = new RestaurantHelper(getActivity()); 
		prefs = PreferenceManager.getDefaultSharedPreferences(getActivity()); 
		initializeList(); 
		prefs.registerOnSharedPreferenceChangeListener(prefListener);
	}
	
	@Override
	public void onPause() { 
		helper.close();
		super.onPause(); 
	}
    
    private void initializeList() {
    	if (model != null) {
    		model.close();
    	}   	
        
        model = helper.getAll(prefs.getString("sort_order", "name"));
        restaurantAdapter = new RestaurantAdapter(model);
        setListAdapter(restaurantAdapter);
    }
    
	@Override
	public void onListItemClick(ListView list, View view, int position, long id) {
		if (listener != null) { 
			listener.onRestaurantSelected(id);
		}
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.option, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.add) {
			startActivity(new Intent(getActivity(), DetailForm.class));
			return true;
		} else if (item.getItemId() == R.id.perfs) {
			startActivity(new Intent(getActivity(), EditPreferences.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void setOnRestaurantListener(OnRestaurantListener listener) { 
		this.listener = listener;
	}
	
	public interface OnRestaurantListener { 
		void onRestaurantSelected(long id);
	}
	
	private SharedPreferences.OnSharedPreferenceChangeListener prefListener = 
			new SharedPreferences.OnSharedPreferenceChangeListener() {
		
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
			if (key.equals("sort_order")) {
				initializeList();
			}
		}
	};

    class RestaurantAdapter extends CursorAdapter {
		RestaurantAdapter(Cursor c) {
			super(getActivity(), c);
		}

		@Override
		public void bindView(View row, Context ctxt, Cursor c) {
			RestaurantHolder holder = (RestaurantHolder) row.getTag();
			holder.populateFrom(c, helper);
		}

		@Override
		public View newView(Context ctxt, Cursor c, ViewGroup parent) {
			LayoutInflater inflater = getActivity().getLayoutInflater();
			View row = inflater.inflate(R.layout.row, parent, false);
			RestaurantHolder holder = new RestaurantHolder(row);
			
			row.setTag(holder);
			return row;
		}
	}
	
	static class RestaurantHolder {
		private TextView name;
		private TextView address;
		private ImageView icon;
		
		RestaurantHolder(View row) {
			name = (TextView) row.findViewById(R.id.title);
			address = (TextView) row.findViewById(R.id.address);
			icon = (ImageView) row.findViewById(R.id.icon);
		}
		
		void populateFrom(Cursor c, RestaurantHelper helper) {
			name.setText(helper.getName(c));
			address.setText(helper.getAddress(c));
			
			if (helper.getType(c).equals(R.string.sit_down)) {
				name.setTextColor(Color.RED);
				icon.setImageResource(R.drawable.ball_red);
			} else if (helper.getType(c).equals(R.string.take_out)) {
				name.setTextColor(Color.YELLOW);
				icon.setImageResource(R.drawable.ball_yellow);
			} else {
				name.setTextColor(Color.GREEN);
				icon.setImageResource(R.drawable.ball_green);
			}
		}
	}
	
}
