package skyray.waol2000.datamodel;

import com.orm.SugarRecord;

/**
 * 标定系数
 */

public class CalibrateCoefficient extends SugarRecord {

    private double KValue = 1;
    private double BValue = 0;

    public CalibrateCoefficient() {
    }

    public CalibrateCoefficient(double kvalue, double bvalue) {
        setKValue(kvalue);
        setBValue(bvalue);
    }

    public double getKValue() {
        return KValue;
    }

    public void setKValue(double KValue) {
        this.KValue = KValue;
    }

    public double getBValue() {
        return BValue;
    }

    public void setBValue(double BValue) {
        this.BValue = BValue;
    }
}
