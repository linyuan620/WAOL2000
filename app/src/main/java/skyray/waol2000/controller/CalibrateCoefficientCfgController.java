package skyray.waol2000.controller;

import java.util.List;

import skyray.waol2000.datamodel.CalibrateCoefficient;

/**
 * 连接配置管理类
 * Created by Administrator on 2017/6/10.
 */

public class CalibrateCoefficientCfgController {
    private CalibrateCoefficient CFG = new CalibrateCoefficient();

    public CalibrateCoefficient GetCalibrateCoefficient() {
        return CFG;
    }

    private static CalibrateCoefficientCfgController _CFGCtrl = null;

    /**
     * 加载配置并获取之
     *
     * @return
     */
    public static CalibrateCoefficientCfgController GetCalibrateCoefficientController() {
        if (_CFGCtrl == null) {
            _CFGCtrl = new CalibrateCoefficientCfgController();
            List<CalibrateCoefficient> allConfigs = CalibrateCoefficient.listAll(CalibrateCoefficient.class);
            if (allConfigs != null && allConfigs.size() > 0) {
                _CFGCtrl.CFG = allConfigs.get(0);
            }
            if (_CFGCtrl.CFG == null) {
                _CFGCtrl.CFG = new CalibrateCoefficient();
            }
        }
        return _CFGCtrl;
    }

    public void SaveCalibrateCoefficient(CalibrateCoefficient CalibrateCoefficient) {
        CFG.setKValue(CalibrateCoefficient.getKValue());
        CFG.setBValue(CalibrateCoefficient.getBValue());
        CFG.save();
    }
}
