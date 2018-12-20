package id.co.halloarif.catatanku.view.activity.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.presenter.OnSwipeTouchListener;
import id.co.halloarif.catatanku.view.activity.Main.adapter.AcaraSummaryRVAdapter;

import static id.co.halloarif.catatanku.view.activity.LogReg.LoginActivity.*;

public class AcaraSummaryActivity extends AppCompatActivity {

    private FloatingActionButton fabAcaraSummary;
    private RecyclerView rvAcaraSummary;
    private AcaraSummaryRVAdapter adapter;
    private ImageView ivAcaraSummaryCreatefvbi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acara_summary_coordinat);

        initComponent();
        initParam();
        initSession();
        initContent();
        initListener();
    }

    private void initComponent() {
        fabAcaraSummary = (FloatingActionButton) findViewById(R.id.fabAcaraSummary);
        rvAcaraSummary = (RecyclerView) findViewById(R.id.rvAcaraSummary);
        ivAcaraSummaryCreatefvbi = (ImageView) findViewById(R.id.ivAcaraSummaryCreate);
    }

    private void initParam() {

    }

    private void initSession() {

    }

    private void initContent() {
        adapter = new AcaraSummaryRVAdapter();
        rvAcaraSummary.setAdapter(adapter);
        rvAcaraSummary.setLayoutManager(new LinearLayoutManager(this));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        ivAcaraSummaryCreatefvbi.setOnTouchListener(new OnSwipeTouchListener(AcaraSummaryActivity.this) {
            @Override
            public void onSwipeTop() {
                super.onSwipeTop();
                moveTo(AcaraSummaryActivity.this, AcaraActivity.class, false);
                overridePendingTransition(R.anim.masuk_dari_bawah, R.anim.keluar_ke_atas);
            }
        });
    }

    private void moveToAcara() {
        Intent intent = new Intent(AcaraSummaryActivity.this, AcaraActivity.class);
        startActivity(intent);
        //finish();
    }
}
