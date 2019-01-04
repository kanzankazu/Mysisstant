package id.co.halloarif.catatanku.view.activity.Support;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import id.co.halloarif.catatanku.R;

class MemberAutoCompleteAdapter extends RecyclerView.Adapter<MemberAutoCompleteAdapter.ViewHolder> {
    private MemberAutoCompleteAdapterListener mlistener;

    public MemberAutoCompleteAdapter(MemberAutoCompleteAdapterListener mlistener) {
        this.mlistener = mlistener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_member_autocomplete, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.tvListMemberAutocompleteAddressfvbi.setText("");
        holder.cvListMemberAutocompleteAddressfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.onClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvListMemberAutocompleteAddressfvbi;
        private final CardView cvListMemberAutocompleteAddressfvbi;
        private final CheckBox cbListMemberAutocompleteAddressfvbi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvListMemberAutocompleteAddressfvbi = (CardView) itemView.findViewById(R.id.cvListMemberAutocompleteAddress);
            cbListMemberAutocompleteAddressfvbi = (CheckBox) itemView.findViewById(R.id.cbListMemberAutocompleteAddress);
            tvListMemberAutocompleteAddressfvbi = (TextView) itemView.findViewById(R.id.tvListMemberAutocompleteAddress);
        }
    }

    public interface MemberAutoCompleteAdapterListener {
        void onClick(int i);
    }
}
