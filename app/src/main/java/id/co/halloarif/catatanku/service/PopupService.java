package id.co.halloarif.catatanku.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import id.co.halloarif.catatanku.R;

public class PopupService extends Service {

    private static final String TAG = PopupService.class.getSimpleName();
    WindowManager mWindowManager;
    View mView;
    private TextToSpeech mTts;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        showDialog(intent.getStringExtra("msg"));
        return super.onStartCommand(intent, flags, startId);
    }

    private void showDialog(String aTitle) {
        mTts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTts.setLanguage(Locale.UK);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.d("SpeakService", "Language is not available.");
                    } else {
                        if (!TextUtils.isEmpty(aTitle)) {
                            mTts.speak(aTitle, TextToSpeech.QUEUE_FLUSH, null);
                        } else {
                            mTts.speak("Error", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                } else {
                    Log.d("SpeakService", "Could not initialize TextToSpeech.");
                }
            }
        });
        mTts.setLanguage(new Locale("id", "ID"));
        //mTts.setLanguage(new Locale("in_ID"));
        //mTts.setLanguage(new Locale("ind-IDN"));
        mTts.stop();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Set<String> a = new HashSet<>();
            a.add("male");//here you can give male if you want to select male voice.
            Voice v = new Voice("en-us-x-sfg#male_2-local", new Locale("en", "US"), 400, 200, true, a);
            mTts.setVoice(v);
        }
        mTts.setSpeechRate(0.8f);
        mTts.speak(aTitle, TextToSpeech.QUEUE_FLUSH, null);

        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock mWakeLock = pm.newWakeLock((PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "YourServie");
        mWakeLock.acquire();
        mWakeLock.release();

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mView = View.inflate(getApplicationContext(), R.layout.dialog_popup_notification_received, null);
        mView.setTag(TAG);

        int top = getApplicationContext().getResources().getDisplayMetrics().heightPixels / 2;

        LinearLayout dialog = (LinearLayout) mView.findViewById(R.id.pop_exit);
        TextView message = (TextView) mView.findViewById(R.id.TextViewMessageInPopupMessageReceived);
        final EditText etMassage = (EditText) mView.findViewById(R.id.editTextInPopupMessageReceived);
        ImageButton imageButtonSend = (ImageButton) mView.findViewById(R.id.imageButtonSendInPopupMessageReceived);
        TextView close = (TextView) mView.findViewById(R.id.TextViewCloseInPopupMessageReceived);
        TextView view = (TextView) mView.findViewById(R.id.textviewViewInPopupMessageReceived);

        // if you want to set params
        //        android.widget.LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) dialog.getLayoutParams();
        //        lp.topMargin = top;
        //        lp.bottomMargin = top;
        //        mView.setLayoutParams(lp);

        //lp = (LayoutParams) imageButton.getLayoutParams();
        //lp.topMargin = top - 58;
        //imageButton.setLayoutParams(lp);

        message.setText(aTitle);

        imageButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Lihat", "onClick PopupService : " + "clicked");
                //mView.setVisibility(View.INVISIBLE);
                if (!etMassage.getText().toString().equals("")) {
                    Log.d("Lihat", "onClick PopupService : " + "sent");
                    etMassage.setText("");
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                hideDialog();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                hideDialog();
            }
        });

        final WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 0,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                PixelFormat.RGBA_8888);

        mView.setVisibility(View.VISIBLE);
        mWindowManager.addView(mView, mLayoutParams);
        mWindowManager.updateViewLayout(mView, mLayoutParams);

    }

    private void hideDialog() {
        if (mView != null && mWindowManager != null) {
            mWindowManager.removeView(mView);
            mView = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
