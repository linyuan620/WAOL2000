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
import skyray.waol2000.datamodel.ActionBundle;
import skyray.waol2000.datamodel.FlowDetail;
import skyray.waol2000.datamodel.FlowInfo;

/**
 * AD采样动作列表适配器
 */

public class FlowDetailListAdapter extends BaseAdapter {
    private static final String TAG = "FlowDetailListAdapter";

    private List<FlowDetail> datas = null;

    private Context mContext;

    public FlowDetailListAdapter(Context context, List<FlowDetail> d) {
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
            convertView = mLayoutInflater.inflate(R.layout.flowdetail_list_item, parent, false);
        }
        if (convertView != null) {

            TextView tvFlowName = (TextView) convertView.findViewById(R.id.flow_name);
            TextView tvActionBundleName = (TextView) convertView.findViewById(R.id.actionbundle_name);
            TextView tvOrder = (TextView) convertView.findViewById(R.id.excute_order);

            if (datas != null && datas.size() > position) {
                FlowDetail hData = datas.get(position);
                if (hData != null) {
                    FlowInfo flowInfo = FlowInfo.findById(FlowInfo.class, hData.getFlowID());
                    if (flowInfo != null) {
                        tvFlowName.setText(flowInfo.getFlowName());
                    }
                    ActionBundle actionBundle = ActionBundle.findById(ActionBundle.class, hData.getActionBundleID());
                    if (actionBundle != null) {
                        tvActionBundleName.setText(actionBundle.getBundleName());
                    }
                    tvOrder.setText(String.valueOf(hData.getOrderValue()));
                }
            }

            if (position == selectItem) {
                convertView.setBackground(mContext.getResources().getDrawable(R.drawable.list_item_selected_bg));
                tvFlowName.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
                tvActionBundleName.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
                tvOrder.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
                tvFlowName.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
                tvActionBundleName.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
                tvOrder.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
            }
        }
        return convertView;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    private int selectItem = -1;
}
