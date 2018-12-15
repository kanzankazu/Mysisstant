package id.co.halloarif.catatanku.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import id.co.halloarif.catatanku.R;

public class Login extends AppCompatActivity {

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
        etLogRegName
                etLogRegPassword2
        ibLogRegPassword2
                tvLogRegNowForgotPass
        bLogRegExecute
                tvLogRegNowRegister
        llLogRegGmail
                llLogRegFacebook
        llLogRegTwitter
    }

    private void initParam() {

    }

    private void initSession() {

    }

    private void initContent() {

    }

    private void initListener() {

    }
}
