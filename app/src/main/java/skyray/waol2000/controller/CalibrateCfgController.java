package skyray.waol2000.controller;

import java.util.List;

import skyray.waol2000.datamodel.CalibrateConfig;

/**
 * Created by Administrator on 2017/6/23.
 */

public class CalibrateCfgController {
    private CalibrateConfig CFG = new CalibrateConfig();

    public CalibrateConfig GetCalibrateConfig() {
        return CFG;
    }

    private static CalibrateCfgController _CFGCtrl = null;

    /**
     * 加载配置并获取之
     *
     * @return
     */
    public static CalibrateCfgController getCalibrateConfigController() {
        if (_CFGCtrl == null) {
            _CFGCtrl = new CalibrateCfgController();
            List<CalibrateConfig> allConfigs = CalibrateConfig.listAll(CalibrateConfig.class);
            if (allConfigs != null && allConfigs.size() > 0) {
                _CFGCtrl.CFG = allConfigs.get(0);
            }
            if (_CFGCtrl.CFG == null) {
                _CFGCtrl.CFG = new CalibrateConfig();
            }
        }
        return _CFGCtrl;
    }

    public void saveCalibrateConfig(CalibrateConfig CalibrateConfig) {
        CFG.setFirstLoopCalibrate(CalibrateConfig.isFirstLoopCalibrate());
        CFG.setAutoCalibrate(CalibrateConfig.isAutoCalibrate());
        CFG.setContentOne(CalibrateConfig.getContentOne());
        CFG.setContentTwo(CalibrateConfig.getContentTwo());
        CFG.setCalibrateTime(CalibrateConfig.getCalibrateTime());
        CFG.setCalibrateIntervalTime(CalibrateConfig.getCalibrateIntervalTime());
        CFG.save();
    }
}
