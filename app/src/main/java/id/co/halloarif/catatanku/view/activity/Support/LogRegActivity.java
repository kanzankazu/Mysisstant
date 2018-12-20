package id.co.halloarif.catatanku.view.activity.Support;

import android.accounts.AccountManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import id.co.halloarif.catatanku.ISeasonConfig;
import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.presenter.LogRegListener;
import id.co.halloarif.catatanku.support.util.InputValidUtil;
import id.co.halloarif.catatanku.support.util.SessionUtil;
import id.co.halloarif.catatanku.support.util.SystemUtil;
import id.co.halloarif.catatanku.view.activity.LogReg.MainActivity;

public class LogRegActivity extends AppCompatActivity {

    private static final int REQ_ACCOUNT_CODE = 123;
    private TextView tvLogRegTitlefvbi;
    private EditText etLogRegNamefvbi;
    private LinearLayout llLogRegEmailfvbi;
    private EditText etLogRegEmailfvbi;
    private ImageButton ibLogRegEmailfvbi;
    private EditText etLogRegPhoneNofvbi;
    private LinearLayout llLogRegPassword1fvbi;
    private EditText etLogRegPassword1fvbi;
    private ImageButton ibLogRegPassword1fvbi;
    private LinearLayout llLogRegPassword2fvbi;
    private EditText etLogRegPassword2fvbi;
    private ImageButton ibLogRegPassword2fvbi;
    private Button bLogRegExecutefvbi;
    private LinearLayout llLogRegChangeOptionfvbi;
    private Button bLogRegChangeOptionfvbi;
    private LinearLayout llLogRegChangeOption2fvbi;
    private TextView tvLogRegChangeOptionfvbi;

