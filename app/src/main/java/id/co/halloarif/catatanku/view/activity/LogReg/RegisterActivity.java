package id.co.halloarif.catatanku.view.activity.LogReg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import id.co.halloarif.catatanku.ISeasonConfig;
import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.support.util.InputValidUtil;

public class RegisterActivity extends AppCompatActivity {

    private EditText etLogRegNamefvbi;
    private EditText etLogRegEmailfvbi;
    private EditText etLogRegPhoneNofvbi;
    private EditText etLogRegPassword1fvbi;
    private EditText etLogRegPassword2fvbi;
    private CardView bLogRegExecutefvbi;
    private TextView tvLogRegNowLoginfvbi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initComponent();
        initParam();
        initSession();
        initContent();
        initListener();
    }

    private void initComponent() {
        etLogRegNamefvbi = (EditText) findViewById(R.id.etLogRegName);
        etLogRegEmailfvbi = (EditText) findViewById(R.id.etLogRegEmail);
        etLogRegPhoneNofvbi = (EditText) findViewById(R.id.etLogRegPhoneNo);
        etLogRegPassword1fvbi = (EditText) findViewById(R.id.etLogRegPassword1);
        etLogRegPassword2fvbi = (EditText) findViewById(R.id.etLogRegPassword2);
        bLogRegExecutefvbi = (CardView) findViewById(R.id.bLogRegExecute);
        tvLogRegNowLoginfvbi = (TextView) findViewById(R.id.tvLogRegNowLogin);
    }

    private void initParam() {

    }

    private void initSession() {

    }

    private void initContent() {

    }

    private void initListener() {
        bLogRegExecutefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InputValidUtil.isEmptyField(etLogRegNamefvbi)) {
                    InputValidUtil.errorET(etLogRegNamefvbi, "Data kosong");
                } else if (InputValidUtil.isEmptyField(etLogRegEmailfvbi)) {
                    InputValidUtil.errorET(etLogRegEmailfvbi, "Data kosong");
                } else if (!InputValidUtil.isValidateEmail(etLogRegEmailfvbi.getText().toString().trim())) {
                    InputValidUtil.errorET(etLogRegEmailfvbi, "Please Correct Email Format");
                } else if (InputValidUtil.isEmptyField(etLogRegPhoneNofvbi)) {
                    InputValidUtil.errorET(etLogRegPhoneNofvbi, "Data kosong");
                } else if (!InputValidUtil.isValidatePhoneNumber(etLogRegPhoneNofvbi.getText().toString().trim())) {
                    InputValidUtil.errorET(etLogRegPhoneNofvbi, "Data kosong");
                } else if (InputValidUtil.isEmptyField(etLogRegPassword1fvbi)) {
                    InputValidUtil.errorET(etLogRegPassword1fvbi, "Data kosong");
                } else if (InputValidUtil.isLenghtChar(etLogRegPassword1fvbi, 6)) {
                    InputValidUtil.errorET(etLogRegPassword1fvbi, "Password need 6 character or more");
                } else if (InputValidUtil.isEmptyField(etLogRegPassword2fvbi)) {
                    InputValidUtil.errorET(etLogRegPassword2fvbi, "Data kosong");
                } else if (!InputValidUtil.isMatch(etLogRegPassword1fvbi.getText().toString().trim(), etLogRegPassword2fvbi.getText().toString().trim())) {
                    InputValidUtil.errorET(etLogRegPassword2fvbi, "Password not match");
                    etLogRegPassword2fvbi.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), ISeasonConfig.SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvLogRegNowLoginfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
