package skyray.waol2000.Core;

/**
 * 测量过程主要状态管理
 */

public class MainStatusManage {

    /**
     * 是否停止测量、标定1、标定2标志
     */
    private static boolean ifStopOperation = false;

    public static boolean isIfStopOperation() {
        return ifStopOperation;
    }

    public static void setIfStopOperation(boolean ifStopOperation) {
        MainStatusManage.ifStopOperation = ifStopOperation;
    }

    static MeasureStatus _CurrentSatus = MeasureStatus.Idle;

    public static MeasureStatus getCurrentSatus() {
        return _CurrentSatus;
    }

    public static void setLightVoltage(double voltage, boolean finalVoltage) {
        if (finalVoltage) {
            _FinalLightVoltage = voltage;
        } else {
            _OriginalLightVoltage = voltage;
        }
    }

    /**
     * 初始光电压
     */
    static double _OriginalLightVoltage = 0;

    /**
     * 获取初始光电压
     *
     * @return
     */
    public static double get_OriginalLightVoltage() {
        return _OriginalLightVoltage;
    }

    /**
     * 最终光电压
     */
    static double _FinalLightVoltage = 0;

    /**
     * 获取最终光电压
     *
     * @return
     */
    public static double get_FinalLightVoltage() {
        return _FinalLightVoltage;
    }

    public static void ChangeStatus(MeasureStatus status) {
        _CurrentSatus = status;
    }

    public static void ChangeStatus(MeasureStatus status, double content) {
        _CurrentSatus = status;
        if (status == MeasureStatus.StandardOne) {
            CalibrateManage.SetStandardOneContent(content);
        } else if (status == MeasureStatus.StandardTwo) {
            CalibrateManage.SetStandardTwoContent(content);
        }
    }

    public static double getStandardOneContent() {
        return CalibrateManage.StdOneContent;
    }

    public static double getStandardTwoContent() {
        return CalibrateManage.StdTwoContent;
    }
}
