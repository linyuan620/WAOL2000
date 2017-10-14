package skyray.waol2000.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eowise.recyclerview.stickyheaders.StickyHeadersAdapter;

import java.util.List;

import skyray.waol2000.R;
import skyray.waol2000.datamodel.LogInfo;

/**
 * Created by aurel on 24/09/14.
 */
public class LogBigramHeaderAdapter implements StickyHeadersAdapter<LogBigramHeaderAdapter.ViewHolder> {

    private List<LogInfo> items;

    public LogBigramHeaderAdapter(List<LogInfo> items) {
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
