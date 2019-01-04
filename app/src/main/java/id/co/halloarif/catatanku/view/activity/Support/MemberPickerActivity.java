package id.co.halloarif.catatanku.view.activity.Support;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import id.co.halloarif.catatanku.R;

public class MemberPickerActivity extends AppCompatActivity {

    private EditText etMemberAutoCompleteInputfvbi;
    private ImageView ivMemberAutoCompleteDeletefvbi;
    private RecyclerView rvMemberAutoCompletefvbi;
    private MemberAutoCompleteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_picker);

        initComponent();
        initParam();
        initSession();
        initContent();
        initListener();
    }

    private void initComponent() {
        etMemberAutoCompleteInputfvbi = (EditText) findViewById(R.id.etMemberAutoCompleteInput);
        ivMemberAutoCompleteDeletefvbi = (ImageView) findViewById(R.id.ivMemberAutoCompleteDelete);
        rvMemberAutoCompletefvbi = (RecyclerView) findViewById(R.id.rvMemberAutoComplete);

    }

    private void initParam() {

    }

    private void initSession() {

    }

    private void initContent() {
        initRecycleView();
    }

    private void initRecycleView() {
        adapter = new MemberAutoCompleteAdapter(new MemberAutoCompleteAdapter.MemberAutoCompleteAdapterListener() {
            @Override
            public void onClick(int i) {

            }
        });
        rvMemberAutoCompletefvbi.setAdapter(adapter);
        rvMemberAutoCompletefvbi.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initListener() {
        ivMemberAutoCompleteDeletefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etMemberAutoCompleteInputfvbi.setText("");
                ivMemberAutoCompleteDeletefvbi.setVisibility(View.GONE);
            }
        });

        etMemberAutoCompleteInputfvbi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().equals("")) {
                    rvMemberAutoCompletefvbi.setVisibility(View.VISIBLE);
                    ivMemberAutoCompleteDeletefvbi.setVisibility(View.VISIBLE);
                } else {
                    rvMemberAutoCompletefvbi.setVisibility(View.GONE);
                    ivMemberAutoCompleteDeletefvbi.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}
