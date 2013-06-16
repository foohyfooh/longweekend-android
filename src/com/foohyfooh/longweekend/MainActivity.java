package com.foohyfooh.longweekend;


import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {

	EditText yourDate, startDate, endDate;
	Button findLongWeekend, holidaysFrom, holidaysBetween;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		yourDate = (EditText) findViewById(R.id.yourDate);
		startDate = (EditText) findViewById(R.id.startDate);
		endDate = (EditText) findViewById(R.id.endDate);
		findLongWeekend = (Button) findViewById(R.id.longweekendButton);
		holidaysFrom = (Button) findViewById(R.id.holidaysFromButton);
		holidaysBetween = (Button) findViewById(R.id.holidaysBetweenButton);
		findLongWeekend.setOnClickListener(this);
		holidaysFrom.setOnClickListener(this);
		holidaysBetween.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	

	//Implemented Methods
	@Override
	public void onClick(View v) {
		
		try{
			Class<?> name = Class.forName("com.foohyfooh.longweekend.LongWeekend");
			Intent intent = new Intent(MainActivity.this, name);
			Bundle bundle = new Bundle();
			List<Data> daysList;
			String request = null;
			switch(v.getId()){
				case R.id.longweekendButton:
					//Test Date
					//"http://192.168.1.4:8084/longweekend/LongWeekend?yourDate=2013-09-12"
					request = String.format("http://192.168.1.4:8084/longweekend/LongWeekend?yourDate=%s", yourDate.getText().toString());
					break;
				case R.id.holidaysFromButton:
					//Test Date
					//http://192.168.1.4:8084/longweekend/HolidaysFrom?startDate=2012-11-11
					request = String.format("http://192.168.1.4:8084/longweekend/HolidaysFrom?startDate=%s", startDate.getText().toString());
					break;
				case R.id.holidaysBetweenButton:
					//Test Date
					//http://192.168.1.4:8084/longweekend/HolidaysBetween?startDate=2012-01-01&endDate=2012-08-12
					request = String.format("http://192.168.1.4:8084/longweekend/HolidaysBetween?startDate=%s&endDate=%s", 
							startDate.getText().toString(), endDate.getText().toString());
					break;
			}
			daysList = new GetJSON().execute(new String[]{request}).get();
			String[] daysArray = new String[daysList.size()];
			for(int count = 0; count < daysList.size(); count++){
				daysArray[count]  = daysList.get(count).toString();
			}
			bundle.putStringArray("longWeekend", daysArray);
			intent.putExtras(bundle);
			startActivity(intent);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
}
