package com.foohyfooh.longweekend;


import java.util.GregorianCalendar;

public class Utils {

    public static String currentDate(){
        final GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
        int day = cal.get(GregorianCalendar.DAY_OF_MONTH);
        int month = cal.get(GregorianCalendar.MONTH) + 1;//Months are 0 indexed
        int year = cal.get(GregorianCalendar.YEAR);
        return String.format("%d-%02d-%02d", year, month, day);
    }
}
