package skyray.waol2000.controller;

import skyray.waol2000.datamodel.ActionValve;

/**
 * Created by Administrator on 2017/6/24.
 */

public class ActionValveController  extends  DataControllerBase<ActionValve> {

    @Override
    protected void InitClass() {
        cc = ActionValve.class;
    }

    private static ActionValveController AVC = new ActionValveController();

    public static ActionValveController getAVC() {
        return AVC;
    }

    @Override
    protected void updateItem(ActionValve exist, ActionValve av) {
        exist.setActionName(av.getActionName());
        exist.setValveInfoV(av.getValveInfoV());
        exist.setSwitchValue(av.getSwitchValue());
    }
}