/**
 * @author Billy Dixon
 * @version 0.2.3
 */

package csci498.bidixon.lunchlist;

import csci498.bidixon.lunchlist.LunchFragment.RestaurantAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/*
 * Main activity that displays a list of Restaurants in database
 */
@SuppressWarnings("deprecation")
public class LunchList extends FragmentActivity implements LunchFragment.OnRestaurantListener {
	
	public final static String ID_EXTRA = "csci498.bidixon.lunchlist._ID";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lunch_list);
        
        LunchFragment lunch = (LunchFragment) getSupportFragmentManager().findFragmentById(R.id.lunch); 
        lunch.setOnRestaurantListener(this);
    }
    
    public void onRestaurantSelected(long id) {
    	Intent i = new Intent(this, DetailForm.class);
    	i.putExtra(ID_EXTRA, String.valueOf(id));
    	startActivity(i);
    }
    
}