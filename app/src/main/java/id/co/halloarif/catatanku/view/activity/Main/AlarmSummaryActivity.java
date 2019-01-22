package id.co.halloarif.catatanku.view.activity.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import id.co.halloarif.catatanku.ISeasonConfig;
import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.database.SQLiteHelper;
import id.co.halloarif.catatanku.model.AlarmModel;
import id.co.halloarif.catatanku.presenter.OnSwipeTouchListener;
import id.co.halloarif.catatanku.support.util.DateTimeAlarmUtil;
import id.co.halloarif.catatanku.view.activity.Main.adapter.AlarmRecyclerItemTouchHelper;
import id.co.halloarif.catatanku.view.activity.Main.adapter.AlarmSummaryRVAdapter;

public class AlarmSummaryActivity extends AppCompatActivity {

    SQLiteHelper db = new SQLiteHelper(AlarmSummaryActivity.this);

    private FloatingActionButton fabAlarmSummary;
    private RecyclerView rvAlarmSummary;
    private ImageView ivAlarmSummaryCreatefvbi;
    private AlarmSummaryRVAdapter adapter;
    private List<AlarmModel> alarmModels = new ArrayList<>();

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
        alarmModels = db.alarmsGet();

        fabAlarmSummary.setVisibility(View.GONE);

        adapter = new AlarmSummaryRVAdapter(AlarmSummaryActivity.this, new AlarmSummaryRVAdapter.AlarmSummaryRVListener() {
            @Override
            public void onClick(AlarmModel model) {
                Intent intent = new Intent(AlarmSummaryActivity.this, AlarmActivity.class);
                intent.putExtra(ISeasonConfig.INTENT_ID, model.getAlarm_id());
                intent.putExtra(ISeasonConfig.INTENT_ACTIVE, model.getAlarm_is_Active());
                startActivity(intent);
                overridePendingTransition(R.anim.masuk_dari_bawah, R.anim.fadeout);

                DateTimeAlarmUtil.setAlarmSwitch(getApplicationContext(), model, 0);
                Snackbar.make(findViewById(android.R.id.content), "Alarm Deactived", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onChange(AlarmModel model, boolean state) {
                DateTimeAlarmUtil.setAlarmSwitch(getApplicationContext(), model, state ? 1 : 0);
                if (state) {
                    Snackbar.make(findViewById(android.R.id.content), "Alarm Actived", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Alarm Deactived", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        rvAlarmSummary.setLayoutManager(new LinearLayoutManager(this));
        rvAlarmSummary.setItemAnimator(new DefaultItemAnimator());
        rvAlarmSummary.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvAlarmSummary.setAdapter(adapter);

        AlarmRecyclerItemTouchHelper itemTouchHelper = new AlarmRecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, new AlarmRecyclerItemTouchHelper.RecyclerItemTouchHelperListener() {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
                if (viewHolder instanceof AlarmSummaryRVAdapter.ViewHolder) {
                    AlarmModel alarmModel = alarmModels.get(viewHolder.getAdapterPosition());

                    // get the removed item name to display it in snack bar
                    String title = alarmModel.getAlarm_title();

                    // backup of removed item for undo purpose
                    final AlarmModel deletedItem = alarmModel;
                    final int deletedIndex = viewHolder.getAdapterPosition();

                    // remove the item from recycler view
                    adapter.removeAt(viewHolder.getAdapterPosition());
                    db.alarmDelete(alarmModel.getAlarm_id());
                    DateTimeAlarmUtil.setAlarmSwitch(getApplicationContext(), alarmModel, 0);

                    // showing snack bar with Undo option
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), title + " removed from alarm!", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // undo is selected, restore the deleted item
                            adapter.restoreData(deletedItem, deletedIndex);
                            db.alarmSave(deletedItem);
                            DateTimeAlarmUtil.setAlarmSwitch(getApplicationContext(), deletedItem, deletedItem.getAlarm_is_Active() == 1 ? 1 : 0);
                        }
                    });
                    snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                }
            }
        });
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(rvAlarmSummary);

        adapter.setData(alarmModels);

        /*ItemTouchHelper.SimpleCallback itemTouchHelper1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swiped from recycler view
                // remove it from adapter
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        new ItemTouchHelper(itemTouchHelper1).attachToRecyclerView(rvAlarmSummary);*/
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

    /*@Override
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
    }*/

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
    protected void onResume() {
        super.onResume();
        alarmModels = db.alarmsGet();
        adapter.setData(alarmModels);
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
