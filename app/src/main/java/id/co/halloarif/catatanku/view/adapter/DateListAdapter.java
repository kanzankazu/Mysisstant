package id.co.halloarif.catatanku.view.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.support.util.DateTimeUtil;

public class DateListAdapter extends RecyclerView.Adapter<DateListAdapter.ViewHolder> {
    private final Activity activity;
    private final List<Date> dates;
    private final Date dateNow;
    private final int posDateNow;
    private DateListListener mListener;

    private int newPosSelect = -1;
    private int lastPosSelect = -1;

    public DateListAdapter(Activity activity, List<Date> dates, Date dateNow, int posDateNow, DateListListener dateListListener) {
        this.activity = activity;
        this.dates = dates;
        this.dateNow = dateNow;
        this.posDateNow = posDateNow;
        this.mListener = dateListListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_date, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Date date = dates.get(position);

        holder.tvAcaraDateListTglfvbi.setText(DateTimeUtil.dateToString(date, new SimpleDateFormat("dd")));
        holder.tvAcaraDateListMnthfvbi.setText(DateTimeUtil.dateToString(date, new SimpleDateFormat("MMM yyyy")));

        holder.cvAcaraDateListfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onDateSelect(position);

                newPosSelect = position;
                notifyDataSetChanged();
            }
        });

        if (posDateNow == position) {
            if (newPosSelect == position) {
                holder.tvAcaraDateListTglfvbi.setTextColor(activity.getResources().getColor(R.color.colorAccent));
                holder.tvAcaraDateListMnthfvbi.setTextColor(activity.getResources().getColor(R.color.colorAccent));
                holder.cvAcaraDateListfvbi.setCardBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
            } else {
                holder.tvAcaraDateListTglfvbi.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvAcaraDateListMnthfvbi.setTextColor(activity.getResources().getColor(R.color.white));
                holder.cvAcaraDateListfvbi.setCardBackgroundColor(activity.getResources().getColor(R.color.colorAccent));
            }
        } else if (newPosSelect == position) {
            holder.tvAcaraDateListTglfvbi.setTextColor(activity.getResources().getColor(R.color.white));
            holder.tvAcaraDateListMnthfvbi.setTextColor(activity.getResources().getColor(R.color.white));
            holder.cvAcaraDateListfvbi.setCardBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.tvAcaraDateListTglfvbi.setTextColor(activity.getResources().getColor(R.color.black));
            holder.tvAcaraDateListMnthfvbi.setTextColor(activity.getResources().getColor(R.color.black));
            holder.cvAcaraDateListfvbi.setCardBackgroundColor(activity.getResources().getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvAcaraDateListTglfvbi;
        private final TextView tvAcaraDateListMnthfvbi;
        private final CardView cvAcaraDateListfvbi;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAcaraDateListTglfvbi = (TextView) itemView.findViewById(R.id.tvAcaraDateListTgl);
            tvAcaraDateListMnthfvbi = (TextView) itemView.findViewById(R.id.tvAcaraDateListMnth);
            cvAcaraDateListfvbi = (CardView) itemView.findViewById(R.id.cvAcaraDateList);
        }
    }

    public interface DateListListener {
        void onDateSelect(int position);
    }
}
