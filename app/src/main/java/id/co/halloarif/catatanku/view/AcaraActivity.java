package id.co.halloarif.catatanku.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.rm.rmswitch.RMSwitch;
import com.xw.repo.BubbleSeekBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.support.util.DateTimeUtil;
import id.co.halloarif.catatanku.support.util.SystemUtil;
import id.co.halloarif.catatanku.view.adapter.DateListAdapter;

public class AcaraActivity extends AppCompatActivity {

    private EditText etAcaraInputJudulfvbi;
    private RecyclerView rvAcaraInputDatefvbi;
    private RMSwitch rmsAcaraInputMulaifvbi;
    private BubbleSeekBar bsbAcaraInputMulaiJamfvbi;
    private BubbleSeekBar bsbAcaraInputMulaiMenitfvbi;
    private RMSwitch rmsAcaraInputSelesaifvbi;
    private BubbleSeekBar bsbAcaraInputSelesaiJamfvbi;
    private BubbleSeekBar bsbAcaraInputSelesaiMenitfvbi;
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
    private NestedScrollView nsvAcaraInputfvbi;

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
        rvAcaraInputDatefvbi = (RecyclerView) findViewById(R.id.rvAcaraInputDate);
        rmsAcaraInputMulaifvbi = (RMSwitch) findViewById(R.id.rmsAcaraInputMulai);
        bsbAcaraInputMulaiJamfvbi = (BubbleSeekBar) findViewById(R.id.bsbAcaraInputMulaiJam);
        bsbAcaraInputMulaiMenitfvbi = (BubbleSeekBar) findViewById(R.id.bsbAcaraInputMulaiMenit);
        rmsAcaraInputSelesaifvbi = (RMSwitch) findViewById(R.id.rmsAcaraInputSelesai);
        bsbAcaraInputSelesaiJamfvbi = (BubbleSeekBar) findViewById(R.id.bsbAcaraInputSelesaiJam);
        bsbAcaraInputSelesaiMenitfvbi = (BubbleSeekBar) findViewById(R.id.bsbAcaraInputSelesaiMenit);
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
        //Date firstDateOfMonth = DateTimeUtil.getStart(DateTimeUtil.stringToDate("01/01/" + DateTimeUtil.getYearCurrent(), new SimpleDateFormat("dd/MM/yyyy")));
        //Date endDateOfMonth = DateTimeUtil.getEndDateOfMonth(DateTimeUtil.stringToDate("01/12/" + DateTimeUtil.getYearCurrent() + " 23:59:59", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")));
        Date firstDateOfMonth = DateTimeUtil.getCurrentDate();
        Date endDateOfMonth = DateTimeUtil.addMonth(DateTimeUtil.getCurrentDate(), 6);
        int dayBetween2Date = DateTimeUtil.getDayBetween2Date(firstDateOfMonth, endDateOfMonth);
        List<Date> dates = DateTimeUtil.getDates(firstDateOfMonth, endDateOfMonth);
        int posDateNow = DateTimeUtil.getPosDateInListDate(DateTimeUtil.getDates(firstDateOfMonth, endDateOfMonth), DateTimeUtil.getCurrentDate());
        Date dateNow = DateTimeUtil.getDates(firstDateOfMonth, endDateOfMonth).get(posDateNow);

        String AmPm = DateTimeUtil.dateToString(DateTimeUtil.getCurrentDate(), new SimpleDateFormat("a"));
        String hh = DateTimeUtil.dateToString(DateTimeUtil.getCurrentDate(), new SimpleDateFormat("h"));
        String mm = DateTimeUtil.dateToString(DateTimeUtil.getCurrentDate(), new SimpleDateFormat("m"));

        List<String> pengingats = new ArrayList<String>();
        pengingats.add("Pilih waktu pengingat");
        pengingats.add("15 Menit Sebelum Acara");
        pengingats.add("30 Menit Sebelum Acara");
        pengingats.add("45 Menit Sebelum Acara");
        pengingats.add("60 Menit Sebelum Acara");

