package skyray.waol2000.datamodel;

/**
 * 流程类型
 */
public enum FlowType {
	/**
	 * 测量流程
	 */
	F(0),
	/**
	 * 维护动作
	 */
	P(1),
	/**
	 * 标定1
	 */
	S1(2),
	/**
	 * 标定2
	 */
	S2(3);

	private int _value;

	private FlowType(int value) {
		_value = value;
	}

	public int value() {
		return _value;
	}

	public static FlowType valueOf(int value) {
		switch (value) {
			case 0:
				return F;
			case 1:
				return P;
			case 2:
				return S1;
			case 3:
				return S2;
			default:
				return null;
		}
	}
}
