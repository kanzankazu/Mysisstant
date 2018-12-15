package id.co.halloarif.catatanku.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import id.co.halloarif.catatanku.support.util.ServiceUtil;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent1 = new Intent(context, PopupService.class);
        intent1.putExtra("title", intent.getStringExtra("title"));
        intent1.putExtra("msg", intent.getStringExtra("msg"));
        intent1.putExtra("record", intent.getStringExtra("record"));
        intent1.putExtra("ringtone", intent.getStringExtra("ringtone"));

        Log.d("Lihat", "onReceive AlarmReceiver : " + intent.getStringExtra("title"));
        Log.d("Lihat", "onReceive AlarmReceiver : " + intent.getStringExtra("msg"));
        Log.d("Lihat", "onReceive AlarmReceiver : " + intent.getStringExtra("record"));
        Log.d("Lihat", "onReceive AlarmReceiver : " + intent.getStringExtra("ringtone"));

        ServiceUtil.startService(context, intent1);
    }
}