        Log.d("Lihat", "onClick ChangeEventActivity : " + firstDateOfMonth);
        Log.d("Lihat", "onClick ChangeEventActivity : " + endDateOfMonth);
        Log.d("Lihat", "onClick ChangeEventActivity : " + DateTimeUtil.getDayBetween2Date(firstDateOfMonth, endDateOfMonth));
        Log.d("Lihat", "onClick ChangeEventActivity : " + DateTimeUtil.getDates(firstDateOfMonth, endDateOfMonth));
        Log.d("Lihat", "onClick ChangeEventActivity : " + posDateNow);
        Log.d("Lihat", "onClick ChangeEventActivity : " + DateTimeUtil.getDates(firstDateOfMonth, endDateOfMonth).get(posDateNow));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager.setStackFromEnd(true);
        DateListAdapter adapter = new DateListAdapter(AcaraActivity.this, dates, dateNow, posDateNow, new DateListAdapter.DateListListener() {
            @Override
            public void onDateSelect(int position) {
                if (DateTimeUtil.isToday(dates.get(position))) {
                    bsbAcaraInputMulaiJamfvbi.setProgress(Float.parseFloat(hh));
                    bsbAcaraInputMulaiMenitfvbi.setProgress(Float.parseFloat(mm));
                    bsbAcaraInputSelesaiJamfvbi.setProgress(Float.parseFloat(hh));
                    bsbAcaraInputSelesaiMenitfvbi.setProgress(Float.parseFloat(mm));
                } else {
                    bsbAcaraInputMulaiJamfvbi.setProgress(Float.parseFloat(hh));
                    bsbAcaraInputMulaiMenitfvbi.setProgress(Float.parseFloat(mm));
                    bsbAcaraInputSelesaiJamfvbi.setProgress(Float.parseFloat(hh));
                    bsbAcaraInputSelesaiMenitfvbi.setProgress(Float.parseFloat(mm));
                }
            }
        });
        rvAcaraInputDatefvbi.setAdapter(adapter);
        rvAcaraInputDatefvbi.setLayoutManager(linearLayoutManager);
        rvAcaraInputDatefvbi.scrollToPosition(posDateNow < 5 ? posDateNow : posDateNow - 2);
        /*SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvAcaraInputDatefvbi);*/
        //adapterCarousel = new AdapterCarousel(mContext, glideRequestManager);
        //adapterCarousel.setViewType(RECYCLER_VIEW_TYPE_NORMAL);
        //adapterCarousel.setCarousel(carousel);
        //this.recyclerView.setAdapter(adapterCarousel);
        //snapHelper.attachToRecyclerView(recyclerView);
        //linearLayoutManager.scrollToPosition(Integer.MAX_VALUE / 2);

        //View view = snapHelper.findSnapView(linearLayoutManager);
        //rvAcaraInputDatefvbi.getChildAdapterPosition(view);

        //Spinner

        if (AmPm.equalsIgnoreCase("AM")) {
            rmsAcaraInputMulaifvbi.setChecked(false);
            rmsAcaraInputSelesaifvbi.setChecked(false);
        } else {
            rmsAcaraInputMulaifvbi.setChecked(true);
            rmsAcaraInputSelesaifvbi.setChecked(true);
        }

        bsbAcaraInputMulaiJamfvbi.setProgress(Float.parseFloat(hh));
        bsbAcaraInputMulaiMenitfvbi.setProgress(Float.parseFloat(mm));
        bsbAcaraInputMulaiJamfvbi.getConfigBuilder().autoAdjustSectionMark();
        bsbAcaraInputSelesaiJamfvbi.setProgress(Float.parseFloat(hh));
        bsbAcaraInputSelesaiMenitfvbi.setProgress(Float.parseFloat(mm));
        bsbAcaraInputSelesaiJamfvbi.getConfigBuilder().autoAdjustSectionMark();

        ArrayAdapter<String> dataAdapterpengingats = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pengingats) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    SystemUtil.changeColText(R.color.gray_light, tv);
                } else {
                    SystemUtil.changeColText(R.color.androidSblue, tv);
                }
                return view;
            }
        };
        dataAdapterpengingats.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAcaraInputMenitPengingatfvbi.setAdapter(dataAdapterpengingats);
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
        nsvAcaraInputfvbi.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                bsbAcaraInputMulaiJamfvbi.correctOffsetWhenContainerOnScrolling();
                bsbAcaraInputMulaiMenitfvbi.correctOffsetWhenContainerOnScrolling();
                bsbAcaraInputSelesaiJamfvbi.correctOffsetWhenContainerOnScrolling();
                bsbAcaraInputSelesaiMenitfvbi.correctOffsetWhenContainerOnScrolling();
            }
        });
    }
}