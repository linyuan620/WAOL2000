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
import skyray.waol2000.datamodel.ActionValve;
import skyray.waol2000.datamodel.ValveSwitch;

/**
 * 阀动作列表适配器
 */

public class ActionValveListAdapter extends BaseAdapter {
    private static final String TAG = "ActionValveListAdapter";

    private List<ActionValve> datas = null;

    private Context mContext;

    public ActionValveListAdapter(Context context, List<ActionValve> d) {
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
            convertView = mLayoutInflater.inflate(R.layout.action_valve_list_item, parent, false);
        }
        if (convertView != null) {
            TextView actionName = (TextView) convertView.findViewById(R.id.action_name);
            TextView valvename = (TextView) convertView.findViewById(R.id.action_valve_name);
            TextView status = (TextView) convertView.findViewById(R.id.action_switch);

            if (datas != null && datas.size() > position) {
                ActionValve hData = datas.get(position);
                if (hData != null) {
                    actionName.setText(hData.getActionName());
                    valvename.setText(hData.getValveInfoV().toString());

                    String text = mContext.getString(R.string.switchOn);
                    if (hData.getSwitchValue() == ValveSwitch.Close) {
                        text = mContext.getString(R.string.switchOff);
                    }
                    status.setText(text);
                }
            }

            if (position == selectItem) {
                convertView.setBackground(mContext.getResources().getDrawable(R.drawable.list_item_selected_bg));
                actionName.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
                valvename.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
                status.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
                actionName.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
                valvename.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
                status.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
            }
        }
        return convertView;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    private int selectItem = -1;
}
