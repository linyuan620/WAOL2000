package skyray.waol2000.datamodel;

/**
 * Created by Administrator on 2017/6/22.
 */

public enum MeasureMode {
    /**
     * 间隔模式
     */
    IntervalMode(0),
    /**
     * 整点模式
     */
    PointTimeMode(1),
    /**
     * 触发模式
     */
    TriggerMode(2);

    private int _value;

    private MeasureMode(int value) {
        _value = value;
    }

    public int value() {
        return _value;
    }

    public static MeasureMode valueOf(int value) {
        switch (value) {
            case 0:
                return IntervalMode;
            case 1:
                return PointTimeMode;
            case 2:
                return TriggerMode;
            default:
                return null;
        }
    }
}
