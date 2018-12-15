package id.co.halloarif.catatanku.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.co.halloarif.catatanku.R;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder> {
    private static final int VIEW_TYPE_PADDING = 1;
    private static final int VIEW_TYPE_ITEM = 2;

    private Context context;
    private List<String> dateDataList;

    public DateAdapter(Context context, List<String> dateData) {
        this.context = context;
        this.dateDataList = dateData;

    }

    @Override
    public int getItemViewType(int position) {
        if (position < 2 || position > getItemCount() - 3) {
            return VIEW_TYPE_PADDING;
        }
        return VIEW_TYPE_ITEM;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_hhmm_vertical, parent, false);
            return new ViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_padding, parent, false);
            return new ViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            holder.tvDate.setText(dateDataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dateDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDate;
        public View imgSmall;
        public View imgSmall2;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tvListHHMM);
            imgSmall = (View) itemView.findViewById(R.id.vListHHMM);
            imgSmall2 = (View) itemView.findViewById(R.id.vListHHMM2);
        }
    }
}
