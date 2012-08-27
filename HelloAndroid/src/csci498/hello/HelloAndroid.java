package csci498.hello;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import java.util.Date;

public class HelloAndroid extends Activity implements View.OnClickListener{
	Button btn;
	
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        btn = new Button(this);
        btn.setOnClickListener(this);
        updateTime();
        setContentView(btn);
    }
    
    public void onClick(View view){
    	updateTime();
    }
    
    private void updateTime(){
    	btn.setText(new Date().toString());
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_hello_android, menu);
        return true;
    }
}
