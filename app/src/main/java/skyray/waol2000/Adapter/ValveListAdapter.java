package skyray.waol2000.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import skyray.waol2000.R;
import skyray.waol2000.datamodel.ValveInfo;

/**
 * Created by Administrator on 2017/6/21.
 */

public class ValveListAdapter extends BaseAdapter {
    private static final String TAG = "ValveListAdapter";

    private List<ValveInfo> datas = null;

    private Context mContext;

    public ValveListAdapter(Context context, List<ValveInfo> d) {
        datas = d;
        mContext = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        if (datas != null) {
            return datas.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
            convertView = mLayoutInflater.inflate(R.layout.valve_list_item, parent, false);
        }
        if (convertView != null) {
            TextView codeTextView = (TextView) convertView.findViewById(R.id.valve_name);
            TextView symbolTextView = (TextView) convertView.findViewById(R.id.valve_command_index);

            if (datas != null && datas.size() > position) {
                ValveInfo hData = datas.get(position);
                if (hData != null) {
                    codeTextView.setText(hData.getValveName());
                    symbolTextView.setText(String.valueOf(hData.getCmdIndex()));
                }
            }

            if (position == selectItem) {
                convertView.setBackground(mContext.getResources().getDrawable(R.drawable.list_item_selected_bg));
                codeTextView.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
                symbolTextView.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
                codeTextView.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
                symbolTextView.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
            }
        }
        return convertView;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    private int selectItem = -1;
}
