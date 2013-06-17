package com.foohyfooh.longweekend;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	
	private Button longWeekend, holidaysFrom, holidaysBetween;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		longWeekend = (Button) findViewById(R.id.longweekendButton);
		holidaysFrom = (Button) findViewById(R.id.holidaysFromButton);
		holidaysBetween = (Button) findViewById(R.id.holidaysBetweenButton);
		longWeekend.setOnClickListener(this);
		holidaysFrom.setOnClickListener(this);
		holidaysBetween.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
	@Override
	public void onClick(View v) {
		try{
			Class<?> name = null;
			switch(v.getId()){
				case R.id.longweekendButton:
					name = Class.forName("com.foohyfooh.longweekend.LongWeekend");
					break;
				case R.id.holidaysFromButton:
					name = Class.forName("com.foohyfooh.longweekend.HolidaysFrom");
					break;
				case R.id.holidaysBetweenButton:
					name = Class.forName("com.foohyfooh.longweekend.HolidaysBetween");
					break;
			}
			Intent intent = new Intent(this, name);
			startActivity(intent);
		}catch(ClassNotFoundException  e){
			
		}
	}
	
	

	
}
