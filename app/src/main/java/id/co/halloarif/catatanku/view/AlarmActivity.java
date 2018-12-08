package id.co.halloarif.catatanku.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaRecorder;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rm.rmswitch.RMSwitch;
import com.xw.repo.BubbleSeekBar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.support.util.DateTimeUtil;
import id.co.halloarif.catatanku.support.util.VideoAudioUtil;

public class AlarmActivity extends AppCompatActivity {
    private RMSwitch rmsAlarmInputfvbi;
    private BubbleSeekBar bsbAlarmInputJamfvbi;
    private BubbleSeekBar bsbAlarmInputMenitfvbi;
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
    private LinearLayout llAlarmInputRingtonefvbi;
    private TextView tvAlarmInputRingtonefvbi;
    private Button bAlarmInputBuatfvbi;

    private String sPhoneNo;

    private String voicePath = null;
    private boolean isRecord = false;
    MediaRecorder recorder = new MediaRecorder();

    private String chosenRingtone;

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
        rmsAlarmInputfvbi = (RMSwitch) findViewById(R.id.rmsAlarmInput);
        bsbAlarmInputJamfvbi = (BubbleSeekBar) findViewById(R.id.bsbAlarmInputJam);
        bsbAlarmInputMenitfvbi = (BubbleSeekBar) findViewById(R.id.bsbAlarmInputMenit);
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
        llAlarmInputRingtonefvbi = (LinearLayout) findViewById(R.id.llAlarmInputRingtone);
        tvAlarmInputRingtonefvbi = (TextView) findViewById(R.id.tvAlarmInputRingtone);
        bAlarmInputBuatfvbi = (Button) findViewById(R.id.bAlarmInputBuat);
    }

    private void initParam() {

    }

    private void initSession() {

    }

    private void initContent() {
        String AmPm = DateTimeUtil.dateToString(DateTimeUtil.getCurrentDate(), new SimpleDateFormat("a"));
        String hh = DateTimeUtil.dateToString(DateTimeUtil.getCurrentDate(), new SimpleDateFormat("h"));
        String mm = DateTimeUtil.dateToString(DateTimeUtil.getCurrentDate(), new SimpleDateFormat("m"));
        if (AmPm.equalsIgnoreCase("AM")) {
            rmsAlarmInputfvbi.setChecked(false);
        } else {
            rmsAlarmInputfvbi.setChecked(true);
        }
        bsbAlarmInputJamfvbi.setProgress(Float.parseFloat(hh));
        bsbAlarmInputMenitfvbi.setProgress(Float.parseFloat(mm));
        bsbAlarmInputJamfvbi.getConfigBuilder().autoAdjustSectionMark();

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        bsbAlarmInputJamfvbi.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                Log.d("Lihat", "onProgressChanged AlarmActivity : " + progress);
                Log.d("Lihat", "onProgressChanged AlarmActivity : " + progressFloat);
                Log.d("Lihat", "onProgressChanged AlarmActivity : " + fromUser);
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                Log.d("Lihat", "getProgressOnActionUp AlarmActivity : " + progress);
                Log.d("Lihat", "getProgressOnActionUp AlarmActivity : " + progressFloat);
            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                Log.d("Lihat", "getProgressOnFinally AlarmActivity : " + progress);
                Log.d("Lihat", "getProgressOnFinally AlarmActivity : " + progressFloat);
                Log.d("Lihat", "getProgressOnFinally AlarmActivity : " + fromUser);
            }
        });

        llAlarmInputAddfriendfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent, 4);
            }
        });
        llAlarmInputVoicefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRecord) {
                    if (!TextUtils.isEmpty(voicePath)) {
                        File file = new File(voicePath);
                        if (file.exists()) {
                            file.delete();
                        }
                    }

                    VideoAudioUtil.MediaRecorderReady(recorder);
                    VideoAudioUtil.startRecording(recorder);

                    llAlarmInputVoicefvbi.setBackgroundColor(getResources().getColor(R.color.super_red));
                    isRecord = true;
                    Toast.makeText(getApplicationContext(), "Start Record", Toast.LENGTH_SHORT).show();

                    tvAlarmInputVoicefvbi.setText("recording");
                    tvAlarmInputVoicefvbi.setTextColor(getResources().getColor(R.color.white));
                } else {
                    VideoAudioUtil.stopRecording(recorder);

                    llAlarmInputVoicefvbi.setBackgroundColor(getResources().getColor(R.color.white));
                    isRecord = false;
                    Toast.makeText(getApplicationContext(), "Stop Record", Toast.LENGTH_SHORT).show();

                    tvAlarmInputVoicefvbi.setText("recorded");
                    tvAlarmInputVoicefvbi.setTextColor(getResources().getColor(R.color.androidSblue));

                    /*new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //code here
                            Log.d("Lihat", "onClick AlarmActivity : " + voicePath);
                            Uri uri = Uri.parse(getFilename());
                            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                            mediaMetadataRetriever.setDataSource(getFilename());
                            String durationStr = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                            int millSecond = Integer.parseInt(durationStr);
                            mediaMetadataRetriever.release();
                            Log.d("Lihat", "onClick AlarmActivity : " + millSecond);
                        }
                    }, 2000);*/
                }
            }
        });
        llAlarmInputRingtonefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
                startActivityForResult(intent, 5);
            }
        });

        bAlarmInputBuatfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Lihat", "onClick AlarmActivity : " + bsbAlarmInputJamfvbi.getProgress());
                Log.d("Lihat", "onClick AlarmActivity : " + bsbAlarmInputMenitfvbi.getProgress());

                List<Integer> intFromCB = getIntFromCB(
                        cbAlarmInputHariSeninfvbi,
                        cbAlarmInputHariSelasafvbi,
                        cbAlarmInputHariRabufvbi,
                        cbAlarmInputHariKamisfvbi,
                        cbAlarmInputHariJumatfvbi,
                        cbAlarmInputHariSabtufvbi,
                        cbAlarmInputHariMinggufvbi
                );
                Log.d("Lihat", "onClick AlarmActivity : " + intFromCB);

            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 5) {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            if (uri != null) {
                chosenRingtone = uri.toString();
                Log.d("Lihat", "onActivityResult AlarmActivity : " + chosenRingtone);
                Log.d("Lihat", "onActivityResult AlarmActivity : " + uri.getPath());

                Ringtone ringtone = RingtoneManager.getRingtone(this, uri);
                Log.d("Lihat", "onActivityResult AlarmActivity : " + ringtone.getTitle(this));

                tvAlarmInputRingtonefvbi.setText(ringtone.getTitle(this));
                tvAlarmInputRingtonefvbi.setTextColor(getResources().getColor(R.color.androidSblue));
            } else {
                chosenRingtone = null;
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == 4) {
            Cursor cursor = null;
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
            }
        }
    }

    @Override
    public void onBackPressed() {
        dialogQuit();
    }

    private void dialogQuit() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure to exit without save?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if (isRecord) {
                    VideoAudioUtil.stopRecording(recorder);
                }

                /*File file = new File(voicePath);
                if (file.exists()) {
                    file.delete();
                }*/

                finish();
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
