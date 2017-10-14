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
import skyray.waol2000.controller.FlowTypeStrController;
import skyray.waol2000.datamodel.FlowInfo;

/**
 * AD采样动作列表适配器
 */

public class FlowListAdapter extends BaseAdapter {
    private static final String TAG = "FlowInfoListAdapter";

    private List<FlowInfo> datas = null;

    private Context mContext;

    public FlowListAdapter(Context context, List<FlowInfo> d) {
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
            convertView = mLayoutInflater.inflate(R.layout.flow_list_item, parent, false);
        }
        if (convertView != null) {

            TextView flowName = (TextView) convertView.findViewById(R.id.flow_name);
            TextView flowType = (TextView) convertView.findViewById(R.id.flow_type);

            if (datas != null && datas.size() > position) {
                FlowInfo hData = datas.get(position);
                if (hData != null) {
                    flowName.setText(hData.getFlowName());
                    flowType.setText(FlowTypeStrController.getActionTypeStr(mContext, hData.getFlowTypeV()));
                }
            }

            if (position == selectItem) {
                convertView.setBackground(mContext.getResources().getDrawable(R.drawable.list_item_selected_bg));
                flowName.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
                flowType.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
                flowName.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
                flowType.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
            }
        }
        return convertView;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    private int selectItem = -1;
}
