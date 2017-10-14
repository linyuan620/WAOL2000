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
import skyray.waol2000.datamodel.PumpInfo;

/**
 * Created by Administrator on 2017/6/21.
 */

public class PumpListAdapter extends BaseAdapter {
    private static final String TAG = "PumpListAdapter";

    private List<PumpInfo> datas = null;

    private Context mContext;

    public PumpListAdapter(Context context, List<PumpInfo> d) {
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
            convertView = mLayoutInflater.inflate(R.layout.pump_list_item, parent, false);
        }
        if (convertView != null) {

            TextView pName = (TextView) convertView.findViewById(R.id.pump_name);
            TextView impulseCount = (TextView) convertView.findViewById(R.id.pump_ImpulseCount);
            TextView direction = (TextView) convertView.findViewById(R.id.pump_Direction);

            if (datas != null && datas.size() > position) {
                PumpInfo hData = datas.get(position);
                if (hData != null) {
                    pName.setText(hData.getPumpName());
                    impulseCount.setText(String.valueOf(hData.getImpulseCount()));
                    String direc = mContext.getResources().getString(R.string.pumpMoveOppositeDirection);
                    if (hData.getDirection() == 0)//正向
                    {
                        direc = mContext.getResources().getString(R.string.pumpMoveForwardDirection);
                    }
                    direction.setText(direc);
                }
            }

            if (position == selectItem) {
                convertView.setBackground(mContext.getResources().getDrawable(R.drawable.list_item_selected_bg));
                pName.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
                impulseCount.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
                direction.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
                pName.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
                impulseCount.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
                direction.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
            }
        }
        return convertView;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    private int selectItem = -1;
}
