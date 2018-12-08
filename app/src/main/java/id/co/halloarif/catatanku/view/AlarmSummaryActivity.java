package id.co.halloarif.catatanku.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import id.co.halloarif.catatanku.R;

public class AlarmSummaryActivity extends AppCompatActivity {

    private FloatingActionButton fabAlarmSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_summary_coordinat);

        initComponent();
        initParam();
        initSession();
        initContent();
        initListener();


    }

    private void initComponent() {
        fabAlarmSummary = (FloatingActionButton) findViewById(R.id.fabAlarmSummary);
        RecyclerView rvAlarmSummary = (RecyclerView) findViewById(R.id.rvAlarmSummary);

    }

    private void initParam() {

    }

    private void initSession() {

    }

    private void initContent() {

    }

    private void initListener() {
        fabAlarmSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToAlarm();
            }
        });
    }

    private void moveToAlarm() {
        Intent intent = new Intent(AlarmSummaryActivity.this, AlarmActivity.class);
        startActivity(intent);
    }

}
