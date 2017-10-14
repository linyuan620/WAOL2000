package skyray.waol2000.controller;

import skyray.waol2000.datamodel.ActionADSample;

/**
 * 泵动作信息管理类
 */

public class ActionADSampleController extends DataControllerBase<ActionADSample> {
    @Override
    protected void InitClass() {
        cc = ActionADSample.class;
    }

    @Override
    protected void updateItem(ActionADSample exist, ActionADSample av) {
        exist.setActionName(av.getActionName());
        exist.setSampleCount(av.getSampleCount());
        exist.setFinalVoltage(av.isFinalVoltage());
    }

    private static ActionADSampleController ASC = new ActionADSampleController();

    public static ActionADSampleController getASC() {
        return ASC;
    }
}
