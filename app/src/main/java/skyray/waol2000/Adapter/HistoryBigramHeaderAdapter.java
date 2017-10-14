package skyray.waol2000.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eowise.recyclerview.stickyheaders.StickyHeadersAdapter;
import skyray.waol2000.R;
import skyray.waol2000.datamodel.MeasureData;

import java.util.List;

/**
 * Created by aurel on 24/09/14.
 */
public class HistoryBigramHeaderAdapter implements StickyHeadersAdapter<HistoryBigramHeaderAdapter.ViewHolder> {

    private List<MeasureData> items;

    public HistoryBigramHeaderAdapter(List<MeasureData> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_top_header, parent, false);

        return new ViewHolder(itemView);
    }

    private String getHeader(int position)
    {
        return items.get(position).toString().substring(0,10);
    }

    @Override
    public void onBindViewHolder(ViewHolder headerViewHolder, int position) {
        headerViewHolder.title.setText(getHeader(position));
    }

    @Override
    public long getHeaderId(int position) {
        return getHeader(position).hashCode();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.history_title);
        }
    }
}
