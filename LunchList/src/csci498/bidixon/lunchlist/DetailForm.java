/**
 * @author Billy Dixon
 * @version 0.2.3
 */

package csci498.bidixon.lunchlist;

import android.app.Activity;
import android.os.Bundle;

/*
 * Activity that allows user to create new Restaurant entries and save them to the database
 */
public class DetailForm extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.detail_activity);
	}
	
}