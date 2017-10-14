package skyray.waol2000.controller;

import android.content.Context;

import skyray.waol2000.R;
import skyray.waol2000.datamodel.FlowType;

/**
 * Created by Administrator on 2017/6/26.
 */

public class FlowTypeStrController {
    public static String getActionTypeStr(Context context,FlowType flowType) {
        String text = "";
        try {
            text = context.getResources().getStringArray(R.array.flowTypes)[flowType.value()];
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return text;
    }
}
