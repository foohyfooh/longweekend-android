package com.foohyfooh.longweekend;

import java.util.concurrent.ExecutionException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;


public class LongWeekend extends Fragment implements OnClickListener {

    private TextView yourDate;
    private RadioGroup radioGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.longweekend, container, false);
        yourDate = (TextView) rootView.findViewById(R.id.yourDate);
        //yourDate.setText(Utils.currentDate());
        Button findLongWeekend = (Button) rootView.findViewById(R.id.findLongweekendButton);
        findLongWeekend.setOnClickListener(this);
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
            //Test Dates
            //"http://10.0.2.2:8084/longweekend/LongWeekend?startDate=2013-09-12"
            //"http://10.0.2.2:8084/longweekend/LongWeekendBefore?startDate=2012-01-01&endDate=2012-08-07"
            //"http://10.0.2.2:8084/longweekend/LongWeekendAfter?startDate=2012-09-11&endDate=2013-12-31"
            switch(radioGroup.getCheckedRadioButtonId()){
                //any of these requests fail if the range is too big
                case R.id.longweekendBefore:
                    request = new String[]{
                        String.format("http://10.0.2.2:8084/longweekend/LongWeekendBefore?startDate=%s&endDate=%s",
                                Utils.currentDate(), yourDate.getText().toString())
                    };
                    break;
                case R.id.longweekendAfter:
                    request = new String[]{
                        String.format("http://10.0.2.2:8084/longweekend/LongWeekendAfter?startDate=%s&endDate=%s",
                        yourDate.getText().toString(), "2013-12-31")
                    };
                    break;
                case R.id.longweekendBoth:
                    request = new String[] {
                        String.format("http://10.0.2.2:8084/longweekend/LongWeekendBefore?startDate=%s&endDate=%s",
                                Utils.currentDate(), yourDate.getText().toString()),
                        String.format("http://10.0.2.2:8084/longweekend/LongWeekendAfter?startDate=%s&endDate=%s",
                                yourDate.getText().toString(), "2013-12-31")
                    };
                    break;
            }
            String[] daysArray = new GetJSON().execute(request).get();
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
