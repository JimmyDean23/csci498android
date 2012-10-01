/**
 * @author Billy Dixon
 * @version 0.1.5
 */

package csci498.bidixon.lunchlist;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/*
 * Preference screen activity where users can change their preferences
 */
public class EditPreferences extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preferences);
	}
	
}
