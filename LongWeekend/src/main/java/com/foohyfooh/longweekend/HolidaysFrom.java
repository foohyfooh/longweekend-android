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

public class HolidaysFrom extends Activity implements OnClickListener {

	private EditText startDate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.holidaysfrom);
		startDate = (EditText) findViewById(R.id.beginDate);
		Button findHolidaysFrom = (Button) findViewById(R.id.findHolidaysFrom);
		findHolidaysFrom.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {	
		try{
			Class<?> name = Class.forName("com.foohyfooh.longweekend.Display");
			Intent intent = new Intent(this, name);
			Bundle bundle = new Bundle();
			List<String> daysList;
			//Test Date
			//http://10.0.2.2:8084/longweekend/HolidaysFrom?startDate=2012-11-11
			String request = String.format("http://10.0.2.2:8084/longweekend/HolidaysFrom?startDate=%s", startDate.getText().toString());
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