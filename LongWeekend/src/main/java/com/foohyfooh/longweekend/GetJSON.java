package com.foohyfooh.longweekend;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class GetJSON extends AsyncTask<String, Void, String[]> {

    public GetJSON(){

    }

    protected String[] doInBackground(String... params) {
        try{
            ArrayList<String> list = new ArrayList<String>();
            for(int i = 0; i < params.length; i++){
                URL url = new URL(params[i]);
                String json = IOUtils.toString(url.openStream());
                JSONArray jsonArray = new JSONArray(json);
                for(int count = 0; count < jsonArray.length(); count++){
                    JSONObject jsonObject = jsonArray.getJSONObject(count);
                    String value = String.format("Holiday: %s Date: %s Desc: %s\n", jsonObject.getString("holidayName"),
                            jsonObject.getString("holidayDate"), jsonObject.getString("holidayDesc"));
                    list.add(value);
                }
            }
            return list.toArray(new String[list.size()]);
        }catch (IOException e) {
            return null;
        } catch (JSONException e) {
            return null;
        }
    }

}