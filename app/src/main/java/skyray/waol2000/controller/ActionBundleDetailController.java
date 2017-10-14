package skyray.waol2000.controller;

import skyray.waol2000.datamodel.ActionBundleDetail;

/**
 * 泵动作信息管理类
 */

public class ActionBundleDetailController extends DataControllerBase<ActionBundleDetail> {
    @Override
    protected void InitClass() {
        cc = ActionBundleDetail.class;
    }

    private static ActionBundleDetailController ASC = new ActionBundleDetailController();

    public static ActionBundleDetailController getASC() {
        return ASC;
    }

    @Override
    protected void updateItem(ActionBundleDetail exist, ActionBundleDetail av) {
        exist.setActionID(av.getActionID());
        exist.setActionTypeV(av.getActionTypeV());
        exist.setOrderValue(av.getOrderValue());
    }
}
