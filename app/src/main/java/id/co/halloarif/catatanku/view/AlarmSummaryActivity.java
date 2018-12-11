package id.co.halloarif.catatanku.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.QuickContactBadge;

import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.support.util.SystemUtil;
import id.co.halloarif.catatanku.support.widget.OnSwipeTouchListener;

public class AlarmSummaryActivity extends AppCompatActivity {

    private FloatingActionButton fabAlarmSummary;
    private RecyclerView rvAlarmSummary;
    private ImageView ivAlarmSummaryCreatefvbi;

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
        rvAlarmSummary = (RecyclerView) findViewById(R.id.rvAlarmSummary);
        ivAlarmSummaryCreatefvbi = (ImageView) findViewById(R.id.ivAlarmSummaryCreate);
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

        ivAlarmSummaryCreatefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToAlarm();
            }
        });

        ivAlarmSummaryCreatefvbi.setOnTouchListener(new OnSwipeTouchListener(AlarmSummaryActivity.this){
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
            }

            @Override
            public void onSwipeTop() {
                super.onSwipeTop();
                moveToAlarm();
            }

            @Override
            public void onSwipeBottom() {
                super.onSwipeBottom();
            }
        });
    }

    private void moveToAlarm() {
        Intent intent = new Intent(AlarmSummaryActivity.this, AlarmActivity.class);
        startActivity(intent);
    }

}
