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
import skyray.waol2000.controller.ActionADSampleController;
import skyray.waol2000.controller.ActionInjectionController;
import skyray.waol2000.controller.ActionPumpController;
import skyray.waol2000.controller.ActionTypeStrController;
import skyray.waol2000.controller.ActionValveController;
import skyray.waol2000.controller.ActionWaitTimeController;
import skyray.waol2000.datamodel.ActionBundleDetail;
import skyray.waol2000.datamodel.ActionType;
import skyray.waol2000.datamodel.IActionInfo;

/**
 * AD采样动作列表适配器
 */

public class ActionBundleDetailListAdapter extends BaseAdapter {
    private static final String TAG = "ActionBundleDetailListAdapter";

    private List<ActionBundleDetail> datas = null;

    private Context mContext;

    public ActionBundleDetailListAdapter(Context context, List<ActionBundleDetail> d) {
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

    String getActionNameByType(ActionType actionType, int actionID) {
        String result = "";
        IActionInfo av = null;
        switch (actionType) {
            case Valve:
                av = ActionValveController.getAVC().getItemByID(actionID);
                break;
            case Pump:
                av = ActionPumpController.getAPC().getItemByID(actionID);
                break;
            case ADSample:
                av = ActionADSampleController.getASC().getItemByID(actionID);
                break;
            case WaitTime:
                av = ActionWaitTimeController.getASC().getItemByID(actionID);
                break;
            case Injection:
                av = ActionInjectionController.getAIC().getItemByID(actionID);
                break;
        }
        if (av != null) {
            result = av.toString();
        }

        return result;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
            convertView = mLayoutInflater.inflate(R.layout.actionbundledetail_list_item, parent, false);
        }
        if (convertView != null) {

            TextView bundleName = (TextView) convertView.findViewById(R.id.action_bundle_detal_name);
            TextView actionName = (TextView) convertView.findViewById(R.id.action_bundle_detal_actionname);
            TextView actionType = (TextView) convertView.findViewById(R.id.action_bundle_detal_actiontype);
            TextView order = (TextView)convertView.findViewById(R.id.action_bundle_detal_order);

            if (datas != null && datas.size() > position) {
                ActionBundleDetail hData = datas.get(position);
                if (hData != null) {
                    bundleName.setText(hData.getBundleInfo().getBundleName());
                    actionName.setText(getActionNameByType(hData.getActionTypeV(), hData.getActionID()));
                    actionType.setText(ActionTypeStrController.getActionTypeStr(mContext, hData.getActionTypeV()));
                    order.setText(String.valueOf(hData.getOrderValue()));
                }
            }

            if (position == selectItem) {
                convertView.setBackground(mContext.getResources().getDrawable(R.drawable.list_item_selected_bg));
                bundleName.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
                actionName.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
                actionType.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
                order.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
                bundleName.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
                actionName.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
                actionType.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
                order.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
            }
        }
        return convertView;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    private int selectItem = -1;
}
