/**
 * @author Billy Dixon
 * @version 0.1.9
 */

package csci498.bidixon.lunchlist;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.widget.TimePicker;

public class TimePreference extends DialogPreference {

	private int lastHour = 0;
	private int lastMinute = 0;
	private TimePicker picker;
	
	public static int getHour(String time) {
		String[] pieces = time.split(":");
		return Integer.parseInt(pieces[0]);
	}
	
	public static int getMinute(String time) {
		String[] pieces = time.split(":");
		return Integer.parseInt(pieces[1]);
	}
	
	public TimePreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
	
	

}
