package id.co.halloarif.catatanku.view.activity.Main;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.singledateandtimepicker.widget.WheelHourPicker;
import com.github.florent37.singledateandtimepicker.widget.WheelMinutePicker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import id.co.halloarif.catatanku.ISeasonConfig;
import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.database.SQLiteHelper;
import id.co.halloarif.catatanku.model.AlarmModel;
import id.co.halloarif.catatanku.model.ContactPickerModel;
import id.co.halloarif.catatanku.support.util.DateTimeAlarmUtil;
import id.co.halloarif.catatanku.support.util.DateTimeUtil;
import id.co.halloarif.catatanku.support.util.InputValidUtil;
import id.co.halloarif.catatanku.support.util.ListArrayUtil;
import id.co.halloarif.catatanku.support.util.VideoAudioUtil;
import id.co.halloarif.catatanku.view.activity.Support.ListContactPickerCheckBox;

public class AlarmActivity extends AppCompatActivity {
    private static final int REQ_CODE_CONTACK_PICKER = 4;
    private static final int REQ_CODE_RINGTONE_PICKER = 5;

    SQLiteHelper db = new SQLiteHelper(AlarmActivity.this);
    MediaRecorder recorder = new MediaRecorder();

    StringBuilder alarmSubIdStringBuilder = new StringBuilder();
    private String alarmSubIdUpdate;
    private String alarmSubIdNew;

    private EditText etAlarmInputJudulfvbi;
    private CheckBox cbAlarmInputHariSeninfvbi;
    private CheckBox cbAlarmInputHariSelasafvbi;
    private CheckBox cbAlarmInputHariRabufvbi;
    private CheckBox cbAlarmInputHariKamisfvbi;
    private CheckBox cbAlarmInputHariJumatfvbi;
    private CheckBox cbAlarmInputHariSabtufvbi;
    private CheckBox cbAlarmInputHariMinggufvbi;
    private LinearLayout llAlarmInputAddfriendfvbi;
    private TextView tvAlarmInputAddFriendfvbi;
    private ImageView ivAlarmInputAddFriendfvbi;
    private LinearLayout llAlarmInputVoicefvbi;
    private TextView tvAlarmInputVoicefvbi;
    private ImageView ivAlarmInputVoicePlayStopfvbi;
    private ImageView ivAlarmInputVoicefvbi;
    private LinearLayout llAlarmInputRingtonefvbi;
    private TextView tvAlarmInputRingtonefvbi;
    private ImageView ivAlarmInputRingtonefvbi;
    private LinearLayout llAlarmInputOnceAlarmfvbi;
    private ImageView ivAlarmInputOnceAlarmfvbi;

    private WheelHourPicker whpAlarmInputJamfvbi;
    private WheelMinutePicker whpAlarmInputMenitfvbi;

    private String alarmId;
    private int isActive;

    private CheckBox[] chkBoxs;
    private Integer[] chkBoxIds = {
            R.id.cbAlarmInputHariSenin,
            R.id.cbAlarmInputHariSelasa,
            R.id.cbAlarmInputHariRabu,
            R.id.cbAlarmInputHariKamis,
            R.id.cbAlarmInputHariJumat,
            R.id.cbAlarmInputHariSabtu,
            R.id.cbAlarmInputHariMinggu};

    private int currentHour;
    private int currentMinute;
    private String currentHourString;
    private String currentMinuteString;
    private String AmPm;
    private String hh;
    private String mm;
    private String HH;

    private String alarmContactName;
    private String alarmContactNo;

    private boolean isRecord = false;
    private boolean isRecorded = false;
    private boolean isRecordPlay = false;
    private boolean doubleClickRecord = false;
    private MediaPlayer mPlayer;
    private String recordPath;

    private String ringtonePath;
    private String ringtoneTitle;
    private Uri ringtoneUri;

