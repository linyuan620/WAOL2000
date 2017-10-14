package skyray.waol2000.Core;

import skyray.waol2000.controller.CalibrateCoefficientCfgController;
import skyray.waol2000.datamodel.CalibrateCoefficient;

/**
 * 计数存储和得到结果处理类
 */

public class CalibrateManage {

    /**
     * 标样1浓度
     */
    public static double StdOneContent = 0;
    /**
     * 标样1计数
     */
    static double StdOneOriginalLightVoltage = 0;
    /**
     * 标样1计数
     */
    static double StdOneFinalLightVoltage = 0;
    /**
     * 标样2浓度
     */
    public static double StdTwoContent = 0;
    /**
     * 标样1计数
     */
    static double StdTwoOriginalLightVoltage = 0;
    /**
     * 标样1计数
     */
    static double StdTwoFinalLightVoltage = 0;
    private double bValue;

    public static void SetStandardOneContent(double content) {
        StdOneContent = content;
    }

    public static void SetStandardTwoContent(double content) {
        StdTwoContent = content;
    }

    public static void SetStandardOneLightVoltage(double originalvoltage, double finalvoltage) {
        StdOneOriginalLightVoltage = originalvoltage;
        StdOneFinalLightVoltage = finalvoltage;
    }

    public static void SetStandardTwoLightVoltage(double originalvoltage, double finalvoltage) {
        StdTwoOriginalLightVoltage = originalvoltage;
        StdTwoFinalLightVoltage = finalvoltage;
    }

    public static CalibrateCoefficient GetCalibrateCalculateKB() {
        CalibrateCoefficient cc = new CalibrateCoefficient();

        double kValue = 1;
        double bValue = 0;

        double A1 = 0;
        if (StdOneOriginalLightVoltage != 0) {
            A1 = -Math.log10(StdOneFinalLightVoltage / StdOneOriginalLightVoltage);
        }
        double A2 = 0;
        if (StdTwoOriginalLightVoltage != 0) {
            A2 = -Math.log10(StdTwoFinalLightVoltage / StdTwoOriginalLightVoltage);
        }

        double child = StdTwoContent - StdOneContent;
        double master = A2 - A1;
        if (master != 0) {
            kValue = child / master;
            bValue = StdTwoContent - kValue * A2;
        }

        cc.setKValue(kValue);
        cc.setBValue(bValue);
        return cc;
    }

    public static void ChangeCoefficient(double k, double b) {
        CalibrateCoefficient cc = new CalibrateCoefficient(k, b);
        CalibrateCoefficientCfgController.GetCalibrateCoefficientController().SaveCalibrateCoefficient(cc);
    }

    public static double GetResult(double originalvoltage, double finalvoltage) {
        double result = 0;
        if (originalvoltage != 0) {
            double A = -Math.log10(finalvoltage / originalvoltage);
            result = CalibrateCoefficientCfgController.GetCalibrateCoefficientController().GetCalibrateCoefficient().getKValue() * A +
                    CalibrateCoefficientCfgController.GetCalibrateCoefficientController().GetCalibrateCoefficient().getBValue();
        }
        return result;
    }
}
