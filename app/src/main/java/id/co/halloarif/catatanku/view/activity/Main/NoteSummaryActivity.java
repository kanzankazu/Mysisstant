package id.co.halloarif.catatanku.view.activity.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.presenter.OnSwipeTouchListener;
import id.co.halloarif.catatanku.view.activity.Main.adapter.NoteSummaryRVAdapter;

public class NoteSummaryActivity extends AppCompatActivity {

    private FloatingActionButton fabNoteSummary;
    private RecyclerView rvNoteSummary;
    private ImageView ivNoteSummaryCreatefvbi;
    private NoteSummaryRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_summary_coordinat);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initComponent();
        initParam();
        initSession();
        initContent();
        initListener();

    }

    private void initComponent() {
        fabNoteSummary = (FloatingActionButton) findViewById(R.id.fabNoteSummary);
        rvNoteSummary = (RecyclerView) findViewById(R.id.rvNoteSummary);
        ivNoteSummaryCreatefvbi = (ImageView) findViewById(R.id.ivNoteSummaryCreate);
    }

    private void initParam() {

    }

    private void initSession() {

    }

    @SuppressLint("RestrictedApi")
    private void initContent() {

        fabNoteSummary.setVisibility(View.GONE);

        adapter = new NoteSummaryRVAdapter();
        rvNoteSummary.setLayoutManager(new LinearLayoutManager(this));
        rvNoteSummary.setItemAnimator(new DefaultItemAnimator());
        rvNoteSummary.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvNoteSummary.setAdapter(adapter);

        /*AlarmRecyclerItemTouchHelper itemTouchHelper = new AlarmRecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, new AlarmRecyclerItemTouchHelper.RecyclerItemTouchHelperListener() {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
                if (viewHolder instanceof AlarmSummaryRVAdapter.ViewHolder) {
                    // get the removed item name to display it in snack bar
                    String name = alarmModels.get(viewHolder.getAdapterPosition()).getAlarm_title();

                    // backup of removed item for undo purpose
                    final NoteModel deletedItem = alarmModels.get(viewHolder.getAdapterPosition());
                    final int deletedIndex = viewHolder.getAdapterPosition();

                    // remove the item from recycler view
                    adapter.removeAt(viewHolder.getAdapterPosition());

                    // showing snack bar with Undo option
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), name + " removed from cart!", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // undo is selected, restore the deleted item
                            adapter.restoreData(deletedItem, deletedIndex);
                        }
                    });
                    snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                }
            }
        });
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(rvNoteSummary);*/

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        fabNoteSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToNote();
            }
        });

        ivNoteSummaryCreatefvbi.setOnTouchListener(new OnSwipeTouchListener(NoteSummaryActivity.this) {
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
                moveToNote();
            }

            @Override
            public void onSwipeBottom() {
                super.onSwipeBottom();
            }
        });
    }

    private void moveToNote() {
        Intent intent = new Intent(NoteSummaryActivity.this, NoteActivity.class);
        startActivity(intent);
        //finish();
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
    protected void onResume() {
        super.onResume();
        /*alarmModels = db.alarmsGet();
        adapter.setData(alarmModels);*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
