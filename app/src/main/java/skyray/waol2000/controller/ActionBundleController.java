package skyray.waol2000.controller;

import skyray.waol2000.datamodel.ActionBundle;

/**
 * 泵动作信息管理类
 */

public class ActionBundleController extends DataControllerBase<ActionBundle> {
    @Override
    protected void InitClass() {
        cc = ActionBundle.class;
    }

    private static ActionBundleController ASC = new ActionBundleController();

    public static ActionBundleController getASC() {
        return ASC;
    }

    @Override
    protected void updateItem(ActionBundle exist, ActionBundle av) {
    }
}
