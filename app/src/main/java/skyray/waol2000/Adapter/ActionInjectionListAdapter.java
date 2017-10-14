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
import skyray.waol2000.datamodel.ActionInjection;

/**
 * 阀动作列表适配器
 */

public class ActionInjectionListAdapter extends BaseAdapter {
    private static final String TAG = "ActionInjectionListAdapter";

    private List<ActionInjection> datas = null;

    private Context mContext;

    public ActionInjectionListAdapter(Context context, List<ActionInjection> d) {
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
            convertView = mLayoutInflater.inflate(R.layout.action_injection_list_item, parent, false);
        }
        if (convertView != null) {
            TextView actionName = (TextView) convertView.findViewById(R.id.action_name);
            TextView Injectionname = (TextView) convertView.findViewById(R.id.action_injectioninfo_name);

            if (datas != null && datas.size() > position) {
                ActionInjection hData = datas.get(position);
                if (hData != null) {
                    actionName.setText(hData.getActionName());
                    if(hData.getInjectionValue()!=null) {
                        Injectionname.setText(hData.getInjectionValue().getInjectionName());
                    }else {
                        Injectionname.setText("系统进样");
                    }
                }
            }

            if (position == selectItem) {
                convertView.setBackground(mContext.getResources().getDrawable(R.drawable.list_item_selected_bg));
                actionName.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
                Injectionname.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
                actionName.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
                Injectionname.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
            }
        }
        return convertView;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    private int selectItem = -1;
}
