package id.co.halloarif.catatanku.view.activity.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.support.util.DateTimeUtil;
import id.co.halloarif.catatanku.support.util.SystemUtil;
import id.co.halloarif.catatanku.view.activity.Support.PlacesAutoCompleteActivity;

public class AcaraActivity extends AppCompatActivity {

    private static final int REQ_CODE_MAPS = 1;
    private NestedScrollView nsvAcaraInputfvbi;
    private EditText etAcaraInputJudulfvbi;
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
    private SingleDateAndTimePicker whpAlarmInputTanggal1Selesaifvbi;
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
        whpAlarmInputTanggal1Selesaifvbi = (SingleDateAndTimePicker) findViewById(R.id.whpAlarmInputTanggal1Selesai);

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
        initReminder();

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

        whpAlarmInputTanggal1Selesaifvbi.setIsAmPm(false);
        whpAlarmInputTanggal1Selesaifvbi.setDayFormatter(format);
        whpAlarmInputTanggal1Selesaifvbi.setDefaultDate(DateTimeUtil.getCurrentDate());
        whpAlarmInputTanggal1Selesaifvbi.setVisibleItemCount(7);
        whpAlarmInputTanggal1Selesaifvbi.addOnDateChangedListener(new SingleDateAndTimePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(String displayed, Date date) {
                Log.d("Lihat", "onDateChanged AcaraActivity : " + displayed);
                Log.d("Lihat", "onDateChanged AcaraActivity : " + date);
            }
        });
        whpAlarmInputTanggal1Selesaifvbi.setStepMinutes(1);
    }

    private void initReminder() {
        //Spinner
        List<String> pengingat = new ArrayList<String>();
        //pengingat.add("Dagangan");
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
                } else {
                    SystemUtil.changeColText(R.color.black, tv);
                }
                return view;
            }
        };
        dataAdapterpengingat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAcaraInputMenitPengingatfvbi.setAdapter(dataAdapterpengingat);
        spAcaraInputMenitPengingatfvbi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initListener() {
        llAcaraInputLokasifvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AcaraActivity.this, PlacesAutoCompleteActivity.class);
                startActivityForResult(intent, REQ_CODE_MAPS);
                //finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.masuk_dari_atas, R.anim.keluar_ke_bawah);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQ_CODE_MAPS){

        }
    }
}