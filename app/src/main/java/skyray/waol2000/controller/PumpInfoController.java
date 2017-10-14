package skyray.waol2000.controller;

import skyray.waol2000.datamodel.PumpInfo;

/**
 * 泵动作信息管理类
 */

public class PumpInfoController extends  DataControllerBase<PumpInfo> {

    @Override
    protected void InitClass() {
        cc = PumpInfo.class;
    }

    private static PumpInfoController APC = new PumpInfoController();

    public static PumpInfoController getAPC() {
        return APC;
    }

    @Override
    protected void updateItem(PumpInfo exist, PumpInfo av) {
        exist.setPumpName(av.getPumpName());
        exist.setDirection(av.getDirection());
        exist.setImpulseCount(av.getImpulseCount());
    }
}
