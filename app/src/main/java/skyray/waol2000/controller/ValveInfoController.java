package skyray.waol2000.controller;

import skyray.waol2000.datamodel.ValveInfo;

/**
 * 泵动作信息管理类
 */

public class ValveInfoController extends  DataControllerBase<ValveInfo> {

    @Override
    protected void InitClass() {
        cc = ValveInfo.class;
    }

    private static ValveInfoController APC = new ValveInfoController();

    public static ValveInfoController getAPC() {
        return APC;
    }

    @Override
    protected void updateItem(ValveInfo exist, ValveInfo av) {
        exist.setValveName(av.getValveName());
        exist.setCmdIndex(av.getCmdIndex());
    }
}
