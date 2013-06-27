package com.foohyfooh.longweekend;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class HolidaysBetween extends Activity implements OnClickListener {

	
	private EditText startDate, endDate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.holidaysbetween);
		startDate = (EditText) findViewById(R.id.startDate);
		endDate = (EditText) findViewById(R.id.endDate);
		Button findHolidaysBetween = (Button) findViewById(R.id.findHolidaysBetween);
		findHolidaysBetween.setOnClickListener(this);
	}
	
	
	
	@Override
	public void onClick(View v) {
		try{
			Class<?> name = Class.forName("com.foohyfooh.longweekend.Display");
			Intent intent = new Intent(this, name);
			Bundle bundle = new Bundle();
			List<String> daysList;
			//Test Date
			//http://10.0.2.2:8084/longweekend/HolidaysBetween?startDate=2012-01-01&endDate=2012-08-12
			String request = String.format("http://10.0.2.2:8084/longweekend/HolidaysBetween?startDate=%s&endDate=%s",
					startDate.getText().toString(), endDate.getText().toString());
            String[] daysArray = new GetJSON().execute(request).get();
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
