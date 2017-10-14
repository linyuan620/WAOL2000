package skyray.waol2000.datamodel;

/**
 * 泵开关量
 */
public enum PumpSwitch {
	/**
	 * 开
	 */
	Open(0),
	/**
	 * 关
	 */
	Close(1);

	private int _value;

	private PumpSwitch(int value) {
		_value = value;
	}

	public int value() {
		return _value;
	}

	public static PumpSwitch valueOf(int value) {
		switch (value) {
			case 0:
				return Open;
			case 1:
				return Close;
			default:
				return null;
		}
	}
}
