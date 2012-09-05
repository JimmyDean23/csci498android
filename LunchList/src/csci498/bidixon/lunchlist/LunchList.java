package csci498.bidixon.lunchlist;

import java.util.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.*;

public class LunchList extends Activity {
	
	List<Restaurant> model = new ArrayList<Restaurant>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_list);
        
        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(onSave);
    }
    
    private View.OnClickListener onSave = new View.OnClickListener() {
		public void onClick(View v) {
			Restaurant r = new Restaurant();
			EditText name = (EditText) findViewById(R.id.name);
			EditText address = (EditText) findViewById(R.id.addr);
			
			r.setName(name.getText().toString());
			r.setAddress(address.getText().toString());
			
			RadioGroup types = (RadioGroup) findViewById(R.id.types);
			
			switch (types.getCheckedRadioButtonId()){
			case R.id.sit_down:
				r.setType("sit_down");
				break;
			case R.id.take_out:
				r.setType("take_out");
				break;
			case R.id.delivery:
				r.setType("delivery");
				break;
			}
		}
	};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_lunch_list, menu);
        return true;
    }
}
