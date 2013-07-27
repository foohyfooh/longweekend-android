package com.foohyfooh.longweekend;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserDates extends Fragment implements View.OnClickListener {

    private ListView dates;
    private ArrayList<String> allDates;
    private ArrayAdapter<String> adapter;
    private TextView date;
    private EditText name, desc;
    private SharedPreferences preferences;
    private static final String preference_location = "user_dates";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_dates, container, false);
        dates = (ListView) rootView.findViewById(R.id.user_dates_custom_dates);
        setList();
        dates.setAdapter(adapter);
        date = (TextView) rootView.findViewById(R.id.user_dates_date);
        date.setText(Utils.currentDate());
        date.setOnClickListener(new DatePickerFragment(getActivity(), date));
        name = (EditText) rootView.findViewById(R.id.user_dates_name);
        desc = (EditText) rootView.findViewById(R.id.user_dates_desc);
        rootView.findViewById(R.id.user_dates_add).setOnClickListener(this);
        rootView.findViewById(R.id.user_dates_delete).setOnClickListener(this);
        return rootView;
    }

    private void setList(){
        preferences = getActivity().getSharedPreferences(preference_location, Context.MODE_PRIVATE);
        ArrayList<String> values = new ArrayList(preferences.getAll().values());
        allDates = new ArrayList<String>();
        for(String value: values){
            try {
                JSONObject jsonObject = new JSONObject(value);
                String nameValue = jsonObject.getString("name");
                String dateValue = jsonObject.getString("date");
                String descValue = jsonObject.getString("desc");
                allDates.add(String.format("Name:%s Date:%s Desc:%s", nameValue, dateValue, descValue));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, allDates);
    }

    public static String getList(FragmentActivity fragmentActivity){
        SharedPreferences preferences = fragmentActivity.getSharedPreferences(preference_location, Context.MODE_PRIVATE);
        ArrayList<String> values = new ArrayList(preferences.getAll().values());
        JSONArray jsonArray = new JSONArray();
        for(String value: values){
            try {
                jsonArray.put(new JSONObject(value));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray.toString();
    }

    @Override
    public void onClick(View view) {
        SharedPreferences.Editor editor = preferences.edit();
        switch (view.getId()){
            case R.id.user_dates_add:
                String nameValue = name.getText().toString();
                String dateValue = date.getText().toString();
                String descValue = desc.getText().toString();

                if(nameValue.isEmpty() || dateValue.isEmpty() || descValue.isEmpty()){
                    Toast.makeText(getActivity(), "Sorry but please enter a value in each field", Toast.LENGTH_SHORT).show();
                    return;
                }

                allDates.add(String.format("Name:%s Date:%s Desc:%s", nameValue, dateValue, descValue));
                String newDate = String.format("{name:%s, date:%s, desc:%s}",nameValue, dateValue, descValue);
                editor.putString(dateValue, newDate);
                editor.commit();
                adapter.notifyDataSetChanged();
                break;
            case R.id.user_dates_delete:
                editor.clear();
                allDates.clear();
                editor.commit();
                adapter.notifyDataSetChanged();
                break;
        }
    }

}