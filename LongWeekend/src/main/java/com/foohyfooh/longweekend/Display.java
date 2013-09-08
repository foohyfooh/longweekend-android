package com.foohyfooh.longweekend;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class Display extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] list = getIntent().getStringArrayExtra("longWeekend");
		setListAdapter(new ArrayAdapter<String>(Display.this, android.R.layout.simple_list_item_1, list));
    }

}
