/**
 * @author Billy Dixon
 * @version 0.2.3
 */

package csci498.bidixon.lunchlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/*
 * Main activity that displays a list of Restaurants in database
 */
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
    	if (findViewById(R.id.details) == null) {
    		Intent i = new Intent(this, DetailForm.class);
    		i.putExtra(ID_EXTRA, String.valueOf(id));
    		
    		startActivity(i); 
    	} else {    	
    		FragmentManager fragmentManager = getSupportFragmentManager(); 
    		DetailFragment details = (DetailFragment) fragmentManager.findFragmentById(R.id.details); 
    		
    		if (details == null) {
    			details = DetailFragment.newInstance(id);
    			
    	    	FragmentTransaction xaction = fragmentManager.beginTransaction();
    	    	
    	    	xaction.add(R.id.details, details)
    	    		.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
    	    		.addToBackStack(null)
    	    		.commit();
    		} else {
    			details.loadRestaurant(String.valueOf(id));
    		}
    	}
    }
    
}