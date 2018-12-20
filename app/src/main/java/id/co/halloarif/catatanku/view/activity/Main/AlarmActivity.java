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
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.database.SQLiteHelper;
import id.co.halloarif.catatanku.model.AlarmModel;
import id.co.halloarif.catatanku.model.ContactPickerModel;
import id.co.halloarif.catatanku.service.AlarmReceiver;
import id.co.halloarif.catatanku.support.util.DateTimeUtil;
import id.co.halloarif.catatanku.support.util.VideoAudioUtil;
import id.co.halloarif.catatanku.view.activity.Support.ListContactPickerCheckBox;

public class AlarmActivity extends AppCompatActivity {
    private static final int REQ_CODE_CONTACK_PICKER = 4;
    private static final int REQ_CODE_RINGTONE_PICKER = 5;

    SQLiteHelper db = new SQLiteHelper(AlarmActivity.this);
    MediaRecorder recorder = new MediaRecorder();

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
    private boolean isRecordPlay = false;
    private boolean doubleClickRecord = false;
    private MediaPlayer mPlayer;
    private String recordPath;

    private String ringtonePath;
    private String ringtoneTitle;
    private Uri ringtoneUri;

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
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < checkBoxes.length; i++) {
            CheckBox cb = checkBoxes[i];
            String tag = (String) cb.getTag();

            if (cb.isChecked()) {
                if (i == (checkBoxes.length - 1)) {
                    builder.append(tag);
                } else {
                    builder.append(tag + ",");
                }
            }
        }

        return builder.toString();
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

    private void setAlarm() {
        AlarmModel model = new AlarmModel();
        StringBuilder alarmSubId = new StringBuilder();

        String alarmId = UUID.randomUUID().toString();
        String alarmTitle = etAlarmInputJudulfvbi.getText().toString().trim();

        String s;
        if (TextUtils.isEmpty(alarmTitle)) {
            s = "";
        } else {
            s = "dan sudah waktunya " + etAlarmInputJudulfvbi.getText().toString().trim() + " . ";
        }

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("title", "Notifikasi " + alarmTitle);
        intent.putExtra("msg", "Hay Faisal . sekarang sudah jam " + currentHourString + ":" + currentMinuteString + " . " + s);
        intent.putExtra("record", recordPath);
        intent.putExtra("ringtone", ringtonePath);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        long yesterday = calendar.getTimeInMillis();
        int timeMillis = (int) (yesterday - System.currentTimeMillis());

        String alarmTagDayFromCB = getTagFromCB(
                cbAlarmInputHariSeninfvbi,
                cbAlarmInputHariSelasafvbi,
                cbAlarmInputHariRabufvbi,
                cbAlarmInputHariKamisfvbi,
                cbAlarmInputHariJumatfvbi,
                cbAlarmInputHariSabtufvbi,
                cbAlarmInputHariMinggufvbi
        );

        List<Integer> intFromCB = getIntFromCB(
                cbAlarmInputHariSeninfvbi,
                cbAlarmInputHariSelasafvbi,
                cbAlarmInputHariRabufvbi,
                cbAlarmInputHariKamisfvbi,
                cbAlarmInputHariJumatfvbi,
                cbAlarmInputHariSabtufvbi,
                cbAlarmInputHariMinggufvbi
        );

        if (intFromCB.size() == 0) {
            int reqCode = timeMillis;
            alarmSubId.append(reqCode);
            //PendingIntent pendingIntent = DateTimeAlarmUtil.setPendingIntentMakeAlarm(AlarmActivity.this, intent, reqCode);
            //DateTimeAlarmUtil.setAlarm(AlarmActivity.this, pendingIntent, currentHour, currentMinute);
        } else {
            for (int i = 0; i < intFromCB.size(); i++) {
                Integer q = intFromCB.get(i);
                int reqCode = timeMillis + i;
                //PendingIntent pendingIntent = DateTimeAlarmUtil.setPendingIntentMakeAlarm(AlarmActivity.this, intent, reqCode);
                //DateTimeAlarmUtil.setAlarmRepeatDay(AlarmActivity.this, pendingIntent, currentHour, currentMinute, q.intValue());

                if (i == (intFromCB.size() - 1)) {
                    alarmSubId.append(reqCode);
                } else {
                    alarmSubId.append(reqCode + ",");
                }
            }
        }

       /* model.setAlarm_id(alarmId);
        model.setAlarm_sub_id(alarmSubId.toString());
        model.setAlarm_title(alarmTitle);
        model.setAlarm_hour(currentHourString);
        model.setAlarm_minute(currentMinuteString);
        model.setAlarm_day(alarmTagDayFromCB);
        model.setAlarm_friend(alarmContactName);
        model.setAlarm_friend_no(alarmContactNo);
        model.setAlarm_voice_uri(recordPath);
        model.setAlarm_ringtone(ringtoneTitle);
        model.setAlarm_ringtone_uri(ringtonePath);
        db.alarmSave(model);

        LoginActivity.moveTo(AlarmActivity.this, AlarmSummaryActivity.class, true);*/

        Log.d("Lihat", "setAlarm AlarmActivity : " + alarmId);
        Log.d("Lihat", "setAlarm AlarmActivity : " + alarmSubId.toString());
        Log.d("Lihat", "setAlarm AlarmActivity : " + alarmTitle);
        Log.d("Lihat", "setAlarm AlarmActivity : " + currentHourString);
        Log.d("Lihat", "setAlarm AlarmActivity : " + currentMinuteString);
        Log.d("Lihat", "setAlarm AlarmActivity : " + alarmTagDayFromCB);
        Log.d("Lihat", "setAlarm AlarmActivity : " + alarmContactName);
        Log.d("Lihat", "setAlarm AlarmActivity : " + alarmContactNo);
        Log.d("Lihat", "setAlarm AlarmActivity : " + recordPath);
        Log.d("Lihat", "setAlarm AlarmActivity : " + ringtoneTitle);
        Log.d("Lihat", "setAlarm AlarmActivity : " + ringtonePath);
    }

    private void dialogQuit() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure to exit without save?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                stopDeleteRecord();
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.keluar_ke_bawah);
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
            setAlarm();
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        dialogQuit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopDeleteRecord();
    }
}
