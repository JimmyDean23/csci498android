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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
}