    private boolean isUpdate = false;
    private boolean isSaveUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_input);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initComponent();
        initParam();
        initSession();
        initContent();
        initListener();
    }

    private void initComponent() {
        etAlarmInputJudulfvbi = (EditText) findViewById(R.id.etAlarmInputJudul);
        whpAlarmInputJamfvbi = (WheelHourPicker) findViewById(R.id.whpAlarmInputJam);
        whpAlarmInputMenitfvbi = (WheelMinutePicker) findViewById(R.id.whpAlarmInputMenit);
        llAlarmInputAddfriendfvbi = (LinearLayout) findViewById(R.id.llAlarmInputAddfriend);
        tvAlarmInputAddFriendfvbi = (TextView) findViewById(R.id.tvAlarmInputAddFriend);
        ivAlarmInputAddFriendfvbi = (ImageView) findViewById(R.id.ivAlarmInputAddFriend);
        llAlarmInputVoicefvbi = (LinearLayout) findViewById(R.id.llAlarmInputVoice);
        tvAlarmInputVoicefvbi = (TextView) findViewById(R.id.tvAlarmInputVoice);
        ivAlarmInputVoicefvbi = (ImageView) findViewById(R.id.ivAlarmInputVoice);
        ivAlarmInputVoicePlayStopfvbi = (ImageView) findViewById(R.id.ivAlarmInputVoicePlayStop);
        llAlarmInputRingtonefvbi = (LinearLayout) findViewById(R.id.llAlarmInputRingtone);
        tvAlarmInputRingtonefvbi = (TextView) findViewById(R.id.tvAlarmInputRingtone);
        ivAlarmInputRingtonefvbi = (ImageView) findViewById(R.id.ivAlarmInputRingtone);
        llAlarmInputOnceAlarmfvbi = (LinearLayout) findViewById(R.id.llAlarmInputOnceAlarm);
        ivAlarmInputOnceAlarmfvbi = (ImageView) findViewById(R.id.ivAlarmInputOnceAlarm);
        cbAlarmInputHariSeninfvbi = (CheckBox) findViewById(R.id.cbAlarmInputHariSenin);
        cbAlarmInputHariSelasafvbi = (CheckBox) findViewById(R.id.cbAlarmInputHariSelasa);
        cbAlarmInputHariRabufvbi = (CheckBox) findViewById(R.id.cbAlarmInputHariRabu);
        cbAlarmInputHariKamisfvbi = (CheckBox) findViewById(R.id.cbAlarmInputHariKamis);
        cbAlarmInputHariJumatfvbi = (CheckBox) findViewById(R.id.cbAlarmInputHariJumat);
        cbAlarmInputHariSabtufvbi = (CheckBox) findViewById(R.id.cbAlarmInputHariSabtu);
        cbAlarmInputHariMinggufvbi = (CheckBox) findViewById(R.id.cbAlarmInputHariMinggu);

        chkBoxs = new CheckBox[chkBoxIds.length];
        for (int i = 0; i < chkBoxIds.length; i++) {
            chkBoxs[i] = (CheckBox) findViewById(chkBoxIds[i]);
            chkBoxs[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        ivAlarmInputOnceAlarmfvbi.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void initParam() {
        Intent bundle = getIntent();
        if (bundle.hasExtra(ISeasonConfig.INTENT_ID)) {
            alarmId = bundle.getStringExtra(ISeasonConfig.INTENT_ID);
            isActive = bundle.getIntExtra(ISeasonConfig.INTENT_ACTIVE, 0);
            loadData(alarmId);
            isUpdate = true;
            Snackbar.make(findViewById(android.R.id.content), "Atur Ulang Kembali Jam & Menit Alarm", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        } else {
            isUpdate = false;
            alarmId = UUID.randomUUID().toString();
        }
    }

    private void loadData(String alarmId) {
        AlarmModel alarmModel = db.alarmGetOne(alarmId);
        etAlarmInputJudulfvbi.setText(alarmModel.getAlarm_title());

        alarmSubIdUpdate = alarmModel.getAlarm_sub_id();

        currentHourString = alarmModel.getAlarm_hour();
        whpAlarmInputJamfvbi.setDefault(currentHourString);
        currentMinuteString = alarmModel.getAlarm_minute();

        alarmContactName = alarmModel.getAlarm_friend();
        alarmContactNo = alarmModel.getAlarm_friend_no();
        if (!TextUtils.isEmpty(alarmContactName) && !TextUtils.isEmpty(alarmContactNo)) {
            ivAlarmInputAddFriendfvbi.setVisibility(View.VISIBLE);
        }

        recordPath = alarmModel.getAlarm_voice_uri();
        if (!TextUtils.isEmpty(recordPath)) {
            File file = new File(recordPath);
            if (file.exists()) {
                ivAlarmInputVoicefvbi.setVisibility(View.VISIBLE);
                ivAlarmInputVoicePlayStopfvbi.setVisibility(View.VISIBLE);
                recordPath = null;
            }
        }

        ringtoneTitle = alarmModel.getAlarm_ringtone();
        ringtonePath = alarmModel.getAlarm_ringtone_uri();
        if (!TextUtils.isEmpty(ringtoneTitle) && !TextUtils.isEmpty(ringtonePath)) {
            ivAlarmInputRingtonefvbi.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(alarmModel.getAlarm_day())) {
            ivAlarmInputOnceAlarmfvbi.setVisibility(View.VISIBLE);
        } else {
            ivAlarmInputOnceAlarmfvbi.setVisibility(View.GONE);
            setCBCheck(alarmModel.getAlarm_day());
        }
    }

    private void initSession() {

    }

    private void initContent() {
        initTimePicker();
    }

    private void initTimePicker() {
        AmPm = DateTimeUtil.dateToString(DateTimeUtil.getCurrentDate(), new SimpleDateFormat("a"));
        hh = DateTimeUtil.dateToString(DateTimeUtil.getCurrentDate(), new SimpleDateFormat("h"));
        HH = DateTimeUtil.dateToString(DateTimeUtil.getCurrentDate(), new SimpleDateFormat("HH"));
        mm = DateTimeUtil.dateToString(DateTimeUtil.getCurrentDate(), new SimpleDateFormat("mm"));

        whpAlarmInputJamfvbi.setIsAmPm(false);
        whpAlarmInputJamfvbi.setDefault(HH);
        whpAlarmInputJamfvbi.setHourChangedListener(new WheelHourPicker.OnHourChangedListener() {
            @Override
            public void onHourChanged(WheelHourPicker picker, int hour) {
                currentHour = hour;

                String s = String.valueOf(hour);
                if (s.length() > 1) {
                    currentHourString = s;
                } else {
                    currentHourString = "0" + s;
                }
                Log.d("Lihat", "onHourChanged AlarmActivity : " + currentHourString);
            }
        });

        whpAlarmInputMenitfvbi.setStepMinutes(1);
        whpAlarmInputMenitfvbi.setOnMinuteChangedListener(new WheelMinutePicker.OnMinuteChangedListener() {
            @Override
            public void onMinuteChanged(WheelMinutePicker picker, int minutes) {
                currentMinute = minutes;

                String s = String.valueOf(minutes);
                if (s.length() > 1) {
                    currentMinuteString = s;
                } else {
                    currentMinuteString = "0" + s;
                }
                Log.d("Lihat", "onMinuteChanged AlarmActivity : " + currentMinuteString);
            }
        });

        currentHour = Integer.parseInt(HH);
        String s = HH;
        if (s.length() > 1) {
            currentHourString = s;
        } else {
            currentHourString = "0" + s;
        }

        currentMinute = Integer.parseInt(mm);
        String s1 = mm;
        if (s1.length() > 1) {
            currentMinuteString = s1;
        } else {
            currentMinuteString = "0" + s1;
        }
    }

    private void initListener() {

        llAlarmInputAddfriendfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                //startActivityForResult(intent, REQ_CODE_CONTACK_PICKER);

                Intent intentContactPick = new Intent(AlarmActivity.this, ListContactPickerCheckBox.class);
                startActivityForResult(intentContactPick, REQ_CODE_CONTACK_PICKER);
            }
        });
        llAlarmInputVoicefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (doubleClickRecord) {
                    if (!isRecord) {
                        if (!TextUtils.isEmpty(recordPath)) {
                            File file = new File(recordPath);
                            if (file.exists()) {
                                file.delete();
                                recordPath = "";

                                ivAlarmInputVoicePlayStopfvbi.setVisibility(View.GONE);
                                ivAlarmInputVoicefvbi.setVisibility(View.GONE);

                                //llAlarmInputVoicefvbi.setBackgroundColor(getResources().getColor(R.color.white));
                                isRecord = false;
                                Toast.makeText(getApplicationContext(), "Delete record", Toast.LENGTH_SHORT).show();

                                //tvAlarmInputVoicefvbi.setText("Voice");
                                //tvAlarmInputVoicefvbi.setTextColor(getResources().getColor(R.color.gray));
                            }
                        }
                    }
                }
                doubleClickRecord = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleClickRecord = false;
                    }
                }, 2000);
            }
        });
        llAlarmInputVoicefvbi.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (!isRecord) {
                    if (!TextUtils.isEmpty(recordPath)) {
                        File file = new File(recordPath);
                        if (file.exists()) {
                            file.delete();
                        }
                    }

                    ivAlarmInputVoicePlayStopfvbi.setVisibility(View.GONE);
                    ivAlarmInputVoicefvbi.setVisibility(View.GONE);

                    VideoAudioUtil.MediaRecorderReady(recorder);
                    VideoAudioUtil.startRecording(recorder);

                    //llAlarmInputVoicefvbi.setBackgroundColor(getResources().getColor(R.color.super_red));
                    isRecord = true;
                    isRecorded = true;
                    Toast.makeText(getApplicationContext(), "Start Record", Toast.LENGTH_SHORT).show();

                    //tvAlarmInputVoicefvbi.setText("recording");
                    //tvAlarmInputVoicefvbi.setTextColor(getResources().getColor(R.color.white));
                } else {
                    recordPath = VideoAudioUtil.stopRecording(recorder);

                    ivAlarmInputVoicePlayStopfvbi.setVisibility(View.VISIBLE);
                    ivAlarmInputVoicefvbi.setVisibility(View.VISIBLE);

                    //llAlarmInputVoicefvbi.setBackgroundColor(getResources().getColor(R.color.white));
                    isRecord = false;
                    Toast.makeText(getApplicationContext(), "Stop Record", Toast.LENGTH_SHORT).show();

                    //tvAlarmInputVoicefvbi.setText("recorded");
                    //tvAlarmInputVoicefvbi.setTextColor(getResources().getColor(R.color.androidSblue));
                }
                return true;
            }
        });
        llAlarmInputRingtonefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
                //intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
                //intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
                startActivityForResult(intent, REQ_CODE_RINGTONE_PICKER);
            }
        });
        llAlarmInputOnceAlarmfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = ivAlarmInputOnceAlarmfvbi.getVisibility();
                if (visibility == View.VISIBLE) {
                    ivAlarmInputOnceAlarmfvbi.setVisibility(View.GONE);
                } else if (visibility == View.GONE) {
                    setCBCheckFalse(
                            cbAlarmInputHariSeninfvbi,
                            cbAlarmInputHariSelasafvbi,
                            cbAlarmInputHariRabufvbi,
                            cbAlarmInputHariKamisfvbi,
                            cbAlarmInputHariJumatfvbi,
                            cbAlarmInputHariSabtufvbi,
                            cbAlarmInputHariMinggufvbi);
                    ivAlarmInputOnceAlarmfvbi.setVisibility(View.VISIBLE);
                } else {
                    setCBCheckFalse(
                            cbAlarmInputHariSeninfvbi,
                            cbAlarmInputHariSelasafvbi,
                            cbAlarmInputHariRabufvbi,
                            cbAlarmInputHariKamisfvbi,
                            cbAlarmInputHariJumatfvbi,
                            cbAlarmInputHariSabtufvbi,
                            cbAlarmInputHariMinggufvbi);
                    ivAlarmInputOnceAlarmfvbi.setVisibility(View.VISIBLE);
                }
            }
        });

        ivAlarmInputVoicePlayStopfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRecordPlay) {
                    playMediaPlayer();
                } else {
                    stopMediaPlayer();
                }
            }
        });

    }

    private void setCBCheck(String alarm_day) {
        String[] days = alarm_day.split(",");
        setCBCheckTrue(days, cbAlarmInputHariSeninfvbi,
                cbAlarmInputHariSelasafvbi,
                cbAlarmInputHariRabufvbi,
                cbAlarmInputHariKamisfvbi,
                cbAlarmInputHariJumatfvbi,
                cbAlarmInputHariSabtufvbi,
                cbAlarmInputHariMinggufvbi);
    }

    private void setCBCheckTrue(String[] days, CheckBox... checkBoxes) {
        for (String day : days) {
            for (CheckBox checkBox : checkBoxes) {
                if (checkBox.getTag().toString().equalsIgnoreCase(day)) {
                    checkBox.setChecked(true);
                }
            }
        }
    }

    private void setCBCheckFalse(CheckBox... checkBoxes) {
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setChecked(false);
        }
    }

    private List<Integer> getIntFromCB(CheckBox... checkBoxes) {
        List<Integer> ints = new ArrayList<>();
        for (int i = 0; i < checkBoxes.length; i++) {
            CheckBox checkBox = checkBoxes[i];
            if (checkBox.isChecked()) {
                String trim = checkBox.getTag().toString().trim();
                ints.add(Integer.valueOf(trim));
            }
        }
        return ints;
    }

    private String getTagFromCB(CheckBox... checkBoxes) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < checkBoxes.length; i++) {
            CheckBox cb = checkBoxes[i];
            String tag = (String) cb.getTag();
            if (cb.isChecked()) {
                strings.add(tag);
            }
        }

        return ListArrayUtil.convertListStringToString(strings);
    }

    private String getTextFromCB(CheckBox... checkBoxes) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < checkBoxes.length; i++) {
            CheckBox cb = checkBoxes[i];
            String text = (String) cb.getText();
            if (cb.isChecked()) {
                strings.add(text);
            }
        }

        return ListArrayUtil.convertListStringToString(strings);
    }

    private List<String> getListTagFromCB(CheckBox... checkBoxes) {
        StringBuilder builder = new StringBuilder();
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < checkBoxes.length; i++) {
            CheckBox checkBox = checkBoxes[i];
            if (checkBox.isChecked()) {
                String trim = checkBox.getTag().toString().trim();
                strings.add(trim);
            }
        }

        return strings;
    }

    private void playMediaPlayer() {
        // Even you can refer resource in res/raw directory
        //Uri myUri = Uri.parse("android.resource://com.prgguru.example/" + R.raw.hosannatamil);
        //Uri myUri1 = Uri.parse("file:///sdcard/Songs/ARR Hits/hosannatamil.mp3");

        isRecordPlay = true;
        ivAlarmInputVoicePlayStopfvbi.setImageResource(R.drawable.ic_stop_mp);
        Uri myUri1 = Uri.fromFile(new File(recordPath));
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
                stopMediaPlayer();
            }
        });
    }

    private void stopMediaPlayer() {
        isRecordPlay = false;
        ivAlarmInputVoicePlayStopfvbi.setImageResource(R.drawable.ic_play_mp);
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
        }
    }

    private void stopDeleteRecord() {
        if (!isSaveUpdate) {
            if (isRecorded) {
                if (isRecord) {
                    String s = VideoAudioUtil.stopRecording(recorder);
                    if (!TextUtils.isEmpty(s)) {
                        File file = new File(s);
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                } else {
                    if (!TextUtils.isEmpty(recordPath)) {
                        File file = new File(recordPath);
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                }
            }
        }

    }

    private void setSaveUpdateAlarm() {

        String alarmTitle = etAlarmInputJudulfvbi.getText().toString().trim();

        String s;
        if (TextUtils.isEmpty(alarmTitle)) {
            s = "";
        } else {
            s = "dan sudah waktunya " + etAlarmInputJudulfvbi.getText().toString().trim() + " . ";
        }

        /*Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("id", alarmId);
        intent.putExtra("title", "Notifikasi " + alarmTitle);
        intent.putExtra("msg", "Hay Kamu.. sekarang sudah jam " + currentHourString + ":" + currentMinuteString + " . " + s);
        intent.putExtra("record", recordPath);
        intent.putExtra("ringtone", ringtonePath);*/

        /*Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        long yesterday = calendar.getTimeInMillis();*/

        List<String> tagsDay = getListTagFromCB(
                cbAlarmInputHariSeninfvbi,
                cbAlarmInputHariSelasafvbi,
                cbAlarmInputHariRabufvbi,
                cbAlarmInputHariKamisfvbi,
                cbAlarmInputHariJumatfvbi,
                cbAlarmInputHariSabtufvbi,
                cbAlarmInputHariMinggufvbi
        );

        String alarmTagDayFromCB;
        if (tagsDay.size() > 0) {
            alarmTagDayFromCB = getTagFromCB(
                    cbAlarmInputHariSeninfvbi,
                    cbAlarmInputHariSelasafvbi,
                    cbAlarmInputHariRabufvbi,
                    cbAlarmInputHariKamisfvbi,
                    cbAlarmInputHariJumatfvbi,
                    cbAlarmInputHariSabtufvbi,
                    cbAlarmInputHariMinggufvbi
            );
        } else {
            alarmTagDayFromCB = null;
        }

        String alarmTextDayFromCB;
        if (tagsDay.size() > 0) {
            alarmTextDayFromCB = getTextFromCB(
                    cbAlarmInputHariSeninfvbi,
                    cbAlarmInputHariSelasafvbi,
                    cbAlarmInputHariRabufvbi,
                    cbAlarmInputHariKamisfvbi,
                    cbAlarmInputHariJumatfvbi,
                    cbAlarmInputHariSabtufvbi,
                    cbAlarmInputHariMinggufvbi
            );
        } else {
            alarmTextDayFromCB = null;
        }

        if (tagsDay.size() != 0) {
            for (int i = 0; i < tagsDay.size(); i++) {
                int tagDay = Integer.parseInt(tagsDay.get(i));
                Log.d("Lihat", "setSaveUpdateAlarm AlarmActivity : " + i);
                //int reqCode = SystemUtil.getID();
                int reqCode = (int) System.currentTimeMillis() + i;

                //PendingIntent pendingIntent = DateTimeAlarmUtil.setPendingIntentMakeAlarm(AlarmActivity.this, intent, reqCode);
                //DateTimeAlarmUtil.setAlarmRepeatDay(AlarmActivity.this, pendingIntent, currentHour, currentMinute, tagDay);

                if (i == (tagsDay.size() - 1)) {
                    alarmSubIdStringBuilder.append(reqCode);
                } else {
                    alarmSubIdStringBuilder.append(reqCode + ",");
                }
            }
        } else {
            //int reqCode = SystemUtil.getID();
            int reqCode = (int) System.currentTimeMillis();

            //PendingIntent pendingIntent = DateTimeAlarmUtil.setPendingIntentMakeAlarm(AlarmActivity.this, intent, reqCode);
            //DateTimeAlarmUtil.setAlarm(AlarmActivity.this, pendingIntent, currentHour, currentMinute);

            alarmSubIdStringBuilder.append(reqCode);
        }
        alarmSubIdNew = alarmSubIdStringBuilder.toString();

        Log.d("Lihat", "setSaveUpdateAlarm AlarmActivity alarmId : " + alarmId);
        Log.d("Lihat", "setSaveUpdateAlarm AlarmActivity alarmSubIdUpdate : " + alarmSubIdNew);
        Log.d("Lihat", "setSaveUpdateAlarm AlarmActivity alarmTitle : " + alarmTitle);
        Log.d("Lihat", "setSaveUpdateAlarm AlarmActivity currentHourString : " + currentHourString);
        Log.d("Lihat", "setSaveUpdateAlarm AlarmActivity currentMinuteString : " + currentMinuteString);
        Log.d("Lihat", "setSaveUpdateAlarm AlarmActivity alarmTagDayFromCB : " + alarmTagDayFromCB);
        Log.d("Lihat", "setSaveUpdateAlarm AlarmActivity alarmTextDayFromCB : " + alarmTextDayFromCB);
        Log.d("Lihat", "setSaveUpdateAlarm AlarmActivity alarmContactName : " + alarmContactName);
        Log.d("Lihat", "setSaveUpdateAlarm AlarmActivity alarmContactNo : " + alarmContactNo);
        Log.d("Lihat", "setSaveUpdateAlarm AlarmActivity recordPath : " + recordPath);
        Log.d("Lihat", "setSaveUpdateAlarm AlarmActivity ringtoneTitle : " + ringtoneTitle);
        Log.d("Lihat", "setSaveUpdateAlarm AlarmActivity ringtonePath : " + ringtonePath);

        AlarmModel model = new AlarmModel();
        model.setAlarm_id(alarmId);
        model.setAlarm_sub_id(alarmSubIdNew);
        model.setAlarm_title(alarmTitle);
        model.setAlarm_hour(currentHourString);
        model.setAlarm_minute(currentMinuteString);
        model.setAlarm_day(alarmTagDayFromCB);
        model.setAlarm_text_day(alarmTextDayFromCB);
        model.setAlarm_friend(alarmContactName);
        model.setAlarm_friend_no(alarmContactNo);
        model.setAlarm_voice_uri(recordPath);
        model.setAlarm_ringtone(ringtoneTitle);
        model.setAlarm_ringtone_uri(ringtonePath);

        if (!isUpdate) {
            db.alarmSave(model);
            Snackbar.make(findViewById(android.R.id.content), "Set and Save Alarm Success", Snackbar.LENGTH_SHORT).show();
        } else {
            db.alarmUpdate(model, alarmId);
            Snackbar.make(findViewById(android.R.id.content), "Set and Update Alarm Success", Snackbar.LENGTH_SHORT).show();
        }

        DateTimeAlarmUtil.setAlarmSwitch(getApplicationContext(), model, 1);

        isSaveUpdate = true;

        finish();
        overridePendingTransition(R.anim.fadein, R.anim.keluar_ke_bawah);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_CODE_RINGTONE_PICKER) {
            ringtoneUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            if (ringtoneUri != null) {
                ringtonePath = ringtoneUri.toString();
                //ringtonePath = ringtoneUri.getPath();
                Log.d("Lihat", "onActivityResult AlarmActivity : " + ringtoneUri.toString());
                Log.d("Lihat", "onActivityResult AlarmActivity : " + ringtoneUri.getPath());

                Ringtone ringtone = RingtoneManager.getRingtone(this, ringtoneUri);
                ringtoneTitle = ringtone.getTitle(this);
                Log.d("Lihat", "onActivityResult AlarmActivity : " + ringtone.getTitle(this));

                //tvAlarmInputRingtonefvbi.setText(ringtone.getTitle(this));
                //tvAlarmInputRingtonefvbi.setTextColor(getResources().getColor(R.color.androidSblue));
                ivAlarmInputRingtonefvbi.setVisibility(View.VISIBLE);
            } else {
                ringtonePath = null;
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == REQ_CODE_CONTACK_PICKER) {
            ArrayList<ContactPickerModel> selectedContacts = data.getParcelableArrayListExtra("SelectedContacts");
            Log.d("Lihat", "onActivityResult AlarmActivity : " + selectedContacts.size());
            HashSet<ContactPickerModel> hashSet = new HashSet<ContactPickerModel>(selectedContacts);
            selectedContacts.clear();
            selectedContacts.addAll(hashSet);

            String display = "";
            List<String> contactsFilter = new ArrayList<>();
            StringBuilder contactName = new StringBuilder();
            StringBuilder contactNo = new StringBuilder();
            for (int i = 0; i < selectedContacts.size(); i++) {
                ContactPickerModel model = selectedContacts.get(i);
                contactsFilter.add(model.getName() + "|" + model.getPhone());

                if (i == (selectedContacts.size() - 1)) {
                    contactName.append(model.getName());
                    contactNo.append(model.getPhone());
                } else {
                    contactName.append(model.getName() + ",");
                    contactNo.append(model.getPhone() + ",");
                }

            }
            Log.d("Lihat", "onActivityResult AlarmActivity : " + contactsFilter);
            Log.d("Lihat", "onActivityResult AlarmActivity : " + contactName);
            Log.d("Lihat", "onActivityResult AlarmActivity : " + contactNo);
            alarmContactName = contactName.toString();
            alarmContactNo = contactNo.toString();
            ivAlarmInputAddFriendfvbi.setVisibility(View.VISIBLE);
            //tvAlarmInputAddFriendfvbi.setText(builder);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_sub, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuCheckActivity) {
            if (TextUtils.isEmpty(etAlarmInputJudulfvbi.getText().toString().trim())) {
                InputValidUtil.errorET(etAlarmInputJudulfvbi, "empty field");
            } else {
                setSaveUpdateAlarm();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        dialogBack();
    }

    private void dialogBack() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure to exit without save?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                goBack();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void goBack() {
        if (isUpdate) {
            if (isActive == 0) {
                DateTimeAlarmUtil.setAlarmSwitch(getApplicationContext(), db.alarmGetOne(alarmId), 0);
                Snackbar.make(findViewById(android.R.id.content), "Alarm Deactived", Snackbar.LENGTH_SHORT).show();
            } else {
                DateTimeAlarmUtil.setAlarmSwitch(getApplicationContext(), db.alarmGetOne(alarmId), 1);
                Snackbar.make(findViewById(android.R.id.content), "Alarm Actived", Snackbar.LENGTH_SHORT).show();
            }
        }

        stopDeleteRecord();
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.keluar_ke_bawah);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopDeleteRecord();
    }

    /*@Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        allPixels = savedInstanceState.getFloat(BUNDLE_LIST_PIXELS);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat(BUNDLE_LIST_PIXELS, allPixels);
    }*/
}
