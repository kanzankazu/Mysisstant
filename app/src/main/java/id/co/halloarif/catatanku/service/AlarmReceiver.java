package id.co.halloarif.catatanku.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import id.co.halloarif.catatanku.support.util.ServiceUtil;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String id = intent.getStringExtra("id");
        String title = intent.getStringExtra("title");
        String msg = intent.getStringExtra("msg");
        String record = intent.getStringExtra("record");
        String ringtone = intent.getStringExtra("ringtone");
        String subid = intent.getStringExtra("subid");

        Intent intent1 = new Intent(context, AlarmService.class);
        intent1.putExtra("id", id);
        intent1.putExtra("title", title);
        intent1.putExtra("msg", msg);
        intent1.putExtra("record", record);
        intent1.putExtra("ringtone", ringtone);
        intent1.putExtra("subid", subid);

        Log.d("Lihat", "onReceive AlarmReceiver : " + intent.getStringExtra("id"));
        Log.d("Lihat", "onReceive AlarmReceiver : " + intent.getStringExtra("title"));
        Log.d("Lihat", "onReceive AlarmReceiver : " + intent.getStringExtra("msg"));
        Log.d("Lihat", "onReceive AlarmReceiver : " + intent.getStringExtra("record"));
        Log.d("Lihat", "onReceive AlarmReceiver : " + intent.getStringExtra("ringtone"));
        Log.d("Lihat", "onReceive AlarmReceiver : " + intent.getStringExtra("subid"));

        ServiceUtil.startService(context, intent1);
    }
}
