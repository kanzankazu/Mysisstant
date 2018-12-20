package id.co.halloarif.catatanku.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import id.co.halloarif.catatanku.ISeasonConfig;
import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.support.util.InputValidUtil;
import id.co.halloarif.catatanku.support.util.SessionUtil;

public class LoginActivity extends AppCompatActivity {

    private EditText etLogRegNamefvbi;
    private EditText etLogRegPassword2fvbi;
    private ImageView ibLogRegPassword2fvbi;
    private TextView tvLogRegNowForgotPassfvbi;
    private CardView bLogRegExecutefvbi;
    private TextView tvLogRegNowRegisterfvbi;
    private LinearLayout llLogRegGmailfvbi;
    private LinearLayout llLogRegFacebookfvbi;
    private LinearLayout llLogRegTwitterfvbi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponent();
        initParam();
        initSession();
        initContent();
        initListener();
    }

    private void initComponent() {
        etLogRegNamefvbi = (EditText) findViewById(R.id.etLogRegName);
        etLogRegPassword2fvbi = (EditText) findViewById(R.id.etLogRegPassword2);
        ibLogRegPassword2fvbi = (ImageView) findViewById(R.id.ibLogRegPassword2);
        tvLogRegNowForgotPassfvbi = (TextView) findViewById(R.id.tvLogRegNowForgotPass);
        bLogRegExecutefvbi = (CardView) findViewById(R.id.bLogRegExecute);
        tvLogRegNowRegisterfvbi = (TextView) findViewById(R.id.tvLogRegNowRegister);
        llLogRegGmailfvbi = (LinearLayout) findViewById(R.id.llLogRegGmail);
        llLogRegFacebookfvbi = (LinearLayout) findViewById(R.id.llLogRegFacebook);
        llLogRegTwitterfvbi = (LinearLayout) findViewById(R.id.llLogRegTwitter);

    }

    private void initParam() {

    }

    private void initSession() {
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_IS_HAS_LOGIN)) {
            if (SessionUtil.getBoolPreferences(ISeasonConfig.KEY_IS_HAS_LOGIN, false)) {
                moveToMain();
            }
        }
    }

    private void initContent() {

    }

    private void initListener() {
        bLogRegExecutefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InputValidUtil.isEmptyField(etLogRegNamefvbi)) {
                    InputValidUtil.errorET(etLogRegNamefvbi, "Data kosong");
                } else if (InputValidUtil.isEmptyField(etLogRegPassword2fvbi)) {
                    InputValidUtil.errorET(etLogRegPassword2fvbi, "Data kosong");
                } else {
                    if (InputValidUtil.isValidatePhoneNumber(etLogRegNamefvbi.getText().toString().trim()) || InputValidUtil.isValidateEmail(etLogRegNamefvbi.getText().toString().trim())) {
                        Toast.makeText(getApplicationContext(), ISeasonConfig.SUCCESS, Toast.LENGTH_SHORT).show();
                        moveToMain();
                    } else {
                        if (InputValidUtil.isLenghtChar(etLogRegPassword2fvbi, 6)) {
                            InputValidUtil.errorET(etLogRegPassword2fvbi, "Password need 6 character or more");
                        } else {
                            InputValidUtil.errorET(etLogRegNamefvbi, "Salah format");
                        }
                    }
                }
            }
        });
        tvLogRegNowForgotPassfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTo(LoginActivity.this, ForgetPasswordActivity.class, false);
            }
        });
        tvLogRegNowRegisterfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTo(LoginActivity.this, RegisterActivity.class, false);
            }
        });

        llLogRegGmailfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        llLogRegFacebookfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        llLogRegTwitterfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void moveToMain() {
        SessionUtil.setBoolPreferences(ISeasonConfig.KEY_IS_HAS_LOGIN, true);
        moveTo(LoginActivity.this, IntroActivity.class, true);
        overridePendingTransition(R.anim.masuk_dari_kanan_ke_kiri, R.anim.keluar_ke_kiri);
    }

    public static Intent moveTo(Activity activity, Class<?> targetClass, boolean isFinish) {
        Intent intent = new Intent(activity, targetClass);
        activity.startActivity(intent);

        if (isFinish) {
            activity.finish();
        }

        return intent;
    }
}