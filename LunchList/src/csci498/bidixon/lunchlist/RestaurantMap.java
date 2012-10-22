/**
 * @author Billy Dixon
 * @version 0.1.8
 */

package csci498.bidixon.lunchlist;

import android.os.Bundle;
import com.google.android.maps.MapActivity;

public class RestaurantMap extends MapActivity {

	public static final String EXTRA_LATITUDE="csci498.bidixon.lunchlist.EXTRA_LATITUDE";
	public static final String EXTRA_LONGITUDE="csci498.bidixon.lunchlist.EXTRA_LONGITUDE";
	public static final String EXTRA_NAME="csci498.bidixon.lunchlist.EXTRA_NAME";
	private MapView map;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		double lat = getIntent().getDoubleExtra(EXTRA_LATITUDE, 0);
		double lon = getIntent().getDoubleExtra(EXTRA_LONGITUDE, 0);
		map = (MapView) findViewById(R.id.map);
		
		map.getController().setZoom(17);
		GeoPoint status = new GeoPoint( (int) (lat*1000000.0), (int) (lon*1000000.0) );
		map.getController().setCenter(status);
		map.setBuiltInZoomControls(true);
		
		setContentView(R.layout.map);
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
}