package id.co.halloarif.catatanku.support.util;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import id.co.halloarif.catatanku.R;

public class NotifUtil {

    public static void setNotification(Context context, @Nullable String title, @Nullable String text, int smallIcon, int bigIcon, boolean isNotCancelAble, PendingIntent pendingIntent, int NOTIFICATION_ID) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), bigIcon);
        setNotification(context, title, text, smallIcon, bitmap, isNotCancelAble, pendingIntent, NOTIFICATION_ID);
    }

    public static void setNotificationStandart(Context context, @Nullable String title, @Nullable String text, int smallIcon, int bigIcon, boolean isNotCancelAble, PendingIntent pendingIntent, int NOTIFICATION_ID) {
        /*//set intents and pending intents to call service on click of "dismiss" action button of notification
        Intent dismissIntent = new Intent(context, MyService.class);
        dismissIntent.setAction(ACTION_DISMISS);
        PendingIntent piDismiss = PendingIntent.getService(context, 0, dismissIntent, 0);

        //set intents and pending intents to call service on click of "snooze" action button of notification
        Intent snoozeIntent = new Intent(context, MyService.class);
        snoozeIntent.setAction(ACTION_SNOOZE);
        PendingIntent piSnooze = PendingIntent.getService(context, 0, snoozeIntent, 0);*/

        //build notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(smallIcon)
                .setContentTitle("Ping Notification")
                .setContentText("Tomorrow will be your birthday.")
                .setDefaults(Notification.DEFAULT_ALL) // must requires VIBRATE permission
                .setPriority(NotificationCompat.PRIORITY_HIGH); //must give priority to High, Max which will considered as heads-up notification
                        /*.addAction(R.drawable.ic_alarm_black, "Dismiss", piDismiss)
                        .addAction(R.drawable.ic_add_white, "Snooze", piSnooze);*/

        // Gets an instance of the NotificationManager service
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //to post your notification to the notification bar with a id. If a notification with same id already exists, it will get replaced with updated information.
        notificationManager.notify(0, builder.build());
    }

    public static void setNotification(Context context, @Nullable String title, @Nullable String text, int smallIcon, Bitmap bigIcon, boolean isNotCancelAble, PendingIntent pendingIntent, int NOTIFICATION_ID) {

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setOngoing(isNotCancelAble)
                .setPriority(Notification.PRIORITY_MAX)
                .setSound(uri);
        //.setProgress()
        ;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    public static void lll(Activity context, Class<?> targetClass) {
        final int NOTIF_ID = 1234;
        NotificationCompat.Builder mBuilder;
        NotificationManager mNotificationManager;
        RemoteViews mRemoteViews;
        Notification mNotification;

        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // we need to build a basic notification first, then update it
        /*Intent intentNotif = new Intent(context, targetClass);
        intentNotif.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendIntent = PendingIntent.getActivity(context, 0, intentNotif, PendingIntent.FLAG_UPDATE_CURRENT);*/

        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.custom_notification_small);
        //mRemoteViews.setImageViewResource(R.id.notif_icon, R.mipmap.ic_launcher);
        //mRemoteViews.setTextViewText(R.id.notif_title, context.getResources().getString(R.string.app_name));
        //mRemoteViews.setTextViewText(R.id.notif_content, "OK");
        mRemoteViews.setTextViewText(R.id.notif_icon2, "OK");

        mBuilder = new NotificationCompat.Builder(context);

        CharSequence ticker = "tes";

        int apiVersion = Build.VERSION.SDK_INT;

        if (apiVersion < Build.VERSION_CODES.HONEYCOMB) {
            mNotification = new Notification(R.mipmap.ic_launcher, ticker, System.currentTimeMillis());
            mNotification.contentView = mRemoteViews;
            //mNotification.contentIntent = pendingIntent;

            //mNotification.flags |= Notification.FLAG_NO_CLEAR; //Do not clear the notification
            mNotification.defaults |= Notification.DEFAULT_LIGHTS;

            mNotificationManager.notify(NOTIF_ID, mNotification);
        } else if (apiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            mBuilder.setSmallIcon(R.mipmap.ic_launcher)
                    // .setAutoCancel(isNotCancelAble) //Do not clear the notification
                    .setOngoing(true)
                    //.setContentIntent(pendingIntent)
                    //.setContent(mRemoteViews)
                    .setTicker(ticker, mRemoteViews);

            mNotificationManager.notify(NOTIF_ID, mBuilder.build());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isNotificationShow1(Context context, int NOTIFICATION_ID) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        StatusBarNotification[] notifications = notificationManager.getActiveNotifications();
        for (StatusBarNotification notification : notifications) {
            return notification.getId() == NOTIFICATION_ID;
        }
        return false;
    }

    private static boolean isNotificationShow2(Context context, int NOTIFICATION_ID) {
        Intent notificationIntent = new Intent(context, context.getClass());
        PendingIntent test = PendingIntent.getActivity(context, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_NO_CREATE);
        return test != null;
    }

    public static void clearNotification(Context context, int NOTIFICATION_ID) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }

    public static void clearNotificationAll(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

}
