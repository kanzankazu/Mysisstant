package id.co.halloarif.catatanku.view;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.widget.WheelDayPicker;
import com.github.florent37.singledateandtimepicker.widget.WheelHourPicker;
import com.github.florent37.singledateandtimepicker.widget.WheelMinutePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.support.util.DateTimeUtil;

public class AcaraActivity extends AppCompatActivity {

    private NestedScrollView nsvAcaraInputfvbi;
    private EditText etAcaraInputJudulfvbi;
    private WheelDayPicker whpAlarmInputTanggalMulaifvbi;
    private WheelHourPicker whpAlarmInputJamMulaifvbi;
    private WheelMinutePicker whpAlarmInputMenitMulaifvbi;
    private WheelDayPicker whpAlarmInputTanggalSelesaifvbi;
    private WheelHourPicker whpAlarmInputJamSelesaifvbi;
    private WheelMinutePicker whpAlarmInputMenitSelesaifvbi;
    private LinearLayout llAcaraInputLokasifvbi;
    private TextView tvAcaraInputLokasifvbi;
    private LinearLayout llAcaraInputTemanfvbi;
    private TextView tvAcaraInputTemanfvbi;
    private Spinner spAcaraInputMenitPengingatfvbi;
    private ImageButton ibAcaraInputFakeCallfvbi;
    private ImageButton ibAcaraInputPopUpfvbi;
    private ImageButton ibAcaraInputRingtonefvbi;
    private CheckBox cbAcaraInputEvaluasifvbi;
    private Button bAcaraInputBuatfvbi;
    private int currentHourStart;
    private int currentHourEnd;
    private int currentMinuteStart;
    private int currentMinuteEnd;
    private SingleDateAndTimePicker whpAlarmInputTanggal1Mulaifvbi;
    private Date firstDateOfMonth;
    private Date endDateOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acara_input);

        initComponent();
        initParam();
        initSession();
        initContent();
        initListener();

    }

    private void initComponent() {
        nsvAcaraInputfvbi = (NestedScrollView) findViewById(R.id.nsvAcaraInput);
        etAcaraInputJudulfvbi = (EditText) findViewById(R.id.etAcaraInputJudul);

        whpAlarmInputTanggal1Mulaifvbi = (SingleDateAndTimePicker) findViewById(R.id.whpAlarmInputTanggal1Mulai);
        whpAlarmInputTanggalMulaifvbi = (WheelDayPicker) findViewById(R.id.whpAlarmInputTanggalMulai);
        whpAlarmInputJamMulaifvbi = (WheelHourPicker) findViewById(R.id.whpAlarmInputJamMulai);
        whpAlarmInputMenitMulaifvbi = (WheelMinutePicker) findViewById(R.id.whpAlarmInputMenitMulai);
        whpAlarmInputTanggalSelesaifvbi = (WheelDayPicker) findViewById(R.id.whpAlarmInputTanggalSelesai);
        whpAlarmInputJamSelesaifvbi = (WheelHourPicker) findViewById(R.id.whpAlarmInputJamSelesai);
        whpAlarmInputMenitSelesaifvbi = (WheelMinutePicker) findViewById(R.id.whpAlarmInputMenitSelesai);

        llAcaraInputLokasifvbi = (LinearLayout) findViewById(R.id.llAcaraInputLokasi);
        tvAcaraInputLokasifvbi = (TextView) findViewById(R.id.tvAcaraInputLokasi);
        llAcaraInputTemanfvbi = (LinearLayout) findViewById(R.id.llAcaraInputTeman);
        tvAcaraInputTemanfvbi = (TextView) findViewById(R.id.tvAcaraInputTeman);
        spAcaraInputMenitPengingatfvbi = (Spinner) findViewById(R.id.spAcaraInputMenitPengingat);
        ibAcaraInputFakeCallfvbi = (ImageButton) findViewById(R.id.ibAcaraInputFakeCall);
        ibAcaraInputPopUpfvbi = (ImageButton) findViewById(R.id.ibAcaraInputPopUp);
        ibAcaraInputRingtonefvbi = (ImageButton) findViewById(R.id.ibAcaraInputRingtone);
        cbAcaraInputEvaluasifvbi = (CheckBox) findViewById(R.id.cbAcaraInputEvaluasi);
        bAcaraInputBuatfvbi = (Button) findViewById(R.id.bAcaraInputBuat);
    }

    private void initParam() {

    }

    private void initSession() {

    }

    private void initContent() {
        firstDateOfMonth = DateTimeUtil.getStart(DateTimeUtil.stringToDate("01/01/" + DateTimeUtil.getYearCurrent(), new SimpleDateFormat("dd/MM/yyyy")));
//        endDateOfMonth = DateTimeUtil.getEndDateOfMonth(DateTimeUtil.stringToDate("01/12/" + DateTimeUtil.getYearCurrent() + " 23:59:59", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")));
//        firstDateOfMonth = DateTimeUtil.getCurrentDate();
        endDateOfMonth = DateTimeUtil.addMonth(DateTimeUtil.getCurrentDate(), 6);
        int dayBetween2Date = DateTimeUtil.getDayBetween2Date(firstDateOfMonth, endDateOfMonth);
        List<Date> dates = DateTimeUtil.getDates(firstDateOfMonth, endDateOfMonth);
        int posDateNow = DateTimeUtil.getPosDateInListDate(DateTimeUtil.getDates(firstDateOfMonth, endDateOfMonth), DateTimeUtil.getCurrentDate());
        Date dateNow = DateTimeUtil.getDates(firstDateOfMonth, endDateOfMonth).get(posDateNow);

        String AmPm = DateTimeUtil.dateToString(DateTimeUtil.getCurrentDate(), new SimpleDateFormat("a"));
        String hh = DateTimeUtil.dateToString(DateTimeUtil.getCurrentDate(), new SimpleDateFormat("h"));
        String mm = DateTimeUtil.dateToString(DateTimeUtil.getCurrentDate(), new SimpleDateFormat("m"));

        initTimePicker();
    }

    private void initTimePicker() {
        String HH = DateTimeUtil.dateToString(DateTimeUtil.getCurrentDate(), new SimpleDateFormat("HH"));
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");

        whpAlarmInputTanggal1Mulaifvbi.setIsAmPm(false);
        whpAlarmInputTanggal1Mulaifvbi.setDayFormatter(format);
        whpAlarmInputTanggal1Mulaifvbi.setDefaultDate(DateTimeUtil.getCurrentDate());
        whpAlarmInputTanggal1Mulaifvbi.setVisibleItemCount(7);
        whpAlarmInputTanggal1Mulaifvbi.addOnDateChangedListener(new SingleDateAndTimePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(String displayed, Date date) {
                Log.d("Lihat", "onDateChanged AcaraActivity : " + displayed);
                Log.d("Lihat", "onDateChanged AcaraActivity : " + date);
            }
        });
        whpAlarmInputTanggal1Mulaifvbi.setStepMinutes(1);

        whpAlarmInputTanggalMulaifvbi.setDayFormatter(format);
        whpAlarmInputTanggalMulaifvbi.
        whpAlarmInputTanggalMulaifvbi.setTodayText("Hari ini");
        whpAlarmInputTanggalMulaifvbi.setOnDaySelectedListener(new WheelDayPicker.OnDaySelectedListener() {
            @Override
            public void onDaySelected(WheelDayPicker picker, int position, String name, Date date) {
                Log.d("Lihat", "onDaySelected AcaraActivity : " + position);
                Log.d("Lihat", "onDaySelected AcaraActivity : " + name);
                Log.d("Lihat", "onDaySelected AcaraActivity : " + date);
            }
        });
        whpAlarmInputTanggalSelesaifvbi.setDayFormatter(format);
        whpAlarmInputTanggalSelesaifvbi.setTodayText("Hari ini");
        whpAlarmInputTanggalSelesaifvbi.setOnDaySelectedListener(new WheelDayPicker.OnDaySelectedListener() {
            @Override
            public void onDaySelected(WheelDayPicker picker, int position, String name, Date date) {
                Log.d("Lihat", "onDaySelected AcaraActivity : " + position);
                Log.d("Lihat", "onDaySelected AcaraActivity : " + name);
                Log.d("Lihat", "onDaySelected AcaraActivity : " + date);
            }
        });

        whpAlarmInputJamMulaifvbi.setIsAmPm(false);
        whpAlarmInputJamMulaifvbi.setDefault(HH);
        whpAlarmInputJamMulaifvbi.setHourChangedListener(new WheelHourPicker.OnHourChangedListener() {
            @Override
            public void onHourChanged(WheelHourPicker picker, int hour) {
                currentHourStart = hour;
            }
        });
        whpAlarmInputJamSelesaifvbi.setIsAmPm(false);
        whpAlarmInputJamSelesaifvbi.setDefault(HH);
        whpAlarmInputJamSelesaifvbi.setHourChangedListener(new WheelHourPicker.OnHourChangedListener() {
            @Override
            public void onHourChanged(WheelHourPicker picker, int hour) {
                currentHourEnd = hour;
            }
        });

        whpAlarmInputMenitMulaifvbi.setStepMinutes(1);
        whpAlarmInputMenitMulaifvbi.setOnMinuteChangedListener(new WheelMinutePicker.OnMinuteChangedListener() {
            @Override
            public void onMinuteChanged(WheelMinutePicker picker, int minutes) {
                currentMinuteStart = minutes;
            }
        });
        whpAlarmInputMenitSelesaifvbi.setStepMinutes(1);
        whpAlarmInputMenitSelesaifvbi.setOnMinuteChangedListener(new WheelMinutePicker.OnMinuteChangedListener() {
            @Override
            public void onMinuteChanged(WheelMinutePicker picker, int minutes) {
                currentMinuteEnd = minutes;
            }
        });
    }

    private void initListener() {

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.masuk_dari_atas, R.anim.keluar_ke_bawah);
    }
}