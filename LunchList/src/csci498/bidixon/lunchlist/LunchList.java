package csci498.bidixon.lunchlist;

import android.os.Bundle;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

@SuppressWarnings("deprecation")
public class LunchList extends ListActivity {
	
	public final static String ID_EXTRA = "csci498.bidixon.lunchlist._ID";
	RestaurantAdapter restaurantAdapter;
	RestaurantHelper helper;
	Cursor model;
	Restaurant current = null;
	EditText name;
	EditText address;
	EditText notes;
	RadioGroup types;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_list);
        
        helper = new RestaurantHelper(this);
        model = helper.getAll();
        startManagingCursor(model);
        restaurantAdapter = new RestaurantAdapter(model);
        setListAdapter(restaurantAdapter);
    }
	@Override
	public void onListItemClick(ListView list, View view, int position, long id) {
		Intent i = new Intent(LunchList.this, DetailForm.class);
			
		i.putExtra(ID_EXTRA, String.valueOf(id));
		startActivity(i);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		helper.close();
	}

    class RestaurantAdapter extends CursorAdapter {
		RestaurantAdapter(Cursor c) {
			super(LunchList.this, c);
		}

		@Override
		public void bindView(View row, Context ctxt, Cursor c) {
			RestaurantHolder holder = (RestaurantHolder) row.getTag();
			holder.populateFrom(c, helper);
		}

		@Override
		public View newView(Context ctxt, Cursor c, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.row, parent, false);
			RestaurantHolder holder = new RestaurantHolder(row);
			
			row.setTag(holder);
			return row;
		}
	}
	
	static class RestaurantHolder {
		private TextView name;
		private TextView address;
		private ImageView icon;
		
		RestaurantHolder(View row) {
			name = (TextView) row.findViewById(R.id.title);
			address = (TextView) row.findViewById(R.id.address);
			icon = (ImageView) row.findViewById(R.id.icon);
		}
		
		void populateFrom(Cursor c, RestaurantHelper helper){
			name.setText(helper.getName(c));
			address.setText(helper.getAddress(c));
			
			if (helper.getType(c).equals(R.string.sit_down)) {
				name.setTextColor(Color.RED);
				icon.setImageResource(R.drawable.ball_red);
			} else if (helper.getType(c).equals(R.string.take_out)) {
				name.setTextColor(Color.YELLOW);
				icon.setImageResource(R.drawable.ball_yellow);
			} else {
				name.setTextColor(Color.GREEN);
				icon.setImageResource(R.drawable.ball_green);
			}
		}
	}
}
