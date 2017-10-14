package skyray.waol2000.controller;

import skyray.waol2000.datamodel.InjectionInfo;

/**
 * 泵动作信息管理类
 */

public class InjectionInfoController extends  DataControllerBase<InjectionInfo> {

    @Override
    protected void InitClass() {
        cc = InjectionInfo.class;
    }

    private static InjectionInfoController APC = new InjectionInfoController();

    public static InjectionInfoController getAPC() {
        return APC;
    }

    @Override
    protected void updateItem(InjectionInfo exist, InjectionInfo av) {
        exist.setInjectionName(av.getInjectionName());
        exist.setInputPort(av.getInputPort());
        exist.setOutputPort(av.getOutputPort());
        exist.setHeight(av.getHeight());
    }
}
