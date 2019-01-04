package id.co.halloarif.catatanku.view.activity.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.database.SQLiteHelper;
import id.co.halloarif.catatanku.presenter.OnSwipeTouchListener;
import id.co.halloarif.catatanku.view.activity.Main.adapter.AlarmSummaryRVAdapter;

public class AlarmSummaryActivity extends AppCompatActivity {

    SQLiteHelper db = new SQLiteHelper(AlarmSummaryActivity.this);

    private FloatingActionButton fabAlarmSummary;
    private RecyclerView rvAlarmSummary;
    private ImageView ivAlarmSummaryCreatefvbi;
    private AlarmSummaryRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_summary_coordinat);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        fabAlarmSummary.setVisibility(View.GONE);

        adapter = new AlarmSummaryRVAdapter();
        rvAlarmSummary.setAdapter(adapter);
        rvAlarmSummary.setLayoutManager(new LinearLayoutManager(this));

        adapter.setData(db.alarmsGet());
    }

    private void initListener() {
        fabAlarmSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToAlarm();
            }
        });

        ivAlarmSummaryCreatefvbi.setOnTouchListener(new OnSwipeTouchListener(AlarmSummaryActivity.this) {
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
        overridePendingTransition(R.anim.masuk_dari_bawah, R.anim.fadeout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_summary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuHomeActivity) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    /*@Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        allPixels = savedInstanceState.getFloat(BUNDLE_LIST_PIXELS);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat(BUNDLE_LIST_PIXELS, allPixels);
    }*/

}
