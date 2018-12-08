package id.co.halloarif.catatanku.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.view.adapter.NoteSummaryRVAdapter;

public class NoteSummaryActivity extends AppCompatActivity {

    private FloatingActionButton fabNoteSummary;
    private RecyclerView rvNoteSummary;
    private NoteSummaryRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_summary_coordinat);

        initComponent();
        initParam();
        initSession();
        initContent();
        initListener();



    }

    private void initComponent() {
        fabNoteSummary = (FloatingActionButton) findViewById(R.id.fabNoteSummary);
        rvNoteSummary = (RecyclerView) findViewById(R.id.rvNoteSummary);
    }

    private void initParam() {

    }

    private void initSession() {

    }

    private void initContent() {
        adapter = new NoteSummaryRVAdapter();
        rvNoteSummary.setAdapter(adapter);
        rvNoteSummary.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initListener() {
        fabNoteSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToAcara();
            }
        });
    }

    private void moveToAcara() {
        Intent intent = new Intent(NoteSummaryActivity.this, NoteActivity.class);
        startActivity(intent);
        //finish();
    }

}
