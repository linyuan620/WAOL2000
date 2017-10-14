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

import static com.orm.SugarRecord.findById;

/**
 * AD采样动作列表适配器
 */
public class ActionBundleListAdapter extends BaseAdapter {
    private static final String TAG = "ActionBundleListAdapter";

    private List<ActionBundle> datas = null;

    private Context mContext;

    public ActionBundleListAdapter(Context context, List<ActionBundle> d) {
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
            convertView = mLayoutInflater.inflate(R.layout.actionbundle_list_item, parent, false);
        }
        if (convertView != null) {

            TextView bundleName = (TextView) convertView.findViewById(R.id.action_bundle_name);

            if (datas != null && datas.size() > position) {
                ActionBundle hData = datas.get(position);
                if (hData != null) {
                    bundleName.setText(hData.getBundleName());
                }
            }

            if (position == selectItem) {
                convertView.setBackground(mContext.getResources().getDrawable(R.drawable.list_item_selected_bg));
                bundleName.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Selected));
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
                bundleName.setTextColor(mContext.getResources().getColor(R.color.color_List_Item_Text_Normal));
            }
        }
        return convertView;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    private int selectItem = -1;
}
