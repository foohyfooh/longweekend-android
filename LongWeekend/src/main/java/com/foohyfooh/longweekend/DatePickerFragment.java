package com.foohyfooh.longweekend;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.GregorianCalendar;


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private final FragmentActivity activity;
    private final TextView textView;

    public DatePickerFragment(FragmentActivity activity, TextView textView) {
        this.activity = activity;
        this.textView = textView;
    }

    @Override
    public void onClick(View view) {
        DialogFragment newFragment = new DatePickerFragment(activity,textView);
        newFragment.show(activity.getSupportFragmentManager(), "datePicker");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final GregorianCalendar c = (GregorianCalendar) GregorianCalendar.getInstance();
        int year = c.get(GregorianCalendar.YEAR);
        int month = c.get(GregorianCalendar.MONTH);
        int day = c.get(GregorianCalendar.DAY_OF_MONTH);

        return new DatePickerDialog(activity, this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //Month is zero indexed so I have to add 1 to it
        textView.setText(String.format("%d-%02d-%02d", year, month + 1, day));
    }

}
