package skyray.waol2000.controller;

import skyray.waol2000.datamodel.ActionPump;

/**
 * 泵动作信息管理类
 */

public class ActionPumpController extends  DataControllerBase<ActionPump> {

    @Override
    protected void InitClass() {
        cc = ActionPump.class;
    }

    private static ActionPumpController APC = new ActionPumpController();

    public static ActionPumpController getAPC() {
        return APC;
    }

    @Override
    protected void updateItem(ActionPump exist, ActionPump av) {
        exist.setActionName(av.getActionName());
        exist.setPumpInfoV(av.getPumpInfoV());
        exist.setSpeed(av.getSpeed());
        exist.setSwitchValue(av.getSwitchValue());
    }
}
