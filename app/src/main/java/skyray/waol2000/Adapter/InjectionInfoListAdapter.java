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
import skyray.waol2000.controller.InjectionInfoStrController;
import skyray.waol2000.datamodel.HeightType;
import skyray.waol2000.datamodel.InjectionInfo;

/**
 * Created by Administrator on 2017/6/21.
 */

public class InjectionInfoListAdapter extends BaseAdapter {
    private static final String TAG = "InjectionInfoListAdapter";

    private List<InjectionInfo> datas = null;

    private Context mContext;

    public InjectionInfoListAdapter(Context context, List<InjectionInfo> d) {
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
            convertView = mLayoutInflater.inflate(R.layout.injectioninfo_list_item, parent, false);
        }
        if (convertView != null) {

            TextView pName = (TextView) convertView.findViewById(R.id.injectionInfo_name);
            TextView inputPort = (TextView) convertView.findViewById(R.id.injectionInfo_InputPort);
            TextView outputPort = (TextView) convertView.findViewById(R.id.injectionInfo_OutputPort);
            TextView height = (TextView) convertView.findViewById(R.id.injectionInfo_Height);

            if (datas != null && datas.size() > position) {
                InjectionInfo hData = datas.get(position);
                if (hData != null) {
                    pName.setText(hData.getInjectionName());
                    inputPort.setText(String.valueOf(hData.getInputPort()));
                    outputPort.setText(String.valueOf(hData.getOutputPort()));
                    height.setText(InjectionInfoStrController.getActionTypeStr(mContext, HeightType.valueOf(hData.getHeight())) );
                }
            }

            if (position == selectItem) {
                convertView.setBackground(mContext.getResources().getDrawable(R.drawable.list_item_selected_bg));
                pName.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
                inputPort.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
                outputPort.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
                height.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
                pName.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
                inputPort.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
                outputPort.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
                height.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
            }
        }
        return convertView;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    private int selectItem = -1;
}
