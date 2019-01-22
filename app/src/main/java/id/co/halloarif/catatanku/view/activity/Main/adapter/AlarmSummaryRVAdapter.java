package id.co.halloarif.catatanku.view.activity.Main.adapter;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rm.rmswitch.RMSwitch;

import java.util.ArrayList;
import java.util.List;

import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.database.SQLiteHelper;
import id.co.halloarif.catatanku.model.AlarmModel;

public class AlarmSummaryRVAdapter extends RecyclerView.Adapter<AlarmSummaryRVAdapter.ViewHolder> {

    private final Activity activity;
    private final AlarmSummaryRVListener mListener;
    private List<AlarmModel> models = new ArrayList<>();
    private boolean isOnSwitcher = false;
    private boolean rmsChecked;

    public AlarmSummaryRVAdapter(Activity activity, AlarmSummaryRVListener mListener) {
        this.activity = activity;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_alarm, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AlarmModel model = models.get(position);

        String sHour = model.getAlarm_hour();
        String currentHourString;
        if (sHour.length() > 1) {
            currentHourString = sHour;
        } else {
            currentHourString = "0" + sHour;
        }

        String sMinute = model.getAlarm_minute();
        String currentMinuteString;
        if (sMinute.length() > 1) {
            currentMinuteString = sMinute;
        } else {
            currentMinuteString = "0" + sMinute;
        }

        holder.tvAlarmListHourfvbi.setText(currentHourString + ":" + currentMinuteString);
        holder.tvAlarmListTitlefvbi.setText(model.getAlarm_title());

        if (TextUtils.isEmpty(model.getAlarm_day())) {
            holder.tvAlarmListHourRepeatfvbi.setVisibility(View.INVISIBLE);
        } else {
            holder.tvAlarmListHourRepeatfvbi.setVisibility(View.VISIBLE);
            holder.tvAlarmListHourRepeatfvbi.setText(model.getAlarm_text_day());
        }

        //Log.d("Lihat", "onBindViewHolder AlarmSummaryRVAdapter : " + model.getAlarm_id());
        //Log.d("Lihat", "onBindViewHolder AlarmSummaryRVAdapter : " + model.getAlarm_is_Active());

        if (model.getAlarm_is_Active() == 1) {
            holder.rmsAlarmListActivefvbi.setChecked(true);
        } else {
            holder.rmsAlarmListActivefvbi.setChecked(false);
        }

        //holder.rmsAlarmListActivefvbi.setEnabled(false);
        isOnSwitcher = false;

        new Handler().postDelayed(new Runnable() {
            public void run() {
                //code here
                isOnSwitcher = true;
                //holder.rmsAlarmListActivefvbi.setEnabled(true);
            }
        }, 1000);

        holder.rmsAlarmListActivefvbi.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                if (isOnSwitcher) {
                    mListener.onChange(model, isChecked ? true : false);
                }
            }
        });

        holder.llAlarmListForegroundfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public interface AlarmSummaryRVListener {
        void onClick(AlarmModel model);

        void onChange(AlarmModel model, boolean state);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SQLiteHelper db = new SQLiteHelper(itemView.getContext());
        public TextView tvAlarmListHourfvbi;
        public TextView tvAlarmListHourRepeatfvbi;
        public TextView tvAlarmListTitlefvbi;
        public RMSwitch rmsAlarmListActivefvbi;
        public LinearLayout llAlarmListActivefvbi;
        public LinearLayout llAlarmListForegroundfvbi;
        public LinearLayout llAlarmListBackgroundfvbi;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAlarmListHourfvbi = (TextView) itemView.findViewById(R.id.tvAlarmListHour);
            tvAlarmListHourRepeatfvbi = (TextView) itemView.findViewById(R.id.tvAlarmListHourRepeat);
            tvAlarmListTitlefvbi = (TextView) itemView.findViewById(R.id.tvAlarmListTitle);
            rmsAlarmListActivefvbi = (RMSwitch) itemView.findViewById(R.id.rmsAlarmListActive);
            llAlarmListActivefvbi = (LinearLayout) itemView.findViewById(R.id.llAlarmListActive);
            llAlarmListForegroundfvbi = (LinearLayout) itemView.findViewById(R.id.llAlarmListForeground);
            llAlarmListBackgroundfvbi = (LinearLayout) itemView.findViewById(R.id.llAlarmListBackground);
        }
    }

    public void setData(List<AlarmModel> datas) {
        if (datas.size() > 0) {
            models.clear();
            models = datas;
        } else {
            models = datas;
        }
        notifyDataSetChanged();
    }

    public void replaceData(List<AlarmModel> datas) {
        models.clear();
        models.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDatas(List<AlarmModel> datas) {
        models.addAll(datas);
        notifyItemRangeInserted(models.size(), datas.size());
    }

    public void addDataFirst(AlarmModel data) {
        int position = 0;
        models.add(position, data);
        notifyItemInserted(position);
    }

    public void removeAt(int position) {
        models.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, models.size());
    }

    public void removeDataFirst() {
        int position = 0;
        models.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, models.size());
    }

    public void restoreData(AlarmModel data, int position) {
        models.add(position, data);
        notifyItemInserted(position);
    }
}
