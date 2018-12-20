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

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText etLogRegNamefvbi;
    private CardView bLogRegExecutefvbi;
    private TextView tvLogRegNowResendEmailfvbi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initComponent();
        initParam();
        initSession();
        initContent();
        initListener();

    }

    private void initComponent() {
        etLogRegNamefvbi = (EditText) findViewById(R.id.etLogRegName);
        bLogRegExecutefvbi = (CardView) findViewById(R.id.bLogRegExecute);
        tvLogRegNowResendEmailfvbi = (TextView) findViewById(R.id.tvLogRegNowResendEmail);
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
                }  else {
                    if (InputValidUtil.isValidatePhoneNumber(etLogRegNamefvbi.getText().toString().trim()) || InputValidUtil.isValidateEmail(etLogRegNamefvbi.getText().toString().trim())) {
                        Toast.makeText(getApplicationContext(), ISeasonConfig.SUCCESS, Toast.LENGTH_SHORT).show();
                    } else {
                        InputValidUtil.errorET(etLogRegNamefvbi, "Salah format");
                    }
                }
            }
        });
        tvLogRegNowResendEmailfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
