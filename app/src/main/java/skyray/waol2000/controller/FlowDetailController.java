package skyray.waol2000.controller;

import skyray.waol2000.datamodel.FlowDetail;

/**
 * 泵动作信息管理类
 */

public class FlowDetailController extends DataControllerBase<FlowDetail> {

    @Override
    protected void InitClass() {
        cc = FlowDetail.class;
    }

    private static FlowDetailController ASC = new FlowDetailController();

    public static FlowDetailController getASC() {
        return ASC;
    }

    @Override
    protected void updateItem(FlowDetail exist, FlowDetail av) {
        exist.setOrderValue(av.getOrderValue());
        exist.setFlowID(av.getFlowID());
        exist.setActionBundleID(av.getActionBundleID());
    }
}
