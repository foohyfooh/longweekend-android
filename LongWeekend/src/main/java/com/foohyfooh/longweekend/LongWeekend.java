package com.foohyfooh.longweekend;

import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;


public class LongWeekend extends Activity implements OnClickListener {

    private TextView yourDate;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.longweekend);
        yourDate = (TextView) findViewById(R.id.yourDate);
        //yourDate.setText(currentDate());
        Button findLongWeekend = (Button) findViewById(R.id.findLongweekendButton);
        findLongWeekend.setOnClickListener(this);
        yourDate.setOnClickListener(new DatePickerFragment(this, yourDate));
        radioGroup = (RadioGroup) findViewById(R.id.longweekendOptionsGroup);
    }

    @Override
    public void onClick(View v) {
        try{
            Class<?> name = Class.forName("com.foohyfooh.longweekend.Display");
            Intent intent = new Intent(this, name);
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
                                currentDate(), yourDate.getText().toString())
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
                                currentDate(), yourDate.getText().toString()),
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

    private String currentDate(){
        final GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
        int day = cal.get(GregorianCalendar.DAY_OF_MONTH);
        int month = cal.get(GregorianCalendar.MONTH) + 1;//Months are 0 indexed
        int year = cal.get(GregorianCalendar.YEAR);
        return String.format("%d-%02d-%02d", year, month, day);
    }

}
