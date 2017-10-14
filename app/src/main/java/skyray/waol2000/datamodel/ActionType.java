package skyray.waol2000.datamodel;

/**
 * 动作类型
 */
public enum ActionType {
    /**
     * 阀动作
     */
    Valve(0),
    /**
     * 泵动作
     */
    Pump(1),
    /**
     * AD采样
     */
    ADSample(2),
    /**
     * 等待时间
     */
    WaitTime(3),
    /**
     * 进样动作
     */
    Injection(4);

    private int _value;

    private ActionType(int value) {
        _value = value;
    }

    public int value() {
        return _value;
    }

    public static ActionType valueOf(int value) {
        switch (value) {
            case 0:
                return Valve;
            case 1:
                return Pump;
            case 2:
                return ADSample;
            case 3:
                return WaitTime;
            case 4:
                return Injection;
            default:
                return null;
        }
    }
}
