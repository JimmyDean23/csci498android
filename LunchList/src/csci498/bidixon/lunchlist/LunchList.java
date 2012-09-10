package csci498.bidixon.lunchlist;

import java.util.*;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class LunchList extends Activity {
	
	class RestaurantAdapter extends ArrayAdapter<Restaurant> {
		RestaurantAdapter() {
			super(LunchList.this, android.R.layout.simple_list_item_1, restaurantsList);
		}
		
		public View getView(int position, View convertView, ViewGroup parent){
			View row = convertView;
			
			if(row == null){
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.row, null);
			}
			
			Restaurant rest = restaurantsList.get(position);
			
			((TextView) row.findViewById(R.id.title)).setText(rest.getName());
			((TextView) row.findViewById(R.id.address)).setText(rest.getAddress());
			
			ImageView icon = (ImageView) row.findViewById(R.id.icon);
			
			if(rest.getType().equals("sit_down")) {
				icon.setImageResource(R.drawable.ball_red);
			} else if(rest.getType().equals("take_out")) {
				icon.setImageResource(R.drawable.ball_yellow);
			} else {
				icon.setImageResource(R.drawable.ball_green);
			}
			
			return row;
		}
	}
	
	List<Restaurant> restaurantsList = new ArrayList<Restaurant>();
	RestaurantAdapter restaurantAdapter = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_list);
        
        Button saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(onSave);
        
        ListView restaurantList = (ListView) findViewById(R.id.restaurants);
        restaurantAdapter = new RestaurantAdapter();
        restaurantList.setAdapter(restaurantAdapter);
    }
    
    private View.OnClickListener onSave = new View.OnClickListener() {
    	
		public void onClick(View v) {
			Restaurant currentRestaurant = new Restaurant();
			EditText name = (EditText) findViewById(R.id.name);
			AutoCompleteTextView address = (AutoCompleteTextView) findViewById(R.id.addr);
			RadioGroup types = (RadioGroup) findViewById(R.id.types);
			
			currentRestaurant.setName(name.getText().toString());
			currentRestaurant.setAddress(address.getText().toString());
			currentRestaurant.setType(restaurantTypeFromRadioGroup(types));
			
			restaurantAdapter.add(currentRestaurant);
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
