package id.co.halloarif.catatanku.view.activity.Main.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rm.rmswitch.RMSwitch;

import java.util.ArrayList;
import java.util.List;

import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.model.AlarmModel;

public class AlarmSummaryRVAdapter extends RecyclerView.Adapter<AlarmSummaryRVAdapter.ViewHolder> {

    private List<AlarmModel> models = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_alarm, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AlarmModel model = models.get(position);
        holder.tvAlarmListHourfvbi.setText(model.getAlarm_hour() + ":" + model.getAlarm_minute());
        holder.tvAlarmListTitlefvbi.setText(model.getAlarm_title());
        holder.rmsAlarmListActivefvbi.setChecked(model.getAlarm_is_Active() == 0 ? false : true);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    interface AlarmSummaryRVListener {
        void test();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvAlarmListHourfvbi;
        private final TextView tvAlarmListTitlefvbi;
        private final RMSwitch rmsAlarmListActivefvbi;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAlarmListHourfvbi = (TextView) itemView.findViewById(R.id.tvAlarmListHour);
            tvAlarmListTitlefvbi = (TextView) itemView.findViewById(R.id.tvAlarmListTitle);
            rmsAlarmListActivefvbi = (RMSwitch) itemView.findViewById(R.id.rmsAlarmListActive);
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
}
