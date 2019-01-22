package id.co.halloarif.catatanku.view.activity.Main;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.florent37.singledateandtimepicker.widget.WheelHourPicker;
import com.github.florent37.singledateandtimepicker.widget.WheelMinutePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import id.co.halloarif.catatanku.ISeasonConfig;
import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.support.util.DateTimeUtil;
import id.co.halloarif.catatanku.support.util.SystemUtil;
import id.co.halloarif.catatanku.view.activity.Support.MemberPickerActivity;
import id.co.halloarif.catatanku.view.activity.Support.PlacesAutoCompleteActivity;

public class AcaraActivity extends AppCompatActivity {

    private static final int REQ_CODE_MAPS = 1;
    private static final int REQ_CODE_FRIEND = 2;
    private static final int REQ_CODE_RINGTONE_PICKER = 5;
    //private AcaraModel mainModel = new AcaraModel();

    private NestedScrollView nsvAcaraInputfvbi;
    private EditText etAcaraInputJudulfvbi;
    private LinearLayout llAcaraInputLokasifvbi;
    private LinearLayout llAcaraInputTemanfvbi;
    private Spinner spAcaraInputMenitPengingatfvbi;
    private CheckBox cbAcaraInputEvaluasifvbi;
    private CalendarView cvAcaraInputDateStartfvbi;
    private WheelHourPicker whpAlarmInputJamStartfvbi;
    private WheelMinutePicker whpAlarmInputMenitStartfvbi;
    private TextView tvAcaraInputDateStart;
    private TextView tvAcaraInputTimeStart;
    private CalendarView cvAcaraInputDateEndfvbi;
    private WheelHourPicker whpAlarmInputJamEndfvbi;
    private WheelMinutePicker whpAlarmInputMenitEndfvbi;
    private TextView tvAcaraInputDateEnd;
    private TextView tvAcaraInputTimeEnd;
    private LinearLayout llAlarmInputAddfriendfvbi;
    private TextView tvAcaraInputLokasiNmJlnfvbi;
    private TextView tvAcaraInputTemanNamafvbi;
    private LinearLayout llAcaraInputNotifikasiPopupfvbi;
    private ImageView ivAcaraInputNotifikasiPopupfvbi;
    private LinearLayout llAcaraInputGoogleVoicefvbi;
    private ImageView ivAcaraInputGoogleVoicefvbi;
    private LinearLayout llAcaraInputRingtonefvbi;
    private TextView tvAlarmInputVoicefvbi;
    private ImageView ivAcaraInputRingtonefvbi;
    private LinearLayout llAcaraInputEvaluasifvbi;

    private int currentHourStart;
    private int currentMinuteStart;
    private int currentHourEnd;
    private int currentMinuteEnd;
    private String currentHourStringStart;
    private String currentMinuteStringStart;
    private String currentHourStringEnd;
    private String currentMinuteStringEnd;
    private Calendar calendarStart;
    private Calendar calendarEnd;

    private String friend;
    private String friend_email;

    private String ringtonePath;
    private String ringtoneTitle;
    private Uri ringtoneUri;

    private boolean isPopup = false;

    private boolean isGoogleVoice = false;

    private int reminderTime;

    private String locationName;
    private double locationLat;
    private double LocationLng;

