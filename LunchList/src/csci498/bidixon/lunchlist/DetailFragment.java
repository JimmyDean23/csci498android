/**
 * @author Billy Dixon
 * @version 0.2.3
 */

package csci498.bidixon.lunchlist;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/*
 * Activity that allows user to create new Restaurant entries and save them to the database
 */
public class DetailFragment extends Fragment {
	
	EditText name;
	EditText address;
	EditText notes;
	EditText feed;
	TextView location;
	RadioGroup types;
	RestaurantHelper helper;
	String restaurantId;
	LocationManager locationManager;
	double latitude = 0.0d;
	double longitude = 0.0d;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState); 
		
		locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
		
		name = (EditText) getView().findViewById(R.id.name); 
		address = (EditText) getView().findViewById(R.id.addr); 
		notes = (EditText) getView().findViewById(R.id.notes); 
		types = (RadioGroup) getView().findViewById(R.id.types); 
		feed = (EditText) getView().findViewById(R.id.feed); 
		location = (TextView) getView().findViewById(R.id.location);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) { 
		return inflater.inflate(R.layout.detail_form, container, false);
	}
	
	@Override
	public void onResume() { 
		super.onResume();
		
		helper = new RestaurantHelper(getActivity()); 
		restaurantId = getActivity().getIntent().getStringExtra(LunchList.ID_EXTRA);
		
		if (restaurantId != null) { 
			load();
		} 
	}
	
	@Override
	public void onPause() {
		save();
		helper.close(); 
		locationManager.removeUpdates(onLocationChange);
		super.onPause();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.details_options, menu); 
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		if (restaurantId == null) {
			menu.findItem(R.id.location).setEnabled(false);
			menu.findItem(R.id.map).setEnabled(false);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.feed) {
			if (isNetworkAvailable()) {
				Intent i = new Intent(getActivity(), FeedActivity.class);
				
				i.putExtra(FeedActivity.FEED_URL, feed.getText().toString());
				
				startActivity(i);
			} else {
				Toast.makeText(getActivity(), "Network Connection not available.", Toast.LENGTH_LONG).show();
			}
			
			return true;
		} else if (item.getItemId() == R.id.location) {			
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, onLocationChange);
			
			return true;
		} else if (item.getItemId() == R.id.map) {			
			Intent i = new Intent(getActivity(), RestaurantMap.class);
			
			i.putExtra(RestaurantMap.EXTRA_LATITUDE, latitude);
			i.putExtra(RestaurantMap.EXTRA_LONGITUDE, longitude);
			i.putExtra(RestaurantMap.EXTRA_NAME, name.getText().toString());
			
			startActivity(i);
			return true;			
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	private boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		
		return(info != null);
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
	
	private void save() {
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
	}
	
	private LocationListener onLocationChange = new LocationListener() {
		
		public void onLocationChanged(Location fix) {
			helper.updateLocation(restaurantId, fix.getLatitude(), fix.getLongitude());
			location.setText( String.valueOf(fix.getLatitude()) + ", " + String.valueOf(fix.getLongitude()) );
			locationManager.removeUpdates(onLocationChange);
			
			Toast.makeText(getActivity(), "Location saved", Toast.LENGTH_LONG).show();
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
	
}