package csci498.bidixon.lunchlist;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.*;

public class LunchList extends Activity {
	
	Restaurant r = new Restaurant();
	private RadioGroup radioGroup4minute;
	private RadioButton radioButtonJiyoon;
	private RadioButton radioButtonJihyun;
	private RadioButton radioButtonGayoon;
	private RadioButton radioButtonSohyun;
	private RadioButton radioButtonHyuna;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_list);
        
        radioGroup4minute = (RadioGroup) findViewById(R.id.types);
       
        radioButtonJiyoon = new RadioButton(this);
        radioButtonJiyoon.setText("Jiyoon");
        radioButtonJihyun = new RadioButton(this);
        radioButtonJihyun.setText("Jihyun");
        radioButtonGayoon = new RadioButton(this);
        radioButtonGayoon.setText("Gayoon");
        radioButtonSohyun = new RadioButton(this);
        radioButtonSohyun.setText("Sohyun");
        radioButtonHyuna = new RadioButton(this);
        radioButtonHyuna.setText("Hyuna");
        
        radioGroup4minute.addView(radioButtonJiyoon);
        radioGroup4minute.addView(radioButtonJihyun);
        radioGroup4minute.addView(radioButtonGayoon);
        radioGroup4minute.addView(radioButtonSohyun);
        radioGroup4minute.addView(radioButtonHyuna);
       
        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(onSave);
    }
    
    private View.OnClickListener onSave = new View.OnClickListener() {
		public void onClick(View v) {
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
