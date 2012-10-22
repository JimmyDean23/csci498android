/**
 * @author Billy Dixon
 * @version 0.1.8
 */

package csci498.bidixon.lunchlist;

import android.os.Bundle;
import android.preference.PreferenceManager;
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
@SuppressWarnings("deprecation")
public class LunchList extends ListActivity {
	
	public final static String ID_EXTRA = "csci498.bidixon.lunchlist._ID";
	
	RestaurantAdapter restaurantAdapter;
	RestaurantHelper helper;
	Cursor model;
	SharedPreferences prefs;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lunch_list);
        
        helper = new RestaurantHelper(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        initializeList();        
        prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }
    
    private void initializeList() {
    	if (model != null) {
    		stopManagingCursor(model);
    		model.close();
    	}   	
        
        model = helper.getAll(prefs.getString("sort_order", "name"));
        startManagingCursor(model);
        restaurantAdapter = new RestaurantAdapter(model);
        setListAdapter(restaurantAdapter);
    }
    
	@Override
	public void onListItemClick(ListView list, View view, int position, long id) {
		Intent i = new Intent(LunchList.this, DetailForm.class);
			
		i.putExtra(ID_EXTRA, String.valueOf(id));
		startActivity(i);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		helper.close();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		new MenuInflater(this).inflate(R.menu.option, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.add) {
			startActivity(new Intent(LunchList.this, DetailForm.class));
			return true;
		} else if (item.getItemId() == R.id.perfs) {
			startActivity(new Intent(this, EditPreferences.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
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
			super(LunchList.this, c);
		}

		@Override
		public void bindView(View row, Context ctxt, Cursor c) {
			RestaurantHolder holder = (RestaurantHolder) row.getTag();
			holder.populateFrom(c, helper);
		}

		@Override
		public View newView(Context ctxt, Cursor c, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
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
