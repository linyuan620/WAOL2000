package skyray.waol2000.datamodel;

/**
 * Created by PengJianbo on 2017/9/4.
 */

/**
 * 设置取液高度
 */
public enum HeightType {
    low(0), high(1), full(2);

    private int _value;

    private HeightType(int value) {
        _value = value;
    }

    public int value() {
        return _value;
    }

    public static HeightType valueOf(int value) {
        switch (value) {
            case 0:
                return low;
            case 1:
                return high;
            case 2:
                return full;
            default:
                return null;
        }
    }
}
