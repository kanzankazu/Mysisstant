package id.co.halloarif.catatanku.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.view.adapter.AcaraSummaryRVAdapter;

public class AcaraSummaryActivity extends AppCompatActivity {

    private FloatingActionButton fabAcaraSummary;
    private RecyclerView rvAcaraSummary;
    private AcaraSummaryRVAdapter adapter;

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

    private void initListener() {
        fabAcaraSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToAcara();
            }
        });
    }

    private void moveToAcara() {
        Intent intent = new Intent(AcaraSummaryActivity.this, AcaraActivity.class);
        startActivity(intent);
        //finish();
    }
}
