package com.foohyfooh.longweekend;


import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HolidaysBetween extends Fragment implements View.OnClickListener {

	private TextView startDate, endDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.holidaysbetween, container, false);
        startDate = (TextView) rootView.findViewById(R.id.startDate);
        startDate.setText(Utils.currentDate());
        endDate = (TextView) rootView.findViewById(R.id.endDate);
        endDate.setText(Utils.yearEnd());
        rootView.findViewById(R.id.findHolidaysBetween).setOnClickListener(this);
        startDate.setOnClickListener(new DatePickerFragment(getActivity(), startDate));
        endDate.setOnClickListener(new DatePickerFragment(getActivity(), endDate));
        return rootView;
    }

    @Override
	public void onClick(View v) {
		try{
			Intent intent = new Intent(getActivity(), Display.getDisplay());
			Bundle bundle = new Bundle();
			//Test Date
			//http://10.0.2.2:8084/longweekend/HolidaysBetween?startDate=2012-01-01&endDate=2012-08-12
			String request = String.format("http://10.0.2.2:8084/longweekend/HolidaysBetween?startDate=%s&endDate=%s",
					startDate.getText().toString(), endDate.getText().toString());
            String[] daysArray = new GetJSON(getActivity()).execute(request).get();
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
