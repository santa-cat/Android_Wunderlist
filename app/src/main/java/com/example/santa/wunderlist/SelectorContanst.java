package com.example.santa.wunderlist;

import java.util.ArrayList;

/**
 * Created by santa on 16/7/19.
 */
public class SelectorContanst {
    private static SelectorContanst instance = null;
    public static ArrayList<String> MONTHS = null;
    public static ArrayList<String> YEARS = null;
    public static ArrayList<String> DAYS = null;
    public static ArrayList<String> HOURS = null;
    public static ArrayList<String> MINS = null;


    public static ArrayList<String> getMonths() {
        if (null == MONTHS) {
            synchronized (SelectorContanst.class){
                MONTHS = new ArrayList<>();
                for (int i = 1 ; i<=12; i++) {
                    MONTHS.add(i+"月");
                }
            }
        }
        return MONTHS;
    }


    public static ArrayList<String> getYears() {
        if (null == YEARS) {
            synchronized (SelectorContanst.class){
                YEARS = new ArrayList<>();
                for (int i = 1900 ; i<=3000; i++) {
                    YEARS.add(i+"年");
                }
            }
        }
        return YEARS;
    }


    public static ArrayList<String> getDays() {
        if (null == DAYS) {
            synchronized (SelectorContanst.class){
                DAYS = new ArrayList<>();
                for (int i = 1 ; i<=31; i++) {
                    DAYS.add(i+"日");
                }
            }
        }
        return DAYS;
    }


    public static ArrayList<String> getHours() {
        if (null == HOURS) {
            synchronized (SelectorContanst.class){
                HOURS = new ArrayList<>();
                for (int i = 0 ; i<=23; i++) {
                    HOURS.add(i+"时");
                }
            }
        }
        return HOURS;
    }


    public static ArrayList<String> getMins() {
        if (null == MINS) {
            synchronized (SelectorContanst.class){
                MINS = new ArrayList<>();
                for (int i = 0 ; i<=59; i++) {
                    MINS.add(i+"分");
                }
            }
        }
        return MINS;
    }

}
