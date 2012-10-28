/**
 * @author Billy Dixon
 * @version 0.1.9
 */

package csci498.bidixon.lunchlist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/*
 * Preference screen activity where users can change their preferences
 */
public class EditPreferences extends PreferenceActivity {

	SharedPreferences prefs;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preferences);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(onChange);
	}
	
	@Override
	public void onPause() {
		prefs.unregisterOnSharedPreferenceChangeListener(onChange);
		
		super.onPause();
	}
	
}
