/**
 * @author Billy Dixon
 * @version 0.1.8
 */

package csci498.bidixon.lunchlist;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

/*
 * Map activity showing the location of the selected Restaurant on a map using stored GPS coordinates 
 */
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
		setMapZoom(lat, lon);
		
		setContentView(R.layout.map);
	}
	
	private void setMapZoom(double lat, double lon) {
		map.getController().setZoom(17);
		GeoPoint status = new GeoPoint( (int) (lat*1000000.0), (int) (lon*1000000.0) );
		map.getController().setCenter(status);
		map.setBuiltInZoomControls(true);
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	private class RestaurantOverlay extends ItemizedOverlay<OverlayItem> {
		
		private OverlayItem item;
		
		public RestaurantOverlay(Drawable marker, GeoPoint point, String name) {
			super(marker);
			
			boundCenterBottom(marker);
			item = new OverlayItem(point, name, name);
			populate();
		}
		
		@Override
		protected OverlayItem createItem(int i) {
			return item;
		}

		@Override
		public int size() {
			return 1;
		}
		
	}
	
}