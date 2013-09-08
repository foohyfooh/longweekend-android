package com.foohyfooh.longweekend;

import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HolidaysFrom extends Fragment implements View.OnClickListener {

	private TextView startDate;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.holidaysfrom, container, false);
        startDate = (TextView) rootView.findViewById(R.id.beginDate);
        startDate.setText(Utils.currentDate());
        rootView.findViewById(R.id.findHolidaysFrom).setOnClickListener(this);
        startDate.setOnClickListener(new DatePickerFragment(getActivity(), startDate));
        return rootView;
	}

	@Override
	public void onClick(View v) {	
		try{
			Intent intent = new Intent(getActivity(), Display.class);
			Bundle bundle = new Bundle();
			//Test Date
			//http://10.0.2.2:8084/longweekend/HolidaysFrom?startDate=2012-11-11
			String request = String.format("http://10.0.2.2:8084/longweekend/HolidaysFrom?startDate=%s", startDate.getText().toString());
			String[] daysArray = new GetJSON(getActivity()).execute(request).get();
			bundle.putStringArray("longWeekend", daysArray);
			intent.putExtras(bundle);
			startActivity(intent);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}


}
