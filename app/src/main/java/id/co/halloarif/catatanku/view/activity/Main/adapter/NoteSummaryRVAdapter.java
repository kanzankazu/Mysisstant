package id.co.halloarif.catatanku.view.activity.Main.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.model.AlarmModel;
import id.co.halloarif.catatanku.model.NoteModel;

public class NoteSummaryRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NoteModel> models = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    interface NoteSummaryRVListener {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNoteListTitlefvbi;
        public TextView tvNoteListSubTitlefvbi;
        public TextView tvNoteListDatefvbi;
        public ImageView IVNoteListSharefvbi;
        public LinearLayout llNoteListBackgroundfvbi;
        public LinearLayout llNoteListForegroundfvbi;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNoteListTitlefvbi = (TextView) itemView.findViewById(R.id.tvNoteListTitle);
            tvNoteListSubTitlefvbi = (TextView) itemView.findViewById(R.id.tvNoteListSubTitle);
            tvNoteListDatefvbi = (TextView) itemView.findViewById(R.id.tvNoteListDate);
            IVNoteListSharefvbi = (ImageView) itemView.findViewById(R.id.IVNoteListShare);
            llNoteListBackgroundfvbi = (LinearLayout) itemView.findViewById(R.id.llNoteListBackground);
            llNoteListForegroundfvbi = (LinearLayout) itemView.findViewById(R.id.llNoteListForeground);

        }
    }

    public void setData(List<NoteModel> datas) {
        if (datas.size() > 0) {
            models.clear();
            models = datas;
        } else {
            models = datas;
        }
        notifyDataSetChanged();
    }

    public void replaceData(List<NoteModel> datas) {
        models.clear();
        models.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDatas(List<NoteModel> datas) {
        models.addAll(datas);
        notifyItemRangeInserted(models.size(), datas.size());
    }

    public void addDataFirst(NoteModel data) {
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

    public void restoreData(NoteModel data, int position) {
        models.add(position, data);
        notifyItemInserted(position);
    }
}
