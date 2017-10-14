package skyray.waol2000.controller;

import android.content.Context;

import skyray.waol2000.R;
import skyray.waol2000.datamodel.HeightType;

/**
 * Created by PengJianbo on 2017/9/4.
 */

public class InjectionInfoStrController {
    public static String getActionTypeStr(Context context, HeightType heightType) {
        String text = "";
        try {
            text = context.getResources().getStringArray(R.array.heightTypes)[heightType.value()];
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return text;
    }
}
