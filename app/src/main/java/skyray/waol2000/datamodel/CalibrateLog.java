package skyray.waol2000.datamodel;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * 标定日志记录类
 */

public class CalibrateLog extends SugarRecord {

    private Date CalibrateTime;

    private int CalibrateType;

    private double OriginalLightVoltage;

    private double FinalLightVoltage;

    private double Content = 1;

    public CalibrateLog() {

    }

    /**
     * 标定日志记录类
     *
     * @param calibrateTime        标定时间
     * @param calibrateType        标定类型1为标样1 2为标样2
     * @param originalLightVoltage 初始光电压
     * @param finalLightVoltage    最终光电压
     * @param content              标定浓度
     */
    public CalibrateLog(Date calibrateTime, int calibrateType, double originalLightVoltage, double finalLightVoltage, double content) {
        setCalibrateTime(calibrateTime);
        setCalibrateType(calibrateType);
        setOriginalLightVoltage(originalLightVoltage);
        setFinalLightVoltage(finalLightVoltage);
        setContent(content);
    }

    /**
     * 标定时间
     */
    public Date getCalibrateTime() {
        return CalibrateTime;
    }

    public void setCalibrateTime(Date calibrateTime) {
        CalibrateTime = calibrateTime;
    }

    /**
     * 标定类型 1为标样1 2为标样2
     */
    public int getCalibrateType() {
        return CalibrateType;
    }

    public void setCalibrateType(int calibrateType) {
        CalibrateType = calibrateType;
    }

    public double getOriginalLightVoltage() {
        return OriginalLightVoltage;
    }

    public void setOriginalLightVoltage(double originalLightVoltage) {
        OriginalLightVoltage = originalLightVoltage;
    }

    public double getFinalLightVoltage() {
        return FinalLightVoltage;
    }

    public void setFinalLightVoltage(double finalLightVoltage) {
        FinalLightVoltage = finalLightVoltage;
    }

    /**
     * 标定浓度
     */
    public double getContent() {
        return Content;
    }

    public void setContent(double content) {
        Content = content;
    }
}
