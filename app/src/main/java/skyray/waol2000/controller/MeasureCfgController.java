package skyray.waol2000.controller;

import java.util.List;

import skyray.waol2000.datamodel.MeasureConfig;
import skyray.waol2000.datamodel.MeasureMode;

/**
 * Created by Administrator on 2017/6/22.
 */

public class MeasureCfgController {

    /**
     * 整点测量时间间隔符号
     */
    public final static String PointTimeSplitStr = ",";

    private MeasureConfig CFG = new MeasureConfig();

    public MeasureConfig getMeasureConfig() {
        return CFG;
    }

    private static MeasureCfgController _CFGCtrl = null;

    /**
     * 加载配置并获取之
     *
     * @return
     */
    public static MeasureCfgController getMeasureConfigController() {
        if (_CFGCtrl == null) {
            _CFGCtrl = new MeasureCfgController();
            List<MeasureConfig> allConfigs = MeasureConfig.listAll(MeasureConfig.class);
            if (allConfigs != null && allConfigs.size() > 0) {
                _CFGCtrl.CFG = allConfigs.get(0);
            }
            if (_CFGCtrl.CFG == null) {
                _CFGCtrl.CFG = new MeasureConfig();
            }
        }
        return _CFGCtrl;
    }

    /**
     * 保存测量配置
     *
     * @param measureConfig 配置信息
     */
    public void saveMeasureConfig(MeasureConfig measureConfig) {
        if (measureConfig != null) {
            CFG.setMode(measureConfig.getMode());
            CFG.setIntervalTime(measureConfig.getIntervalTime());
            CFG.setPointTimes(measureConfig.getPointTimes());
            CFG.save();
        }
    }

    public int[] getPointTimeArray() {
        int[] result = null;
        if (CFG != null && CFG.getMode() == MeasureMode.PointTimeMode.value()) {
            if (!CFG.getPointTimes().equals("")) {
                String[] mm = CFG.getPointTimes().split(MeasureCfgController.PointTimeSplitStr);
                if (mm != null && mm.length > 0) {
                    result = new int[mm.length];
                    for (int i = 0; i < mm.length; i++) {
                        result[i] = Integer.parseInt(mm[i]);
                    }
                }
            }
        }
        return result;
    }
}
