package id.co.halloarif.catatanku.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaRecorder;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.singledateandtimepicker.widget.WheelHourPicker;
import com.github.florent37.singledateandtimepicker.widget.WheelMinutePicker;
import com.rm.rmswitch.RMSwitch;
import com.xw.repo.BubbleSeekBar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.model.Contact;
import id.co.halloarif.catatanku.support.util.DateTimeUtil;
import id.co.halloarif.catatanku.support.util.VideoAudioUtil;
import id.co.halloarif.catatanku.view.adapter.DateAdapter;

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

    private RecyclerView rvAlarmJamfvbi;
    private RecyclerView rvAlarmMenitfvbi;
    private WheelHourPicker whpAlarmInputJamfvbi;
    private WheelMinutePicker whpAlarmInputMenitfvbi;

    private String sPhoneNo;

    private String voicePath = null;
    private boolean isRecord = false;
    MediaRecorder recorder = new MediaRecorder();

    private String chosenRingtone;

    private static final String BUNDLE_LIST_PIXELS = "allPixels";
    private float itemWidth;
    private float padding;
    private float firstItemWidth;
    private float allPixels;
    private int mLastPosition;

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

        rvAlarmJamfvbi = (RecyclerView) findViewById(R.id.rvAlarmJam);
        rvAlarmMenitfvbi = (RecyclerView) findViewById(R.id.rvAlarmMenit);

        whpAlarmInputJamfvbi = (WheelHourPicker) findViewById(R.id.whpAlarmInputJam);
        whpAlarmInputMenitfvbi = (WheelMinutePicker) findViewById(R.id.whpAlarmInputMenit);

    }

    private void initParam() {

    }

    private void initSession() {

    }

    private void initContent() {
        initSeekbarTime();

        initRecycleDate();

        initTimePicker();
    }

    private void initSeekbarTime() {
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

    private void initRecycleDate() {
        String HH = DateTimeUtil.dateToString(DateTimeUtil.getCurrentDate(), new SimpleDateFormat("H"));

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        itemWidth = getResources().getDimension(R.dimen.item_width);
        padding = (size.x - itemWidth) / 2;
        firstItemWidth = getResources().getDimension(R.dimen.padding_item_width);

        allPixels = 0;

        List<String> datesHH = new ArrayList<>();
        for (int ih = -2; ih <= 25; ih++) {
            datesHH.add(String.valueOf(ih));
        }
        Log.d("Lihat", "initRecycleDate AlarmActivity : " + datesHH);
        Log.d("Lihat", "initRecycleDate AlarmActivity : " + datesHH.size());
        List<String> datesmm = new ArrayList<>();
        for (int im = -2; im <= 61; im++) {
            datesmm.add(String.valueOf(im));
        }
        Log.d("Lihat", "initRecycleDate AlarmActivity : " + datesmm);
        Log.d("Lihat", "initRecycleDate AlarmActivity : " + datesmm.size());

        DateAdapter adapter = new DateAdapter(AlarmActivity.this, datesHH);
        rvAlarmJamfvbi.setAdapter(adapter);
        rvAlarmJamfvbi.setLayoutManager(new LinearLayoutManager(this));
        SnapHelper snapHelper = new LinearSnapHelper() {
            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
                View centerView = findSnapView(layoutManager);
                if (centerView == null)
                    return RecyclerView.NO_POSITION;

                int position = layoutManager.getPosition(centerView);
                int targetPosition = -1;
                if (layoutManager.canScrollHorizontally()) {
                    if (velocityX < 0) {
                        targetPosition = position - 1;
                    } else {
                        targetPosition = position + 1;
                    }
                }

                if (layoutManager.canScrollVertically()) {
                    if (velocityY < 0) {
                        targetPosition = position - 1;
                    } else {
                        targetPosition = position + 1;
                    }
                }

                final int firstItem = 0;
                final int lastItem = layoutManager.getItemCount() - 1;
                targetPosition = Math.min(lastItem, Math.max(targetPosition, firstItem));
                Log.d("Lihat", "findTargetSnapPosition AlarmActivity : " + targetPosition);
                return targetPosition;
            }
        };
        snapHelper.attachToRecyclerView(rvAlarmJamfvbi);
        rvAlarmJamfvbi.setOnFlingListener(snapHelper);

        DateAdapter adapter2 = new DateAdapter(AlarmActivity.this, datesmm);
        rvAlarmMenitfvbi.setAdapter(adapter2);
        rvAlarmMenitfvbi.setLayoutManager(new LinearLayoutManager(this));
        SnapHelper snapHelper2 = new LinearSnapHelper();
        snapHelper2.attachToRecyclerView(rvAlarmMenitfvbi);
        rvAlarmJamfvbi.setOnFlingListener(snapHelper);
    }

    private void initTimePicker() {
        whpAlarmInputJamfvbi.setIsAmPm(false);
        whpAlarmInputMenitfvbi.setStepMinutes(1);
        whpAlarmInputJamfvbi.setHourChangedListener(new WheelHourPicker.OnHourChangedListener() {
            @Override
            public void onHourChanged(WheelHourPicker picker, int hour) {
                Log.d("Lihat", "onHourChanged AlarmActivity : " + hour);
            }
        });
        whpAlarmInputMenitfvbi.setOnMinuteChangedListener(new WheelMinutePicker.OnMinuteChangedListener() {
            @Override
            public void onMinuteChanged(WheelMinutePicker picker, int minutes) {
                Log.d("Lihat", "onMinuteChanged AlarmActivity : " + minutes);
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        rvAlarmJamfvbi.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                synchronized (this) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        calculatePositionAndScroll(recyclerView);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                allPixels += dx;
            }
        });
        rvAlarmMenitfvbi.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                synchronized (this) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        calculatePositionAndScroll(recyclerView);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                allPixels += dx;
            }
        });

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
                //Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                //startActivityForResult(intent, 4);

                Intent intentContactPick = new Intent(AlarmActivity.this, ListContactPickerCheckBox.class);
                startActivityForResult(intentContactPick, 4);
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

            ArrayList<Contact> selectedContacts = data.getParcelableArrayListExtra("SelectedContacts");
            Log.d("Lihat", "onActivityResult AlarmActivity : " + selectedContacts.size());
            HashSet<Contact> hashSet = new HashSet<Contact>(selectedContacts);
            selectedContacts.clear();
            selectedContacts.addAll(hashSet);

            String display = "";
            List<String> contactsFilter = new ArrayList<>();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < selectedContacts.size(); i++) {
                Contact model = selectedContacts.get(i);
                contactsFilter.add(model.getName() + "|" + model.getPhone());

                if (i == (selectedContacts.size() - 1)) {
                    builder.append(model.getPhone());
                } else {
                    builder.append(model.getPhone() + ",");
                }

            }
            Log.d("Lihat", "onActivityResult AlarmActivity : " + contactsFilter);
            Log.d("Lihat", "onActivityResult AlarmActivity : " + builder);
            tvAlarmInputAddFriendfvbi.setText(builder);
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

    private void calculatePositionAndScroll(RecyclerView recyclerView) {
        int expectedPosition = Math.round((allPixels + padding - firstItemWidth) / itemWidth);
        // Special cases for the padding items
        if (expectedPosition == -1) {
            expectedPosition = 0;
        } else if (expectedPosition >= recyclerView.getAdapter().getItemCount() - 2) {
            expectedPosition--;
        }
        Log.d("Lihat", "calculatePositionAndScroll AlarmActivity : " + expectedPosition);
        scrollListToPosition(recyclerView, expectedPosition);
    }

    private void scrollListToPosition(RecyclerView recyclerView, int expectedPosition) {
        float targetScrollPos = expectedPosition * itemWidth + firstItemWidth - padding;
        float missingPx = targetScrollPos - allPixels;
        if (missingPx != 0) {
            recyclerView.smoothScrollBy((int) missingPx, 0);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        final RecyclerView rvAlarmJamfvbi = (RecyclerView) findViewById(R.id.rvAlarmJam);
        final RecyclerView rvAlarmMenitfvbi = (RecyclerView) findViewById(R.id.rvAlarmMenit);

        ViewTreeObserver vto = rvAlarmJamfvbi.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rvAlarmJamfvbi.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                calculatePositionAndScroll(rvAlarmJamfvbi);
            }
        });
        ViewTreeObserver vto2 = rvAlarmMenitfvbi.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rvAlarmMenitfvbi.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                calculatePositionAndScroll(rvAlarmMenitfvbi);
            }
        });
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
}
