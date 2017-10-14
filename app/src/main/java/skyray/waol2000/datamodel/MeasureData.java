package skyray.waol2000.datamodel;

import com.orm.SugarRecord;

import java.util.Date;

import skyray.waol2000.Utils.DateTimeFormater;
import skyray.waol2000.Utils.NumberPointManage;

/**
 * 测量结果类
 */

public class MeasureData extends SugarRecord {
    public Date getMeasureTime() {
        return MeasureTime;
    }

    public void setMeasureTime(Date measureTime) {
        MeasureTime = measureTime;
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

    public double getMeasureValue() {
        return MeasureValue;
    }

    public void setMeasureValue(double measureValue) {
        MeasureValue = measureValue;
    }

    /**
     * 测量时间
     */
    private Date MeasureTime = new Date();
    /**
     * 初始光电压
     */
    private double OriginalLightVoltage;
    /**
     * 最终光电压
     */
    private double FinalLightVoltage;
    /**
     * 测量结果
     */
    private double MeasureValue = 0;

    public MeasureData() {
    }

    public MeasureData(Date measureTime, double originalLightVoltage, double finalLightVoltage, double measureValue) {
        setMeasureTime(measureTime);
        setOriginalLightVoltage(originalLightVoltage);
        setFinalLightVoltage(finalLightVoltage);
        setMeasureValue(measureValue);
    }

    @Override
    public String toString() {
        return DateTimeFormater.getTimeString(MeasureTime) + "\t\t"
                + NumberPointManage.toString(OriginalLightVoltage, 2) + "\t\t"
                + NumberPointManage.toString(FinalLightVoltage, 2) + "\t\t"
                + NumberPointManage.toString(MeasureValue, 2);
    }
}
