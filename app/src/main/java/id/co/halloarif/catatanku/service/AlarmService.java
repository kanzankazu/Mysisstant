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
import java.util.List;
import java.util.Locale;
import java.util.Set;

import id.co.halloarif.catatanku.ISeasonConfig;
import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.database.SQLiteHelper;
import id.co.halloarif.catatanku.model.AlarmModel;
import id.co.halloarif.catatanku.support.util.DateTimeAlarmUtil;
import id.co.halloarif.catatanku.support.util.DateTimeUtil;
import id.co.halloarif.catatanku.support.util.ListArrayUtil;
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
    private int volumeAlarmDefault;
    private int volumeNotifDefault;

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

            Intent intent3 = new Intent(AlarmService.this, AlarmSummaryActivity.class);
            intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, ISeasonConfig.ID_NOTIF, intent3, PendingIntent.FLAG_ONE_SHOT);

            int ringerMode = SoundUtil.getRingerMode(this);
            volumeMusicDefault = SoundUtil.getVolume(this, AudioManager.STREAM_MUSIC);
            volumeRingDefault = SoundUtil.getVolume(this, AudioManager.STREAM_RING);
            volumeNotifDefault = SoundUtil.getVolume(this, AudioManager.STREAM_NOTIFICATION);
            volumeAlarmDefault = SoundUtil.getVolume(this, AudioManager.STREAM_ALARM);
            Log.d("Lihat", "onStartCommand AlarmService : " + volumeMusicDefault + " - " + ringerMode);
            Log.d("Lihat", "onStartCommand AlarmService : " + volumeRingDefault + " - " + ringerMode);
            Log.d("Lihat", "onStartCommand AlarmService : " + volumeNotifDefault + " - " + ringerMode);
            Log.d("Lihat", "onStartCommand AlarmService : " + volumeAlarmDefault + " - " + ringerMode);

            String substring;
            if (subid.substring(0, 1).equalsIgnoreCase("0")) {
                substring = "1" + subid.substring(1, 3);
            } else {
                substring = subid;
            }

            AlarmModel model = db.alarmGetOne(id);
            String alarm_day = model.getAlarm_day();
            if (!TextUtils.isEmpty(alarm_day)) {
                if (alarm_day.contains(",")) {
                    List<String> alarmDays = ListArrayUtil.convertStringToListStringComma(alarm_day);

                    db.alarmSetActive(id, 1);
                    DateTimeAlarmUtil.setAlarmSwitch(this, model, 1);

                    if (ListArrayUtil.isListContainString(alarmDays, String.valueOf(DateTimeUtil.getDayNow()))) {
                        setMaxVolume();
                        makeNotif(pendingIntent, substring);
                        makeSoundinfo();
                    }
                } else {
                    db.alarmSetActive(id, 0);
                    DateTimeAlarmUtil.setAlarmSwitch(this, model, 0);

                    setMaxVolume();
                    makeNotif(pendingIntent, substring);
                    makeSoundinfo();
                }
            } else {
                db.alarmSetActive(id, 0);
                DateTimeAlarmUtil.setAlarmSwitch(this, model, 0);

                setMaxVolume();
                makeNotif(pendingIntent, substring);
                makeSoundinfo();
            }

            //showDialog(msg);
        } catch (Exception e) {
            Log.e("Lihat", "onStartCommand AlarmService : " + e);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void setMaxVolume() {
        SoundUtil.setRingerMode(this, AudioManager.MODE_NORMAL);
        SoundUtil.setVolumeMax(this, AudioManager.STREAM_MUSIC);
        SoundUtil.setVolumeMax(this, AudioManager.STREAM_RING);
    }

    private void setMinVolume() {
        SoundUtil.setVolumeTo(this, AudioManager.STREAM_MUSIC, volumeMusicDefault);
        SoundUtil.setVolumeTo(this, AudioManager.STREAM_RING, volumeRingDefault);
    }

    private void makeNotif(PendingIntent pendingIntent, String substring) {
        NotifUtil.setNotification(AlarmService.this, title, msg, R.mipmap.ic_launcher, R.mipmap.ic_launcher, false, pendingIntent, Integer.parseInt(substring), ringtone);
    }

    private void makeSoundinfo() {
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

        setMinVolume();
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
