package id.co.halloarif.catatanku.support.util;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class DateTimeAlarmUtil extends DateTimeUtil {
    public static PendingIntent setPendingIntentMakeAlarm(Context context, Intent intent, int REQ_CODE) {
        return PendingIntent.getBroadcast(context, REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static PendingIntent setPendingIntentMake(Context context, Intent intent, int REQ_CODE) {
        return PendingIntent.getBroadcast(context, REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static PendingIntent setPendingIntentRemove(Context context, Intent intent, int REQ_CODE) {
        return PendingIntent.getBroadcast(context, REQ_CODE, intent, 0);
    }

    public static boolean isAlarmActive(Context context, Intent intent, int REQ_CODE) {
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE) != null;
    }

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
            Toast.makeText(activity, "Alarm Has set At " + hourOfDay + ":" + minute + " Tomorrow", Toast.LENGTH_SHORT).show();
        }

        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.v("AlarmManager", "Starting AlarmManager for >= KITKAT version");
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pendingIntent);
        } else {
            Log.v("AlarmManager", "Starting AlarmManager for < KITKAT version");
            alarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pendingIntent);
        }

        Toast.makeText(activity, "Alarm Has set At " + hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();

    }

    /**
     * @param activity
     * @param pendingIntent
     * @param hourOfDay
     * @param minute
     * @param dayOfWeek    sun 1,mon 2, tue 3, wed 4, thu 5, fri 6, sat 7
     */
    public static void setAlarmRepeatDay(Activity activity, PendingIntent pendingIntent, int hourOfDay, int minute, int dayOfWeek) {
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();

        calSet.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calSet.set(Calendar.MINUTE, minute);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        if (calSet.compareTo(calNow) <= 0) {
            //jika ternyata waktu lewat maka alarm akan di atur untuk besok
            calSet.add(Calendar.DATE, 1);
            Toast.makeText(activity, "Alarm Has set At " + hourOfDay + ":" + minute + " Tomorrow", Toast.LENGTH_SHORT).show();
        }

        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), WEEK_MILLIS, pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.v("AlarmManager", "Starting AlarmManager for >= KITKAT version");
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), WEEK_MILLIS, pendingIntent);
        } else {
            Log.v("AlarmManager", "Starting AlarmManager for < KITKAT version");
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), WEEK_MILLIS, pendingIntent);
        }

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


