package skyray.waol2000.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eowise.recyclerview.stickyheaders.StickyHeadersAdapter;
import skyray.waol2000.R;

import java.util.List;

/**
 * Created by aurel on 23/09/14.
 */
public class HistoryInitialHeaderAdapter implements StickyHeadersAdapter<HistoryInitialHeaderAdapter.ViewHolder> {

    private List<String> items;

    public HistoryInitialHeaderAdapter(List<String> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_letter_header, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder headerViewHolder, int position) {
        headerViewHolder.letter.setText(items.get(position).subSequence(0, 1));
    }

    @Override
    public long getHeaderId(int position) {
        return items.get(position).charAt(0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView letter;

        public ViewHolder(View itemView) {
            super(itemView);
            letter = (TextView) itemView.findViewById(R.id.history_letter);
        }
    }
}
