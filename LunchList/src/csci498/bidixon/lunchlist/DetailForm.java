/**
 * @author Billy Dixon
 * @version 0.2.3
 */

package csci498.bidixon.lunchlist;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/*
 * Activity that allows user to create new Restaurant entries and save them to the database
 */
public class DetailForm extends Activity {
	
	EditText name;
	EditText address;
	EditText notes;
	EditText feed;
	TextView location;
	RadioGroup types;
	RestaurantHelper helper;
	String restaurantId;
	LocationManager locMgr;
	double latitude = 0.0d;
	double longitude = 0.0d;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_form);
		
		initializeFields();
        
        Button saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(onSave);
        
        restaurantId = getIntent().getStringExtra(LunchList.ID_EXTRA);
        
        if(restaurantId != null) {
        	load();
        }
	}
	
	private void initializeFields() {
		name = (EditText) findViewById(R.id.name);
		address = (EditText) findViewById(R.id.addr);
		notes = (EditText) findViewById(R.id.notes);
		types = (RadioGroup) findViewById(R.id.types);
		feed = (EditText) findViewById(R.id.feed);
		location = (TextView) findViewById(R.id.location);
		
		helper = new RestaurantHelper(this);
		locMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		helper.close();
	}
	
	@Override
	public void onPause() {
		locMgr.removeUpdates(onLocationChange);
		super.onPause();
	}
	
	@Override
	public void onSaveInstanceState(Bundle state) {
		super.onSaveInstanceState(state);
		
		state.putString("name", name.getText().toString());
		state.putString("address", address.getText().toString());
		state.putString("notes", notes.getText().toString());
		state.putInt("type", types.getCheckedRadioButtonId());
	}
	
	@Override
	public void onRestoreInstanceState(Bundle state) {
		super.onRestoreInstanceState(state);
		
		name.setText(state.getString("name"));
		address.setText(state.getString("address"));
		notes.setText(state.getString("notes"));
		types.check(state.getInt("type"));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		new MenuInflater(this).inflate(R.menu.details_options, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if (item.getItemId() == R.id.feed) {
			
			if (isNetworkAvailable()) {
				Intent i = new Intent(this, FeedActivity.class);
				
				i.putExtra(FeedActivity.FEED_URL, feed.getText().toString());
				
				startActivity(i);
			} else {
				Toast.makeText(this, "Network Connection not available.", Toast.LENGTH_LONG).show();
			}
			
			return true;
			
		} else if (item.getItemId() == R.id.location) {
			
			locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, onLocationChange);
			
			return true;
			
		} else if (item.getItemId() == R.id.map) {
			
			Intent i = new Intent(this, RestaurantMap.class);
			
			i.putExtra(RestaurantMap.EXTRA_LATITUDE, latitude);
			i.putExtra(RestaurantMap.EXTRA_LONGITUDE, longitude);
			i.putExtra(RestaurantMap.EXTRA_NAME, name.getText().toString());
			
			startActivity(i);
			return true;
			
		}
		
		return super.onOptionsItemSelected(item);
		
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (restaurantId == null) {
			menu.findItem(R.id.location).setEnabled(false);
			menu.findItem(R.id.map).setEnabled(false);
		}
		return super.onPrepareOptionsMenu(menu);
	}
	
	private void load() {
		Cursor c = helper.getById(restaurantId);
		c.moveToFirst();
		
		name.setText(helper.getName(c));
		address.setText(helper.getAddress(c));
		notes.setText(helper.getNotes(c));
		feed.setText(helper.getFeed(c));
		latitude = helper.getLatitude(c);
		longitude = helper.getLongitude(c);
		location.setText(String.valueOf( helper.getLatitude(c) + ", " + helper.getLongitude(c) ));
		
		if (helper.getType(c).equals("sit_down")) {
			types.check(R.id.sit_down);
		} else if (helper.getType(c).equals("take_out")) {
			types.check(R.id.take_out);
		} else {
			types.check(R.id.delivery);
		}
				
		c.close();
	}
	
	private boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		
		return(info != null);
	}
	
	private LocationListener onLocationChange = new LocationListener() {
		
		public void onLocationChanged(Location fix) {
			helper.updateLocation(restaurantId, fix.getLatitude(), fix.getLongitude());
			location.setText( String.valueOf(fix.getLatitude()) + ", " + String.valueOf(fix.getLongitude()) );
			locMgr.removeUpdates(onLocationChange);
			
			Toast.makeText(DetailForm.this, "Location saved", Toast.LENGTH_LONG).show();
		}

		public void onProviderDisabled(String provider) { 
			// not use
		}
		public void onProviderEnabled(String provider) {
			// not used			
		}
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// not used			
		}

	};
	
	private View.OnClickListener onSave = new View.OnClickListener() {
		
		public void onClick(View v) {
			String type = null;
			
			switch (types.getCheckedRadioButtonId()) {
				case R.id.sit_down:
					type = "sit_down";
					break;
				case R.id.take_out:
					type = "take_out";
					break;
				case R.id.delivery:
					type = "delivery";
					break;
			}
			
			if (restaurantId == null) {
				helper.insert(name.getText().toString(), address.getText().toString(), type, 
						notes.getText().toString(), feed.getText().toString());
			} else {
				helper.update(restaurantId, name.getText().toString(), address.getText().toString(), 
						type, notes.getText().toString(), feed.getText().toString());
			}
			
			finish();
		}
		
	};
	
}