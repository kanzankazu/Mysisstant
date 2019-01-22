package id.co.halloarif.catatanku.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import id.co.halloarif.catatanku.ISeasonConfig;
import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.database.SQLiteHelper;
import id.co.halloarif.catatanku.model.AlarmModel;
import id.co.halloarif.catatanku.support.util.DateTimeAlarmUtil;
import id.co.halloarif.catatanku.support.util.NotifUtil;
import id.co.halloarif.catatanku.support.util.SoundUtil;
import id.co.halloarif.catatanku.view.activity.Main.AlarmSummaryActivity;

public class AlarmService extends Service {
    private static final String TAG = AlarmService.class.getSimpleName();
    SQLiteHelper db = new SQLiteHelper(this);
    private TextToSpeech mTts;

    private String id;
    private String msg;
    private String title;
    private String record;
    private String ringtone;

    private MediaPlayer mPlayer;
    private boolean isPlay = false;
    private String subid;

    private int volumeMusicDefault;
    private int volumeRingDefault;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {

            id = intent.getStringExtra("id");
            title = intent.getStringExtra("title");
            msg = intent.getStringExtra("msg");
            record = intent.getStringExtra("record");
            ringtone = intent.getStringExtra("ringtone");
            subid = intent.getStringExtra("subid");

            Log.d("Lihat", "onStartCommand AlarmService : " + id);
            Log.d("Lihat", "onStartCommand AlarmService : " + title);
            Log.d("Lihat", "onStartCommand AlarmService : " + msg);
            Log.d("Lihat", "onStartCommand AlarmService : " + record);
            Log.d("Lihat", "onStartCommand AlarmService : " + ringtone);
            Log.d("Lihat", "onStartCommand AlarmService : " + subid);

            String substring;
            if (subid.substring(0, 1).equalsIgnoreCase("0")) {
                substring = "1" + subid.substring(1, 3);
            } else {
                substring = subid;
            }
            Log.d("Lihat", "onStartCommand AlarmService : " + substring);

            volumeMusicDefault = SoundUtil.getVolume(this, AudioManager.STREAM_MUSIC);
            Log.d("Lihat", "onStartCommand AlarmService : " + volumeMusicDefault);
            volumeRingDefault = SoundUtil.getVolume(this, AudioManager.STREAM_RING);
            Log.d("Lihat", "onStartCommand AlarmService : " + volumeRingDefault);

            SoundUtil.setVolumeTo(this, AudioManager.STREAM_MUSIC, 10);
            SoundUtil.setVolumeTo(this, AudioManager.STREAM_RING, 10);

            AlarmModel model = db.alarmGetOne(id);
            db.alarmSetActive(id, !TextUtils.isEmpty(model.getAlarm_day()) ? 1 : 0);
            DateTimeAlarmUtil.setAlarmSwitch(this, model, !TextUtils.isEmpty(model.getAlarm_day()) ? 1 : 0);

            Intent intent3 = new Intent(AlarmService.this, AlarmSummaryActivity.class);
            intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, ISeasonConfig.ID_NOTIF, intent3, PendingIntent.FLAG_ONE_SHOT);

            NotifUtil.setNotification(AlarmService.this, title, msg, R.mipmap.ic_launcher, R.mipmap.ic_launcher, false, pendingIntent, Integer.parseInt(substring), ringtone);
            //NotifUtil.setNotification(AlarmService.this, title, msg, R.mipmap.ic_launcher, R.mipmap.ic_launcher, false, null, Integer.parseInt(substring), ringtone);

            if (!TextUtils.isEmpty(record)) {
                File file = new File(record);
                if (file.exists()) {
                    if (!isPlay) {
                        playMediaPlayer();
                    } else {
                        stopMediaPlayer();
                    }
                } else {
                    loadTTS(msg);
                }
            } else {
                loadTTS(msg);
            }

