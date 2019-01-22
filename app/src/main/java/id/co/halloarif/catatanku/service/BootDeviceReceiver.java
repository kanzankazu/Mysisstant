package id.co.halloarif.catatanku.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import id.co.halloarif.catatanku.database.SQLiteHelper;
import id.co.halloarif.catatanku.model.AlarmModel;
import id.co.halloarif.catatanku.support.util.DateTimeAlarmUtil;

public class BootDeviceReceiver extends BroadcastReceiver {
    private static final String TAG_BOOT_BROADCAST_RECEIVER = "BOOT_BROADCAST_RECEIVER";

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        String message = "BootDeviceReceiver onReceive, action is " + action;

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

        Log.d(TAG_BOOT_BROADCAST_RECEIVER, action);

        if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            //startServiceDirectly(context);

            startServiceByAlarm(context);
        }
    }

    /* Start RunAfterBootService service directly and invoke the service every 10 seconds. */
    private void startServiceDirectly(Context context) {
        try {
            while (true) {
                String message = "BootDeviceReceiver onReceive start service directly.";

                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                Log.d(TAG_BOOT_BROADCAST_RECEIVER, message);

                // This intent is used to start background service. The same service will be invoked for each invoke in the loop.
                Intent startServiceIntent = new Intent(context, RunAfterBootService.class);
                context.startService(startServiceIntent);

                // Current thread will sleep one second.
                Thread.sleep(10000);
            }
        } catch (InterruptedException ex) {
            Log.e(TAG_BOOT_BROADCAST_RECEIVER, ex.getMessage(), ex);
        }
    }

    /* Create an repeat Alarm that will invoke the background service for each execution time.
     * The interval time can be specified by your self.  */
    private void startServiceByAlarm(Context context) {
        SQLiteHelper db = new SQLiteHelper(context);
        ArrayList<AlarmModel> models = db.alarmsGet();
        for (int i = 0; i < models.size(); i++) {
            AlarmModel model = models.get(i);

            int i1 = model.getAlarm_is_Active() == 1 ? 1 : 0;

            DateTimeAlarmUtil.setAlarmSwitch(context, model, i1);
            if (i1 == 1) {
                Log.d("Lihat", "startServiceByAlarm BootDeviceReceiver : " + model.getAlarm_title() + "," + model.getAlarm_id() + " - active");
            } else {
                Log.d("Lihat", "startServiceByAlarm BootDeviceReceiver : " + model.getAlarm_title() + "," + model.getAlarm_id() + " - not active");
            }
        }
    }
}
