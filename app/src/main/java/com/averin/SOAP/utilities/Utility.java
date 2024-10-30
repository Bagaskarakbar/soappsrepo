package com.averin.SOAP.utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utility {

    public final static String baseUrl = "http://m.sirs.co.id/_ws";
    public final static String ImageUrl = "http://m.sirs.co.id/_images/";

    // locale setups
    public Locale setIndoLocale() {
        Locale locale = new Locale("in", "ID");
        return locale;
    }
    //end locale setups

    //date parse, formats, compares and current dates
    public Date ParseDate(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", setIndoLocale());
            return dateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public Date ParseHour(String date) {
        try {
            Date dateNormal = ParseDate(date);
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa", setIndoLocale());
            return dateFormat.parse(FormatHour(dateNormal));
        } catch (ParseException e) {
            return null;
        }
    }

    public int getDayOfWeek() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("u",setIndoLocale());
        int day = Integer.parseInt(df.format(c));
        return day;
    }

    public Calendar getHourNow() {
        Date c = Calendar.getInstance().getTime();
        Calendar timeNow = Calendar.getInstance();
        SimpleDateFormat dfh = new SimpleDateFormat("HH",setIndoLocale());
        int hour = Integer.parseInt(dfh.format(c));
        SimpleDateFormat dfm = new SimpleDateFormat("mm",setIndoLocale());
        int minute = Integer.parseInt(dfm.format(c));
        timeNow.set(Calendar.HOUR_OF_DAY, hour);
        timeNow.set(Calendar.MINUTE, minute);
        return timeNow;
    }

    public String getDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public String FormatHour(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa", setIndoLocale());
        return dateFormat.format(date);
    }

    public String FormatStringDate(String date){
        Date tempdate = ParseSimpleDate(date);
        return FormatSimpleDate(tempdate);
    }
    public Date ParseSimpleDate(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", setIndoLocale());
            return dateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public String FormatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", setIndoLocale());
        return dateFormat.format(date);
    }

    public String FormatSimpleDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", setIndoLocale());
        return dateFormat.format(date);
    }

    public boolean IsTimeAlreadyPassed(Date timeDone) {
        Calendar calendarTimeDone = changeToHour(timeDone);
        if (calendarTimeDone.getTime().before(getHourNow().getTime()))
            return true;
        else
            return false;
    }

    public boolean IsHourBefore(Date Hour1, Date Hour2){
        if (Hour1.before(Hour2))
            return true;
        else
            return false;
    }

    public Calendar changeToHour(Date time) {
        SimpleDateFormat dateFormatDone = new SimpleDateFormat("HH:mm", setIndoLocale());
        String stringTimeDone = dateFormatDone.format(time);
        String[] brokenTime = stringTimeDone.split(":");
        Calendar timeProcessed = Calendar.getInstance();
        timeProcessed.set(Calendar.HOUR_OF_DAY, Integer.parseInt(brokenTime[0]));
        timeProcessed.set(Calendar.MINUTE, Integer.parseInt(brokenTime[1]));
        timeProcessed.set(Calendar.SECOND, 0);
        return timeProcessed;
    }

    public Date addMinutesToHourFormat(Date time,int Minutes){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MINUTE, Minutes);
        return calendar.getTime();
    }
    //end date parse and formats

    //inputs
    public void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.getApplicationContext().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            view.clearFocus();
        }
    }

    //end inputs
}
