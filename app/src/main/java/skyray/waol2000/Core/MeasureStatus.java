package skyray.waol2000.Core;

/**
 * Created by Administrator on 2017/6/28.
 */

public enum MeasureStatus {

    /**
     * 空闲
     */
    Idle(0),
    /**
     * 标样1
     */
    StandardOne(1),
    /**
     * 标样2
     */
    StandardTwo(2),
    /**
     * 样品
     */
    Sample(3);

    private int _value;

    private MeasureStatus(int value) {
        _value = value;
    }

    public int value() {
        return _value;
    }

    public static MeasureStatus valueOf(int value) {
        switch (value) {
            case 0:
                return Idle;
            case 1:
                return StandardOne;
            case 2:
                return StandardTwo;
            case 3:
                return Sample;
            default:
                return null;
        }
    }
}
