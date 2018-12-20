package id.co.halloarif.catatanku.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.model.AlarmModel;
import id.co.halloarif.catatanku.model.ContactPickerModel;
import id.co.halloarif.catatanku.service.AlarmReceiver;
import id.co.halloarif.catatanku.support.util.DateTimeAlarmUtil;
import id.co.halloarif.catatanku.support.util.DateTimeUtil;
import id.co.halloarif.catatanku.support.util.VideoAudioUtil;

public class AlarmActivity extends AppCompatActivity {
    private static final int REQ_CODE_CONTACK_PICKER = 4;
    private static final int REQ_CODE_RINGTONE_PICKER = 5;
    private static final String BUNDLE_LIST_PIXELS = "allPixels";
    MediaRecorder recorder = new MediaRecorder();
    private CheckBox cbAlarmInputHariSeninfvbi;
    private CheckBox cbAlarmInputHariSelasafvbi;
    private CheckBox cbAlarmInputHariRabufvbi;
    private CheckBox cbAlarmInputHariKamisfvbi;
    private CheckBox cbAlarmInputHariJumatfvbi;
    private CheckBox cbAlarmInputHariSabtufvbi;
    private CheckBox cbAlarmInputHariMinggufvbi;
    private LinearLayout llAlarmInputAddfriendfvbi;
    private TextView tvAlarmInputAddFriendfvbi;
    private LinearLayout llAlarmInputVoicefvbi;
    private TextView tvAlarmInputVoicefvbi;
    private ImageView ivAlarmInputVoicePlayStopfvbi;
    private LinearLayout llAlarmInputRingtonefvbi;
    private TextView tvAlarmInputRingtonefvbi;
    private Button bAlarmInputBuatfvbi;
    private WheelHourPicker whpAlarmInputJamfvbi;
    private WheelMinutePicker whpAlarmInputMenitfvbi;
    private int currentHour;
    private int currentMinute;
    private String AmPm;
    private String hh;
    private String mm;
    private String HH;
    private String sPhoneNo;
    private boolean isRecord = false;
    private String recordPath;
    private MediaPlayer mPlayer;
    private boolean isPlay = false;
    private String ringtonePath;
    private Uri uriRingtone;
    private float allPixels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_input);

        initComponent();
        initParam();
        initSession();
        initContent();
        initListener();
    }

    private void initComponent() {
        cbAlarmInputHariSeninfvbi = (CheckBox) findViewById(R.id.cbAlarmInputHariSenin);
        cbAlarmInputHariSelasafvbi = (CheckBox) findViewById(R.id.cbAlarmInputHariSelasa);
        cbAlarmInputHariRabufvbi = (CheckBox) findViewById(R.id.cbAlarmInputHariRabu);
        cbAlarmInputHariKamisfvbi = (CheckBox) findViewById(R.id.cbAlarmInputHariKamis);
        cbAlarmInputHariJumatfvbi = (CheckBox) findViewById(R.id.cbAlarmInputHariJumat);
        cbAlarmInputHariSabtufvbi = (CheckBox) findViewById(R.id.cbAlarmInputHariSabtu);
        cbAlarmInputHariMinggufvbi = (CheckBox) findViewById(R.id.cbAlarmInputHariMinggu);
        llAlarmInputAddfriendfvbi = (LinearLayout) findViewById(R.id.llAlarmInputAddfriend);
        tvAlarmInputAddFriendfvbi = (TextView) findViewById(R.id.tvAlarmInputAddFriend);
        llAlarmInputVoicefvbi = (LinearLayout) findViewById(R.id.llAlarmInputVoice);
        tvAlarmInputVoicefvbi = (TextView) findViewById(R.id.tvAlarmInputVoice);
        ivAlarmInputVoicePlayStopfvbi = (ImageView) findViewById(R.id.ivAlarmInputVoicePlayStop);
        llAlarmInputRingtonefvbi = (LinearLayout) findViewById(R.id.llAlarmInputRingtone);
        tvAlarmInputRingtonefvbi = (TextView) findViewById(R.id.tvAlarmInputRingtone);
        bAlarmInputBuatfvbi = (Button) findViewById(R.id.bAlarmInputBuat);

        whpAlarmInputJamfvbi = (WheelHourPicker) findViewById(R.id.whpAlarmInputJam);
        whpAlarmInputMenitfvbi = (WheelMinutePicker) findViewById(R.id.whpAlarmInputMenit);

    }

    private void initParam() {

    }

    private void initSession() {

    }

    private void initContent() {
        AmPm = DateTimeUtil.dateToString(DateTimeUtil.getCurrentDate(), new SimpleDateFormat("a"));
        hh = DateTimeUtil.dateToString(DateTimeUtil.getCurrentDate(), new SimpleDateFormat("h"));
        mm = DateTimeUtil.dateToString(DateTimeUtil.getCurrentDate(), new SimpleDateFormat("mm"));


        initTimePicker();
    }

    private void initTimePicker() {
        whpAlarmInputJamfvbi.setIsAmPm(false);
        whpAlarmInputJamfvbi.setDefault(HH);
        whpAlarmInputJamfvbi.setHourChangedListener(new WheelHourPicker.OnHourChangedListener() {
            @Override
            public void onHourChanged(WheelHourPicker picker, int hour) {
                Log.d("Lihat", "onHourChanged AlarmActivity : " + hour);
                currentHour = hour;
            }
        });

        whpAlarmInputMenitfvbi.setStepMinutes(1);
        whpAlarmInputMenitfvbi.setOnMinuteChangedListener(new WheelMinutePicker.OnMinuteChangedListener() {
            @Override
            public void onMinuteChanged(WheelMinutePicker picker, int minutes) {
                Log.d("Lihat", "onMinuteChanged AlarmActivity : " + minutes);
                currentMinute = minutes;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
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
                if (!isRecord) {
                    if (!TextUtils.isEmpty(recordPath)) {
                        File file = new File(recordPath);
                        if (file.exists()) {
                            file.delete();
                        }
                    }

                    ivAlarmInputVoicePlayStopfvbi.setVisibility(View.GONE);

                    VideoAudioUtil.MediaRecorderReady(recorder);
                    VideoAudioUtil.startRecording(recorder);

                    llAlarmInputVoicefvbi.setBackgroundColor(getResources().getColor(R.color.super_red));
                    isRecord = true;
                    Toast.makeText(getApplicationContext(), "Start Record", Toast.LENGTH_SHORT).show();

                    tvAlarmInputVoicefvbi.setText("recording");
                    tvAlarmInputVoicefvbi.setTextColor(getResources().getColor(R.color.white));
                } else {
                    recordPath = VideoAudioUtil.stopRecording(recorder);

                    ivAlarmInputVoicePlayStopfvbi.setVisibility(View.VISIBLE);

                    llAlarmInputVoicefvbi.setBackgroundColor(getResources().getColor(R.color.white));
                    isRecord = false;
                    Toast.makeText(getApplicationContext(), "Stop Record", Toast.LENGTH_SHORT).show();

                    tvAlarmInputVoicefvbi.setText("recorded");
                    tvAlarmInputVoicefvbi.setTextColor(getResources().getColor(R.color.androidSblue));
                }
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
                            recordPath = "";

                            ivAlarmInputVoicePlayStopfvbi.setVisibility(View.GONE);

                            llAlarmInputVoicefvbi.setBackgroundColor(getResources().getColor(R.color.white));
                            isRecord = false;
                            Toast.makeText(getApplicationContext(), "Delete record", Toast.LENGTH_SHORT).show();

                            tvAlarmInputVoicefvbi.setText("Voice");
                            tvAlarmInputVoicefvbi.setTextColor(getResources().getColor(R.color.gray));
                        }
                    }
                }

                return false;
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

        ivAlarmInputVoicePlayStopfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPlay) {
                    playMediaPlayer();
                } else {
                    stopMediaPlayer();
                }
            }
        });

        bAlarmInputBuatfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSaveAlarm();
            }
        });
    }

    private void setSaveAlarm() {
        AlarmModel model = new AlarmModel();

        List<Integer> intFromCB = getIntFromCB(
                cbAlarmInputHariSeninfvbi,
                cbAlarmInputHariSelasafvbi,
                cbAlarmInputHariRabufvbi,
                cbAlarmInputHariKamisfvbi,
                cbAlarmInputHariJumatfvbi,
                cbAlarmInputHariSabtufvbi,
                cbAlarmInputHariMinggufvbi
        );

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("title", "Notifikasi Alarm");
        intent.putExtra("msg", "Hay Faisal. sekarang sudah jam " + currentHour + ":" + currentMinute);
        intent.putExtra("record", recordPath);
        intent.putExtra("ringtone", ringtonePath);
        PendingIntent pendingIntent = DateTimeAlarmUtil.setPendingIntentMakeAlarm(AlarmActivity.this, intent, 0);

        DateTimeAlarmUtil.setAlarm(AlarmActivity.this, pendingIntent, currentHour, currentMinute);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_CODE_RINGTONE_PICKER) {
            uriRingtone = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            if (uriRingtone != null) {
                ringtonePath = uriRingtone.toString();
//                ringtonePath = uriRingtone.getPath();
                Log.d("Lihat", "onActivityResult AlarmActivity : " + uriRingtone.toString());
                Log.d("Lihat", "onActivityResult AlarmActivity : " + uriRingtone.getPath());

                Ringtone ringtone = RingtoneManager.getRingtone(this, uriRingtone);
                Log.d("Lihat", "onActivityResult AlarmActivity : " + ringtone.getTitle(this));

                tvAlarmInputRingtonefvbi.setText(ringtone.getTitle(this));
                tvAlarmInputRingtonefvbi.setTextColor(getResources().getColor(R.color.androidSblue));
            } else {
                ringtonePath = null;
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == REQ_CODE_CONTACK_PICKER) {
            /*Cursor cursor = null;
            try {
                String phoneNo = null;
                String name = null;
                Uri uri = data.getData();
                cursor = getContentResolver().query(uri, null, null, null, null);
                cursor.moveToFirst();

                int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

                phoneNo = cursor.getString(phoneIndex);
                name = cursor.getString(nameIndex);

                sPhoneNo = phoneNo;

                //tvAlarmInputAddFriendfvbi.setText(name);
                tvAlarmInputAddFriendfvbi.setText(name + " (" + phoneNo + ")");
                tvAlarmInputAddFriendfvbi.setTextColor(getResources().getColor(R.color.androidSblue));
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            ArrayList<ContactPickerModel> selectedContacts = data.getParcelableArrayListExtra("SelectedContacts");
            Log.d("Lihat", "onActivityResult AlarmActivity : " + selectedContacts.size());
            HashSet<ContactPickerModel> hashSet = new HashSet<ContactPickerModel>(selectedContacts);
            selectedContacts.clear();
            selectedContacts.addAll(hashSet);

            String display = "";
            List<String> contactsFilter = new ArrayList<>();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < selectedContacts.size(); i++) {
                ContactPickerModel model = selectedContacts.get(i);
                contactsFilter.add(model.getName() + "|" + model.getPhone());

                if (i == (selectedContacts.size() - 1)) {
                    builder.append(model.getPhone());
                } else {
                    builder.append(model.getPhone() + ",");
                }

            }
            Log.d("Lihat", "onActivityResult AlarmActivity : " + contactsFilter);
            Log.d("Lihat", "onActivityResult AlarmActivity : " + builder);
            sPhoneNo = builder.toString();
            tvAlarmInputAddFriendfvbi.setText(builder);
        }
    }

    @Override
    public void onBackPressed() {
        dialogQuit();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        allPixels = savedInstanceState.getFloat(BUNDLE_LIST_PIXELS);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat(BUNDLE_LIST_PIXELS, allPixels);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopDeleteRecord();
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

    private void playMediaPlayer() {
        // Even you can refer resource in res/raw directory
        //Uri myUri = Uri.parse("android.resource://com.prgguru.example/" + R.raw.hosannatamil);
        //Uri myUri1 = Uri.parse("file:///sdcard/Songs/ARR Hits/hosannatamil.mp3");

        isPlay = true;
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
        isPlay = false;
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
}