            //showDialog(msg);
        } catch (Exception e) {
            Log.e("Lihat", "onStartCommand AlarmService : " + e);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    /*private void showDialog(String aTitle) {

        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "YourServie");
        wakeLock.acquire();
        wakeLock.release();

        View mView = View.inflate(getApplicationContext(), R.layout.dialog_popup_notification_received, null);
        mView.setTag(TAG);

        WindowManager mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        LinearLayout dialog = (LinearLayout) mView.findViewById(R.id.pop_exit);
        TextView message = (TextView) mView.findViewById(R.id.TextViewMessageInPopupMessageReceived);
        EditText etMassage = (EditText) mView.findViewById(R.id.editTextInPopupMessageReceived);
        ImageButton imageButtonSend = (ImageButton) mView.findViewById(R.id.imageButtonSendInPopupMessageReceived);
        TextView close = (TextView) mView.findViewById(R.id.TextViewCloseInPopupMessageReceived);
        TextView view = (TextView) mView.findViewById(R.id.textviewViewInPopupMessageReceived);

        message.setText(aTitle);

        imageButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Lihat", "onClick AlarmService : " + "clicked");
                //mView.setVisibility(View.INVISIBLE);
                if (!etMassage.getText().toString().equals("")) {
                    Log.d("Lihat", "onClick AlarmService : " + "sent");
                    etMassage.setText("");
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                hideDialog(mView, mWindowManager);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                hideDialog(mView, mWindowManager);
            }
        });

        final WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 0,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                PixelFormat.RGBA_8888);

        mView.setVisibility(View.VISIBLE);
        mWindowManager.addView(mView, mLayoutParams);
        mWindowManager.updateViewLayout(mView, mLayoutParams);

    }

    private void hideDialog(View mView, WindowManager mWindowManager) {
        if (mView != null && mWindowManager != null) {
            mWindowManager.removeView(mView);
            mView = null;
        }
    }*/

    private void loadTTS(String msg) {
        mTts = new TextToSpeech(AlarmService.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTts.setLanguage(new Locale("id", "ID"));
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.d("SpeakService", "Language is not available.");
                    } else {
                        if (!TextUtils.isEmpty(msg)) {
                            mTts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
                        } else {
                            mTts.speak("Error", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                } else {
                    Log.d("SpeakService", "Could not initialize TextToSpeech.");
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Set<String> a = new HashSet<>();
            a.add("female");//here you can give male if you want to select male voice.
            Voice v = new Voice("in-id-x-cfn#female_2-local", new Locale("id", "ID"), 400, 200, true, a);
            mTts.setVoice(v);
        }
        mTts.setSpeechRate(0.7f);
        mTts.setLanguage(new Locale("id", "ID"));
        mTts.stop();

        SoundUtil.setVolumeTo(this, AudioManager.STREAM_MUSIC, volumeMusicDefault);
        SoundUtil.setVolumeTo(this, AudioManager.STREAM_RING, volumeRingDefault);
    }

    private void playMediaPlayer() {
        // Even you can refer resource in res/raw directory
        //Uri myUri = Uri.parse("android.resource://com.prgguru.example/" + R.raw.hosannatamil);
        //Uri myUri1 = Uri.parse("file:///sdcard/Songs/ARR Hits/hosannatamil.mp3");

        isPlay = true;
        Uri myUri1 = Uri.fromFile(new File(record));
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mPlayer.setDataSource(getApplicationContext(), myUri1);
        } catch (IllegalArgumentException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mPlayer.prepare();
        } catch (IllegalStateException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        }
        mPlayer.start();

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                loadTTS(msg);

                stopMediaPlayer();
            }
        });
    }

    private void stopMediaPlayer() {
        isPlay = false;
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
        }
    }

    private void changeVolume() {

    }

    @Override
    public void onDestroy() {

        if (mTts != null) {
            mTts.stop();
            mTts.shutdown();
        }

        super.onDestroy();
    }
}
