package com.foohyfooh.longweekend;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class GetJSON extends AsyncTask<String, Void, List<Data>> {

	public GetJSON(){
		
	}
	
	protected List<Data> doInBackground(String... params) {
		try{
			URL url = new URL(params[0]);
			String json = IOUtils.toString(url.openStream());
			List<Data> list = new ArrayList<Data>();
			JSONArray jsonArray = new JSONArray(json);
			for(int count = 0; count < jsonArray.length(); count++){
				JSONObject jsonObject = jsonArray.getJSONObject(count);
				Data value = new Data(jsonObject.getString("holidayName"), jsonObject.getString("holidayDate"), jsonObject.getString("holidayDesc"));
				list.add(value);
			}
			return list;
		}catch (IOException e) {
			return null;
		} catch (JSONException e) {
			return null;
		}
	}

}
