package skyray.waol2000.controller;

import skyray.waol2000.datamodel.ActionWaitTime;

/**
 * 泵动作信息管理类
 */

public class ActionWaitTimeController extends DataControllerBase<ActionWaitTime> {

    @Override
    protected void InitClass() {
        cc=ActionWaitTime.class;
    }

    private static ActionWaitTimeController ASC = new ActionWaitTimeController();

    public static ActionWaitTimeController getASC() {
        return ASC;
    }

    @Override
    protected void updateItem(ActionWaitTime exist, ActionWaitTime av) {
        exist.setActionName(av.getActionName());
        exist.setTimeValue(av.getTimeValue());
    }
}