    private boolean isEvaluasi = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acara_input);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initComponent();
        initParam();
        initSession();
        initContent();
        initListener();

    }

    private void initComponent() {
        nsvAcaraInputfvbi = (NestedScrollView) findViewById(R.id.nsvAcaraInput);
        etAcaraInputJudulfvbi = (EditText) findViewById(R.id.etAcaraInputJudul);
        llAcaraInputLokasifvbi = (LinearLayout) findViewById(R.id.llAcaraInputLokasi);
        llAcaraInputTemanfvbi = (LinearLayout) findViewById(R.id.llAcaraInputTeman);
        spAcaraInputMenitPengingatfvbi = (Spinner) findViewById(R.id.spAcaraInputMenitPengingat);
        cbAcaraInputEvaluasifvbi = (CheckBox) findViewById(R.id.cbAcaraInputEvaluasi);
        cvAcaraInputDateStartfvbi = (CalendarView) findViewById(R.id.cvAcaraInputDateStart);
        whpAlarmInputJamStartfvbi = (WheelHourPicker) findViewById(R.id.whpAlarmInputJamStart);
        whpAlarmInputMenitStartfvbi = (WheelMinutePicker) findViewById(R.id.whpAlarmInputMenitStart);
        tvAcaraInputDateStart = (TextView) findViewById(R.id.tvAcaraInputDateStart);
        tvAcaraInputTimeStart = (TextView) findViewById(R.id.tvAcaraInputTimeStart);
        cvAcaraInputDateEndfvbi = (CalendarView) findViewById(R.id.cvAcaraInputDateEnd);
        whpAlarmInputJamEndfvbi = (WheelHourPicker) findViewById(R.id.whpAlarmInputJamEnd);
        whpAlarmInputMenitEndfvbi = (WheelMinutePicker) findViewById(R.id.whpAlarmInputMenitEnd);
        tvAcaraInputDateEnd = (TextView) findViewById(R.id.tvAcaraInputDateEnd);
        tvAcaraInputTimeEnd = (TextView) findViewById(R.id.tvAcaraInputTimeEnd);
        llAlarmInputAddfriendfvbi = (LinearLayout) findViewById(R.id.llAlarmInputAddfriend);
        tvAcaraInputLokasiNmJlnfvbi = (TextView) findViewById(R.id.tvAcaraInputLokasiNmJln);
        tvAcaraInputTemanNamafvbi = (TextView) findViewById(R.id.tvAcaraInputTemanNama);
        llAcaraInputNotifikasiPopupfvbi = (LinearLayout) findViewById(R.id.llAcaraInputNotifikasiPopup);
        ivAcaraInputNotifikasiPopupfvbi = (ImageView) findViewById(R.id.ivAcaraInputNotifikasiPopup);
        llAcaraInputGoogleVoicefvbi = (LinearLayout) findViewById(R.id.llAcaraInputGoogleVoice);
        ivAcaraInputGoogleVoicefvbi = (ImageView) findViewById(R.id.ivAcaraInputGoogleVoice);
        llAcaraInputRingtonefvbi = (LinearLayout) findViewById(R.id.llAcaraInputRingtone);
        tvAlarmInputVoicefvbi = (TextView) findViewById(R.id.tvAlarmInputVoice);
        ivAcaraInputRingtonefvbi = (ImageView) findViewById(R.id.ivAcaraInputRingtone);
        llAcaraInputEvaluasifvbi = (LinearLayout) findViewById(R.id.llAcaraInputEvaluasi);

    }

    private void initParam() {

    }

    private void initSession() {

    }

    private void initContent() {

        String HH = DateTimeUtil.dateToString(DateTimeUtil.getCurrentDate(), new SimpleDateFormat("HH"));
        String mm = DateTimeUtil.dateToString(DateTimeUtil.getCurrentDate(), new SimpleDateFormat("mm"));

        initDatePicker(HH, mm);
        initTimePicker(HH, mm);
        initReminder();
    }

    private void initListener() {
        llAcaraInputLokasifvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AcaraActivity.this, PlacesAutoCompleteActivity.class);
                if (locationLat != 0 && LocationLng != 0) {
                    intent.putExtra(ISeasonConfig.INTENT_PARAM, locationLat);
                    intent.putExtra(ISeasonConfig.INTENT_PARAM2, locationName);
                }
                startActivityForResult(intent, REQ_CODE_MAPS);
                //finish();
            }
        });
        llAcaraInputTemanfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AcaraActivity.this, MemberPickerActivity.class);
                startActivityForResult(intent, REQ_CODE_FRIEND);
                //finish();
            }
        });

        llAcaraInputRingtonefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
                startActivityForResult(intent, REQ_CODE_RINGTONE_PICKER);
            }
        });
        llAcaraInputNotifikasiPopupfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPopup) {
                    ivAcaraInputNotifikasiPopupfvbi.setVisibility(View.GONE);
                    isPopup = false;
                } else {
                    ivAcaraInputNotifikasiPopupfvbi.setVisibility(View.VISIBLE);
                    isPopup = true;
                }
            }
        });
        llAcaraInputGoogleVoicefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isGoogleVoice) {
                    ivAcaraInputGoogleVoicefvbi.setVisibility(View.GONE);
                    isGoogleVoice = false;
                } else {
                    ivAcaraInputGoogleVoicefvbi.setVisibility(View.VISIBLE);
                    isGoogleVoice = true;
                }
            }
        });

        cbAcaraInputEvaluasifvbi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isEvaluasi = true;
            }
        });
    }

    private void initDatePicker(String HH, String mm) {
        Date currentDate = DateTimeUtil.getCurrentDate();

        cvAcaraInputDateStartfvbi.setMinDate(Calendar.getInstance().getTimeInMillis());
        cvAcaraInputDateStartfvbi.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                calendarStart = Calendar.getInstance();
                calendarStart.set(i, i1, i2);
                Date date = calendarStart.getTime();
                if (DateTimeUtil.isToday(date)) {
                }

                tvAcaraInputDateStart.setText(DateTimeUtil.dateToString(date, new SimpleDateFormat("dd MMMM yyyy")));
            }
        });

        tvAcaraInputDateStart.setText(DateTimeUtil.dateToString(DateTimeUtil.getCurrentDate(), new SimpleDateFormat("dd MMMM yyyy")));

        cvAcaraInputDateEndfvbi.setMinDate(Calendar.getInstance().getTimeInMillis());
        cvAcaraInputDateEndfvbi.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                calendarEnd = Calendar.getInstance();
                calendarEnd.set(i, i1, i2);
                Date date = calendarEnd.getTime();
                if (DateTimeUtil.isToday(date)) {
                    whpAlarmInputJamEndfvbi.setMinHour(Integer.parseInt(DateTimeUtil.dateToString(DateTimeUtil.getCurrentDate(), new SimpleDateFormat("HH"))));
                }

                tvAcaraInputDateEnd.setText(DateTimeUtil.dateToString(date, new SimpleDateFormat("dd MMMM yyyy")));
            }
        });

        tvAcaraInputDateEnd.setText(DateTimeUtil.dateToString(DateTimeUtil.getCurrentDate(), new SimpleDateFormat("dd MMMM yyyy")));

    }

    private void initTimePicker(String HH, String mm) {
        initTimePickerStart(HH, mm);
        initTimePickerEnd(HH, mm);

    }

    private void initTimePickerStart(String HH, String mm) {
        whpAlarmInputJamStartfvbi.setIsAmPm(false);
        whpAlarmInputJamStartfvbi.setDefault(HH);
        whpAlarmInputJamStartfvbi.setHourChangedListener(new WheelHourPicker.OnHourChangedListener() {
            @Override
            public void onHourChanged(WheelHourPicker picker, int hour) {
                currentHourStart = hour;

                String s = String.valueOf(hour);
                if (s.length() > 1) {
                    currentHourStringStart = s;
                } else {
                    currentHourStringStart = "0" + s;
                }
                Log.d("Lihat", "onHourChanged AlarmActivity : " + currentHourStringStart);

                tvAcaraInputTimeStart.setText(currentHourStringStart + ":" + currentMinuteStringStart);
            }
        });

        whpAlarmInputMenitStartfvbi.setStepMinutes(1);
        whpAlarmInputMenitStartfvbi.setOnMinuteChangedListener(new WheelMinutePicker.OnMinuteChangedListener() {
            @Override
            public void onMinuteChanged(WheelMinutePicker picker, int minutes) {
                currentMinuteStart = minutes;

                String s = String.valueOf(minutes);
                if (s.length() > 1) {
                    currentMinuteStringStart = s;
                } else {
                    currentMinuteStringStart = "0" + s;
                }
                Log.d("Lihat", "onMinuteChanged AlarmActivity : " + currentMinuteStringStart);

                tvAcaraInputTimeStart.setText(currentHourStringStart + ":" + currentMinuteStringStart);
            }
        });

        currentHourStart = Integer.parseInt(HH);
        String s = HH;
        if (s.length() > 1) {
            currentHourStringStart = s;
        } else {
            currentHourStringStart = "0" + s;
        }

        currentMinuteStart = Integer.parseInt(mm);
        String s1 = mm;
        if (s1.length() > 1) {
            currentMinuteStringStart = s1;
        } else {
            currentMinuteStringStart = "0" + s1;
        }

        tvAcaraInputTimeStart.setText(currentHourStringStart + ":" + currentMinuteStringStart);
    }

    private void initTimePickerEnd(String HH, String mm) {
        whpAlarmInputJamEndfvbi.setIsAmPm(false);
        whpAlarmInputJamEndfvbi.setDefault(HH);
        whpAlarmInputJamEndfvbi.setHourChangedListener(new WheelHourPicker.OnHourChangedListener() {
            @Override
            public void onHourChanged(WheelHourPicker picker, int hour) {
                currentHourEnd = hour;

                String s = String.valueOf(hour);
                if (s.length() > 1) {
                    currentHourStringEnd = s;
                } else {
                    currentHourStringEnd = "0" + s;
                }
                Log.d("Lihat", "onHourChanged AlarmActivity : " + currentHourStringEnd);

                tvAcaraInputTimeEnd.setText(currentHourStringEnd + ":" + currentMinuteStringEnd);
            }
        });

        whpAlarmInputMenitEndfvbi.setStepMinutes(1);
        whpAlarmInputMenitEndfvbi.setOnMinuteChangedListener(new WheelMinutePicker.OnMinuteChangedListener() {
            @Override
            public void onMinuteChanged(WheelMinutePicker picker, int minutes) {
                currentMinuteEnd = minutes;

                String s = String.valueOf(minutes);
                if (s.length() > 1) {
                    currentMinuteStringEnd = s;
                } else {
                    currentMinuteStringEnd = "0" + s;
                }
                Log.d("Lihat", "onMinuteChanged AlarmActivity : " + currentMinuteStringEnd);

                tvAcaraInputTimeEnd.setText(currentHourStringEnd + ":" + currentMinuteStringEnd);
            }
        });

        currentHourEnd = Integer.parseInt(HH);
        String s = HH;
        if (s.length() > 1) {
            currentHourStringEnd = s;
        } else {
            currentHourStringEnd = "0" + s;
        }

        currentMinuteEnd = Integer.parseInt(mm);
        String s1 = mm;
        if (s1.length() > 1) {
            currentMinuteStringEnd = s1;
        } else {
            currentMinuteStringEnd = "0" + s1;
        }

        tvAcaraInputTimeEnd.setText(currentHourStringEnd + ":" + currentMinuteStringEnd);
    }

    private void initReminder() {

        //Spinner
        List<String> pengingat = new ArrayList<String>();
        pengingat.add("Pilih waktu pengingat");
        pengingat.add("5 Menit Sebelum");
        pengingat.add("10 Menit Sebelum");
        pengingat.add("15 Menit Sebelum");
        pengingat.add("20 Menit Sebelum");
        ArrayAdapter<String> dataAdapterpengingat = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pengingat) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
                //return ;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    SystemUtil.changeColText(R.color.gray_light, tv);
                    SystemUtil.changeColBackground(R.color.pink, tv);
                } else {
                    SystemUtil.changeColText(R.color.white, tv);
                    SystemUtil.changeColBackground(R.color.pink, tv);
                }
                return view;
            }
        };
        dataAdapterpengingat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAcaraInputMenitPengingatfvbi.setAdapter(dataAdapterpengingat);
        spAcaraInputMenitPengingatfvbi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    reminderTime = 5;
                } else if (position == 2) {
                    reminderTime = 10;
                } else if (position == 3) {
                    reminderTime = 15;
                } else if (position == 4) {
                    reminderTime = 20;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setAcara() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQ_CODE_MAPS) {
            locationName = data.getStringExtra(ISeasonConfig.INTENT_PARAM_MAP_NAME);
            locationLat = data.getDoubleExtra(ISeasonConfig.INTENT_PARAM_MAP_LAT, 0);
            LocationLng = data.getDoubleExtra(ISeasonConfig.INTENT_PARAM_MAP_LONG, 0);

            Log.d("Lihat", "onActivityResult AcaraActivity : " + locationName);
            Log.d("Lihat", "onActivityResult AcaraActivity : " + locationLat);
            Log.d("Lihat", "onActivityResult AcaraActivity : " + LocationLng);

            tvAcaraInputLokasiNmJlnfvbi.setVisibility(View.VISIBLE);
            tvAcaraInputLokasiNmJlnfvbi.setText(data.getStringExtra(ISeasonConfig.INTENT_PARAM_MAP_NAME));
        } else if (resultCode == Activity.RESULT_OK && requestCode == REQ_CODE_RINGTONE_PICKER) {
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
                ivAcaraInputRingtonefvbi.setVisibility(View.VISIBLE);

            } else {
                ringtonePath = null;
            }
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
            setAcara();
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
        finish();
        overridePendingTransition(R.anim.masuk_dari_atas, R.anim.keluar_ke_bawah);

    }
}