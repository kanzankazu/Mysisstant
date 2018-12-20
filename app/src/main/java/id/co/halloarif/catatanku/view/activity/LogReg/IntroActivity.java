package id.co.halloarif.catatanku.view.activity.LogReg;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.support.util.SystemUtil;

public class IntroActivity extends AppCompatActivity {

    private TextView tvIntroHaiUsernamefvbi;
    private TextView tvIntro1fvbi;
    private TextView tvIntro2fvbi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        initComponent();
        initParam();
        initSession();
        initContent();
        initListener();

    }

    private void initComponent() {
        tvIntroHaiUsernamefvbi = (TextView) findViewById(R.id.tvIntroHaiUsername);
        tvIntro1fvbi = (TextView) findViewById(R.id.tvIntro1);
        tvIntro2fvbi = (TextView) findViewById(R.id.tvIntro2);
    }

    private void initParam() {

    }

    private void initSession() {

    }

    private void initContent() {
        loopViewGone(tvIntroHaiUsernamefvbi, tvIntro1fvbi, tvIntro2fvbi);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //code here
                loopViewAnimVisible(tvIntroHaiUsernamefvbi, tvIntro1fvbi, tvIntro2fvbi);
            }
        }, 500);
    }

    private void initListener() {

    }

    private void loopViewAnimVisible(TextView... views) {
        for (int i = 0; i < views.length; i++) {
            View view = views[i];
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    SystemUtil.visibile(IntroActivity.this, view, View.VISIBLE, R.anim.masuk_dari_kanan_ke_kiri);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //code here
                            LoginActivity.moveTo(IntroActivity.this, MainActivity.class, true);
                            overridePendingTransition(R.anim.masuk_dari_kanan_ke_kiri, R.anim.keluar_ke_kiri);
                        }
                    }, 3000);
                }
            };
            new Handler().postDelayed(runnable, 200);
        }
    }

    private void loopViewGone(View... views) {
        for (int i = 0; i < views.length; i++) {
            View view = views[i];
            view.setVisibility(View.GONE);
        }
    }
}
