package skyray.waol2000.controller;

import android.content.Context;

import skyray.waol2000.R;
import skyray.waol2000.datamodel.ActionType;

/**
 * Created by Administrator on 2017/6/26.
 */

public class ActionTypeStrController {
    public static String getActionTypeStr(Context context,ActionType actionType) {
        String text = "";
        try {
            text = context.getResources().getStringArray(R.array.actionTypes)[actionType.value()];
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return text;
    }
}
