package skyray.waol2000.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import skyray.waol2000.R;
import skyray.waol2000.controller.LogDataProvider;
import skyray.waol2000.datamodel.LogInfo;
import skyray.waol2000.listeners.OnEditListener;
import skyray.waol2000.listeners.OnRemoveListener;

/**
 * Created by aurel on 22/09/14.
 */
public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> implements OnRemoveListener, OnEditListener {

    private List<LogInfo> items;
    private LogDataProvider personDataProvider;
    private Context mContext;

    public LogAdapter(Context context, LogDataProvider personDataProvider) {
        this.mContext = context;
        this.personDataProvider = personDataProvider;
        this.items = personDataProvider.getItems();

        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_list_item, viewGroup, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).hashCode();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.label.setText(items.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onRemove(int position) {
        personDataProvider.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onEdit(final int position) {
//        final EditText edit = new EditText(mContext);
//        edit.setTextColor(Color.BLACK);
//        edit.setText(personDataProvider.getItems().get(position).toString());
//        new AlertDialog.Builder(mContext).setTitle(R.string.edit).setView(edit).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String name = edit.getText().toString();
//                personDataProvider.update(position, name);
//                notifyItemChanged(position);
//            }
//        }).create().show();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView label;
        private OnRemoveListener removeListener;
        private OnEditListener editListener;

        public ViewHolder(View itemView, LogAdapter personAdapter) {
            super(itemView);
            this.label = (TextView) itemView.findViewById(R.id.history_name);
            this.removeListener = personAdapter;
            this.editListener = personAdapter;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }


        @Override
        public void onClick(View v) {
            //removeListener.onRemove(getPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            //editListener.onEdit(getPosition());
            return true;
        }
    }


}
