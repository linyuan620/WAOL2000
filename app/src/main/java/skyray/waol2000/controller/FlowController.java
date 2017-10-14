package skyray.waol2000.controller;

import skyray.waol2000.datamodel.FlowInfo;

/**
 * 泵动作信息管理类
 */

public class FlowController extends DataControllerBase<FlowInfo> {

    @Override
    protected void InitClass() {
        cc=FlowInfo.class;
    }

    private static FlowController ASC = new FlowController();

    public static FlowController getASC() {
        return ASC;
    }

    @Override
    protected void updateItem(FlowInfo exist, FlowInfo av) {
        exist.setFlowTypeV(av.getFlowTypeV());
    }
}
