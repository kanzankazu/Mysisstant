package id.co.halloarif.catatanku.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import id.co.halloarif.catatanku.support.util.ServiceUtil;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ServiceUtil.startService(context, PopupService.class);
    }
}
