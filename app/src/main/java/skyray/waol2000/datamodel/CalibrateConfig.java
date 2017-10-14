package skyray.waol2000.datamodel;

import com.orm.SugarRecord;

/**
 * 标定配置类
 */

public class CalibrateConfig extends SugarRecord {

    /**
     * 标样1浓度
     */
    private double ContentOne = 0;
    /**
     * 标样2浓度
     */
    private double ContentTwo = 0;
    /**
     * 校准时间 小时
     */
    private int CalibrateTime = 0;
    /**
     * 间隔时间天
     */
    private int CalibrateIntervalTime = 1;
    /**
     * 测量流程中是否自动校准
     */
    private boolean AutoCalibrate = false;
    /**
     * 第一个循环是否校准
     */
    private boolean FirstLoopCalibrate = false;

    public CalibrateConfig() {
    }

    public CalibrateConfig(double contentOne, double contentTwo, int calibrateTime,
                           int calibrateIntervalTime, boolean autoCalibrate, boolean firstLoopCalibrate) {
        setContentOne(contentOne);
        setContentTwo(contentTwo);
        setCalibrateTime(calibrateTime);
        setCalibrateIntervalTime(calibrateIntervalTime);
        setAutoCalibrate(autoCalibrate);
        setFirstLoopCalibrate(firstLoopCalibrate);
    }

    public boolean isAutoCalibrate() {
        return AutoCalibrate;
    }

    public void setAutoCalibrate(boolean autoCalibrate) {
        AutoCalibrate = autoCalibrate;
    }

    public boolean isFirstLoopCalibrate() {
        return FirstLoopCalibrate;
    }

    public void setFirstLoopCalibrate(boolean firstLoopCalibrate) {
        FirstLoopCalibrate = firstLoopCalibrate;
    }

    public double getContentOne() {
        return ContentOne;
    }

    public void setContentOne(double contentOne) {
        ContentOne = contentOne;
    }

    public double getContentTwo() {
        return ContentTwo;
    }

    public void setContentTwo(double contentTwo) {
        ContentTwo = contentTwo;
    }

    /**
     * 标定时间 0-23
     */
    public int getCalibrateTime() {
        return CalibrateTime;
    }

    /**
     * 设置标定时间 0-23
     */
    public void setCalibrateTime(int calibrateTime) {
        CalibrateTime = calibrateTime;
    }

    /**
     * 标定间隔时间天
     */
    public int getCalibrateIntervalTime() {
        return CalibrateIntervalTime;
    }

    public void setCalibrateIntervalTime(int calibrateIntervalTime) {
        CalibrateIntervalTime = calibrateIntervalTime;
    }
}