    private int typeUI;
    private LogRegListener mListener;
    private boolean passwordShown1;
    private boolean passwordShown2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_reg);

        initComponent();
        initParam();
        initSession();
        initContent();
        initListener();
    }

    private void initComponent() {
        tvLogRegTitlefvbi = (TextView) findViewById(R.id.tvLogRegTitle);
        etLogRegNamefvbi = (EditText) findViewById(R.id.etLogRegName);
        llLogRegEmailfvbi = (LinearLayout) findViewById(R.id.llLogRegEmail);
        etLogRegEmailfvbi = (EditText) findViewById(R.id.etLogRegEmail);
        ibLogRegEmailfvbi = (ImageButton) findViewById(R.id.ibLogRegEmail);
        etLogRegPhoneNofvbi = (EditText) findViewById(R.id.etLogRegPhoneNo);
        llLogRegPassword1fvbi = (LinearLayout) findViewById(R.id.llLogRegPassword1);
        etLogRegPassword1fvbi = (EditText) findViewById(R.id.etLogRegPassword1);
        ibLogRegPassword1fvbi = (ImageButton) findViewById(R.id.ibLogRegPassword1);
        llLogRegPassword2fvbi = (LinearLayout) findViewById(R.id.llLogRegPassword2);
        etLogRegPassword2fvbi = (EditText) findViewById(R.id.etLogRegPassword2);
        ibLogRegPassword2fvbi = (ImageButton) findViewById(R.id.ibLogRegPassword2);
        bLogRegExecutefvbi = (Button) findViewById(R.id.bLogRegExecute);
        llLogRegChangeOptionfvbi = (LinearLayout) findViewById(R.id.llLogRegChangeOption);
        bLogRegChangeOptionfvbi = (Button) findViewById(R.id.bLogRegChangeOption);
        llLogRegChangeOption2fvbi = (LinearLayout) findViewById(R.id.llLogRegChangeOption2);
        tvLogRegChangeOptionfvbi = (TextView) findViewById(R.id.tvLogRegChangeOption);

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
        typeUI = 1;
        updateUi();

        customText(tvLogRegChangeOptionfvbi);

    }

    private void initListener() {
        String sName = etLogRegNamefvbi.getText().toString().trim();
        String sEmail = etLogRegEmailfvbi.getText().toString().trim();
        String sPhone = etLogRegPhoneNofvbi.getText().toString().trim();
        String sPass1 = etLogRegPassword1fvbi.getText().toString().trim();
        String sPass2 = etLogRegPassword2fvbi.getText().toString().trim();

        mListener = new LogRegListener() {
            @Override
            public void onRegister() {
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

            @Override
            public void onLogin() {
                if (InputValidUtil.isEmptyField(etLogRegNamefvbi)) {
                    InputValidUtil.errorET(etLogRegNamefvbi, "Data kosong");
                } else if (InputValidUtil.isEmptyField(etLogRegPassword2fvbi)) {
                    InputValidUtil.errorET(etLogRegPassword2fvbi, "Data kosong");
                } else {
                    Log.d("Lihat", "onLogin LogRegActivity : " + InputValidUtil.isValidatePhoneNumber(sName));
                    Log.d("Lihat", "onLogin LogRegActivity : " + InputValidUtil.isValidateEmail(sEmail));
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

            @Override
            public void onActivation() {
                if (InputValidUtil.isEmptyField(etLogRegNamefvbi)) {
                    InputValidUtil.errorET(etLogRegNamefvbi, "Data kosong");
                } else if (!InputValidUtil.isValidatePhoneNumber(etLogRegNamefvbi.getText().toString().trim()) || !InputValidUtil.isValidateEmail(etLogRegNamefvbi.getText().toString().trim())) {
                    InputValidUtil.errorET(etLogRegNamefvbi, "Wrong Format");
                } else {
                    Toast.makeText(getApplicationContext(), ISeasonConfig.SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onForgetPass() {
                if (InputValidUtil.isEmptyField(etLogRegNamefvbi)) {
                    InputValidUtil.errorET(etLogRegNamefvbi, "Data kosong");
                } else if (!InputValidUtil.isValidatePhoneNumber(etLogRegNamefvbi.getText().toString().trim()) || !InputValidUtil.isValidateEmail(etLogRegNamefvbi.getText().toString().trim())) {
                    InputValidUtil.errorET(etLogRegNamefvbi, "Wrong Format");
                } else {
                    Toast.makeText(getApplicationContext(), ISeasonConfig.SUCCESS, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onGmailClick() {

            }

            @Override
            public void onFacebookClick() {

            }

            @Override
            public void onTweeterClick() {

            }
        };

        bLogRegExecutefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeUI == 1) {
                    mListener.onRegister();
                } else if (typeUI == 2) {
                    mListener.onLogin();
                } else if (typeUI == 3) {
                    mListener.onActivation();
                } else if (typeUI == 4) {
                    mListener.onForgetPass();
                }
            }
        });

        ibLogRegEmailfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SystemUtil.getEmailAccout(LogRegActivity.this, REQ_ACCOUNT_CODE);
            }
        });
        ibLogRegPassword1fvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passwordShown1) {//tidak terlihat
                    passwordShown1 = false;
                    etLogRegPassword1fvbi.setInputType(129);//edittest
                    etLogRegPassword1fvbi.setTypeface(Typeface.SANS_SERIF);
                    ibLogRegPassword1fvbi.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                } else {//terlihat
                    passwordShown1 = true;
                    etLogRegPassword1fvbi.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ibLogRegPassword1fvbi.setImageResource(R.drawable.ic_visibility_black_24dp);
                }
            }
        });
        ibLogRegPassword2fvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passwordShown2) {//tidak terlihat
                    passwordShown2 = false;
                    etLogRegPassword2fvbi.setInputType(129);
                    etLogRegPassword2fvbi.setTypeface(Typeface.SANS_SERIF);
                    ibLogRegPassword2fvbi.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                } else {//terlihat
                    passwordShown2 = true;
                    etLogRegPassword2fvbi.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ibLogRegPassword2fvbi.setImageResource(R.drawable.ic_visibility_black_24dp);
                }
            }
        });
    }

    private void moveToMain() {
        SessionUtil.setBoolPreferences(ISeasonConfig.KEY_IS_HAS_LOGIN, true);
        Intent intent = new Intent(LogRegActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void customText(TextView view) {
        SpannableStringBuilder spanTxt = new SpannableStringBuilder();

        spanTxt.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.androidSblue)), 0, spanTxt.length(), 0);
        spanTxt.append("RegisterActivity");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (typeUI != 1) {
                    typeUI = 1;
                    updateUi();
                }

            }
        }, spanTxt.length() - "RegisterActivity".length(), spanTxt.length(), 0);

        spanTxt.append(" | ");

        spanTxt.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.androidSblue)), 0, spanTxt.length(), 0);
        spanTxt.append("LoginActivity");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (typeUI != 2) {
                    typeUI = 2;
                    updateUi();
                }

            }
        }, spanTxt.length() - "LoginActivity".length(), spanTxt.length(), 0);

        spanTxt.append(" | ");

        spanTxt.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.androidSblue)), 0, spanTxt.length(), 0);
        spanTxt.append("Activation");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (typeUI != 3) {
                    typeUI = 3;
                    updateUi();
                }

            }
        }, spanTxt.length() - "Activation".length(), spanTxt.length(), 0);

        spanTxt.append(" | ");

        spanTxt.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.androidSblue)), 0, spanTxt.length(), 0);
        spanTxt.append("Forget Password");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (typeUI != 4) {
                    typeUI = 4;
                    updateUi();
                }

            }
        }, spanTxt.length() - "Forget Password".length(), spanTxt.length(), 0);

        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }

    private void updateUi() {
        clearEdittext(etLogRegEmailfvbi, etLogRegNamefvbi, etLogRegPassword1fvbi, etLogRegPassword2fvbi, etLogRegPhoneNofvbi);
        if (typeUI == 1) {
            tvLogRegTitlefvbi.setText("Please Input your data");
            etLogRegNamefvbi.setHint("Name");
            etLogRegPassword2fvbi.setHint("Rewrite password");
            bLogRegExecutefvbi.setText("RegisterActivity");
            bLogRegChangeOptionfvbi.setText("LoginActivity");
            visibleView(etLogRegNamefvbi, llLogRegEmailfvbi, etLogRegPhoneNofvbi, llLogRegPassword1fvbi, llLogRegPassword2fvbi, llLogRegChangeOptionfvbi, llLogRegChangeOption2fvbi);
            bLogRegChangeOptionfvbi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    typeUI = 2;
                    updateUi();
                }
            });
        } else if (typeUI == 2) {
            tvLogRegTitlefvbi.setText("Please LoginActivity Here");
            etLogRegNamefvbi.setHint("Use Phone No or Email");
            etLogRegPassword2fvbi.setHint("Password");
            bLogRegExecutefvbi.setText("LoginActivity");
            bLogRegChangeOptionfvbi.setText("RegisterActivity");
            visibleView(etLogRegNamefvbi, llLogRegPassword2fvbi);
            goneView(llLogRegEmailfvbi, etLogRegPhoneNofvbi, llLogRegPassword1fvbi);
            bLogRegChangeOptionfvbi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    typeUI = 1;
                    updateUi();
                }
            });
        } else if (typeUI == 3) {
            tvLogRegTitlefvbi.setText("For Activation, need phone number and email");
            etLogRegNamefvbi.setHint("Use Phone No or Email");
            bLogRegExecutefvbi.setText("Activation");
            visibleView(etLogRegNamefvbi);
            goneView(llLogRegEmailfvbi, etLogRegPhoneNofvbi, llLogRegPassword1fvbi, llLogRegPassword2fvbi, llLogRegChangeOptionfvbi);
        } else if (typeUI == 4) {
            tvLogRegTitlefvbi.setText("For Forget Password, need phone number and email");
            etLogRegNamefvbi.setHint("Use Phone No or Email");
            bLogRegExecutefvbi.setText("Forget Password");
            visibleView(etLogRegNamefvbi);
            goneView(llLogRegEmailfvbi, etLogRegPhoneNofvbi, llLogRegPassword1fvbi, llLogRegPassword2fvbi, llLogRegChangeOptionfvbi);
        }
    }

    private void goneView(View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    private void visibleView(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    private void clearEdittext(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setText("");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_ACCOUNT_CODE && resultCode == RESULT_OK) {
            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            etLogRegEmailfvbi.setText(accountName);
        }
    }
}
