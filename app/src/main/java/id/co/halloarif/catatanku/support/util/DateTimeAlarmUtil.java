package id.co.halloarif.catatanku.support.util;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class DateTimeAlarmUtil extends DateTimeUtil {
    public static void setAlarm(Activity activity, PendingIntent pendingIntent, int hourOfDay, int minute) {
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();

        calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calSet.set(Calendar.MINUTE, minute);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        if (calSet.compareTo(calNow) <= 0) {
            //jika ternyata waktu lewat maka alarm akan di atur untuk besok
            calSet.add(Calendar.DATE, 1);
        }

        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pendingIntent);
    }

    /**
     * @param activity
     * @param pendingIntent
     * @param hourOfDay
     * @param minute
     * @param weekOfWeek    sun 1,mon 2, tue 3, wed 4, thu 5, fri 6, sat 7
     */
    public static void setAlarmRepeatDay(Activity activity, PendingIntent pendingIntent, int hourOfDay, int minute, int weekOfWeek) {
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();

        calSet.set(Calendar.DAY_OF_WEEK, weekOfWeek);
        calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calSet.set(Calendar.MINUTE, minute);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        if (calSet.compareTo(calNow) <= 0) {
            //jika ternyata waktu lewat maka alarm akan di atur untuk besok
            calSet.add(Calendar.DATE, 1);
        }

        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), WEEK_MILLIS, pendingIntent);
    }

    public static void cancelAlarm(Activity activity, PendingIntent pendingIntent) {
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    public static void cancelAllAlarm(Activity activity, Class<?> targetClass) {
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);

        Intent updateServiceIntent = new Intent(activity, targetClass);
        PendingIntent pendingUpdateIntent = PendingIntent.getService(activity, 0, updateServiceIntent, 0);

        // Cancel alarms
        try {
            alarmManager.cancel(pendingUpdateIntent);
        } catch (Exception e) {
            Log.e("Lihat", "cancelAllAlarm DateTimeAlarmUtil : " + e.toString());
        }
    }
}


