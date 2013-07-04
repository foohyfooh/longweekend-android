package com.foohyfooh.longweekend;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HolidaysFrom extends Activity implements OnClickListener {

	private TextView startDate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.holidaysfrom);
		startDate = (TextView) findViewById(R.id.beginDate);
		Button findHolidaysFrom = (Button) findViewById(R.id.findHolidaysFrom);
		findHolidaysFrom.setOnClickListener(this);
        startDate.setOnClickListener(new DatePickerFragment(this, startDate));
	}


	@Override
	public void onClick(View v) {	
		try{
			Class<?> name = Class.forName("com.foohyfooh.longweekend.Display");
			Intent intent = new Intent(this, name);
			Bundle bundle = new Bundle();
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
