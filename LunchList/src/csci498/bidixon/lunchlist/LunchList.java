package csci498.bidixon.lunchlist;

import java.util.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.*;

public class LunchList extends Activity {
	
	class RestaurantAdapter extends ArrayAdapter<Restaurant> {
		RestaurantAdapter() {
			super(LunchList.this, android.R.layout.simple_list_item_1, restaurants);
		}
	}
	
	List<Restaurant> restaurants = new ArrayList<Restaurant>();
	RestaurantAdapter restaurantAdapter = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_list);
        
        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(onSave);
        
        ListView restaurantList = (ListView) findViewById(R.id.restaurants);
        restaurantAdapter = new RestaurantAdapter();
        restaurantList.setAdapter(restaurantAdapter);
    }
    
    private View.OnClickListener onSave = new View.OnClickListener() {
    	
		public void onClick(View v) {
			Restaurant r = new Restaurant();
			EditText name = (EditText) findViewById(R.id.name);
			AutoCompleteTextView address = (AutoCompleteTextView) findViewById(R.id.addr);
			RadioGroup types = (RadioGroup) findViewById(R.id.types);
			
			r.setName(name.getText().toString());
			r.setAddress(address.getText().toString());
			r.setType(restaurantTypeFromRadioGroup(types));
			
			restaurantAdapter.add(r);
		}
	};
	
	private String restaurantTypeFromRadioGroup(RadioGroup group){
		if (group == null){
			return "";
		}
		
		switch (group.getCheckedRadioButtonId()){
			case R.id.sit_down:
				return "sit_down";
			case R.id.take_out:
				return "take_out";
			case R.id.delivery:
				return "delivery";
		}
		return "";
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_lunch_list, menu);
        return true;
    }   
}
