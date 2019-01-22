package id.co.halloarif.catatanku.view.activity.Main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import id.co.halloarif.catatanku.R;

public class AlarmActivityShow extends AppCompatActivity {

    private TextView tvAlarmShowHourfvbi;
    private TextView tvAlarmShowTitlefvbi;
    private CardView tvAlarmShowSnoozefvbi;
    private CardView cvAlarmShowDismissfvbi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_show);

        initComponent();
        initParam();
        initSession();
        initContent();
        initListener();
    }

    private void initComponent() {
        tvAlarmShowHourfvbi = (TextView) findViewById(R.id.tvAlarmShowHour);
        tvAlarmShowTitlefvbi = (TextView) findViewById(R.id.tvAlarmShowTitle);
        tvAlarmShowSnoozefvbi = (CardView) findViewById(R.id.tvAlarmShowSnooze);
        cvAlarmShowDismissfvbi = (CardView) findViewById(R.id.cvAlarmShowDismiss);
    }

    private void initParam() {

    }

    private void initSession() {

    }

    private void initContent() {

    }

    private void initListener() {

    }
}
