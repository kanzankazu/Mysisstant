package id.co.halloarif.catatanku.view.activity.Main.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rm.rmswitch.RMSwitch;

import id.co.halloarif.catatanku.R;

public class AcaraSummaryRVAdapter extends RecyclerView.Adapter<AcaraSummaryRVAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_acara, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.tvAcaraListHourfvbi.setText("");
        holder.tvAcaraListTitlefvbi.setText("");
        holder.tvAcaraListPlacefvbi.setText("");
        holder.tvAcaraListMemberfvbi.setText("");
        holder.rmsAcaraListActivefvbi.addSwitchObserver(new RMSwitch.RMSwitchObserver() {
            @Override
            public void onCheckStateChange(RMSwitch switchView, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    interface AcaraSummaryRVListener {

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvAcaraListHourfvbi;
        private final TextView tvAcaraListTitlefvbi;
        private final TextView tvAcaraListPlacefvbi;
        private final TextView tvAcaraListMemberfvbi;
        private final RMSwitch rmsAcaraListActivefvbi;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAcaraListHourfvbi = (TextView) itemView.findViewById(R.id.tvAcaraListHour);
            tvAcaraListTitlefvbi = (TextView) itemView.findViewById(R.id.tvAcaraListTitle);
            tvAcaraListPlacefvbi = (TextView) itemView.findViewById(R.id.tvAcaraListPlace);
            tvAcaraListMemberfvbi = (TextView) itemView.findViewById(R.id.tvAcaraListMember);
            rmsAcaraListActivefvbi = (RMSwitch) itemView.findViewById(R.id.rmsAcaraListActive);
        }
    }
}
