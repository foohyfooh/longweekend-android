package com.foohyfooh.longweekend;

public class Data {
		String name, date, desc;

		public Data(String name, String date, String desc) {
			this.name = name;
			this.date = date;
			this.desc = desc;
		}

		@Override
		public String toString() {
			return String.format("Holiday: %s Date: %s Desc: %s\n", name, date, desc);
		}
		
		
	}