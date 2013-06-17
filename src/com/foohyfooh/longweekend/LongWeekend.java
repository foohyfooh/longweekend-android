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


public class LongWeekend extends Activity implements OnClickListener {

	private EditText yourDate;
	private Button findLongWeekend;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.longweekend);
		yourDate = (EditText) findViewById(R.id.yourDate);
		findLongWeekend = (Button) findViewById(R.id.findLongweekendButton);
		findLongWeekend.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		try{
			Class<?> name = Class.forName("com.foohyfooh.longweekend.Display");
			Intent intent = new Intent(this, name);
			Bundle bundle = new Bundle();
			List<Data> daysList;
			//Test Date
			//"http://192.168.1.4:8084/longweekend/LongWeekend?yourDate=2013-09-12"
			String request = String.format("http://192.168.1.4:8084/longweekend/LongWeekend?startDate=%s", yourDate.getText().toString());
			daysList = new GetJSON().execute(request).get();
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
