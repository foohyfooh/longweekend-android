package com.foohyfooh.longweekend;

import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;


public class LongWeekend extends Fragment implements View.OnClickListener {

    private TextView yourDate;
    private RadioGroup radioGroup;

    private final int LONG_WEEKEND_BEFORE = 0;
    private final int LONG_WEEKEND_AFTER = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.longweekend, container, false);
        yourDate = (TextView) rootView.findViewById(R.id.yourDate);
        yourDate.setText(Utils.currentDate());
        rootView.findViewById(R.id.findLongweekendButton).setOnClickListener(this);
        yourDate.setOnClickListener(new DatePickerFragment(getActivity(), yourDate));
        radioGroup = (RadioGroup) rootView.findViewById(R.id.longweekendOptionsGroup);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        try{
            Intent intent = new Intent(getActivity(), Display.getDisplay());
            Bundle bundle = new Bundle();
            String[] request = null;
            String longWeekendRequest = "http://10.0.2.2:8084/longweekend/LongWeekend?startDate=%s&endDate=%s&selector=%d";
            String dates = UserDates.getList(getActivity());
            String userDates = !dates.equals("[]") ? "&userDates="+ dates : "" ;//Not equal empty JSONArray
            //Test Dates
            //"http://10.0.2.2:8084/longweekend/LongWeekend?startDate=2012-01-01&endDate=2012-08-07&selector=0"
            //"http://10.0.2.2:8084/longweekend/LongWeekend?startDate=2012-09-11&endDate=2013-12-31&selector=1"
            switch(radioGroup.getCheckedRadioButtonId()){
                case R.id.longweekendBefore:
                    request = new String[]{
                        String.format(longWeekendRequest, Utils.yearBegin(), yourDate.getText().toString(),
                                LONG_WEEKEND_BEFORE).concat(userDates)
                    };
                    break;
                case R.id.longweekendAfter:
                    request = new String[]{
                        String.format(longWeekendRequest, yourDate.getText().toString(), Utils.yearEnd(),
                                LONG_WEEKEND_AFTER).concat(userDates)
                    };
                    break;
                case R.id.longweekendBoth:
                    request = new String[] {
                        String.format(longWeekendRequest, Utils.yearBegin(), yourDate.getText().toString(),
                                LONG_WEEKEND_BEFORE).concat(userDates),
                        String.format(longWeekendRequest, yourDate.getText().toString(), Utils.yearEnd(),
                                LONG_WEEKEND_AFTER).concat(userDates)
                    };
                    break;
            }
            String[] daysArray = new GetJSON(getActivity()).execute(request).get();
            bundle.putStringArray("longWeekend", daysArray);
            intent.putExtras(bundle);
            startActivity(intent);
        }catch(ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
