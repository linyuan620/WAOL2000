package skyray.waol2000.datamodel;

/**
 * 阀开关量
 * */
public enum ValveSwitch {
	/**
	 * 关
	 */
	Close(0),
	/**
	 * 开
	 */
	Open(1) ;

	private int _value;

	private ValveSwitch(int value) {
		_value = value;
	}

	public int value() {
		return _value;
	}

	public static ValveSwitch valueOf(int value) {
		switch (value) {
			case 0:
				return Close;
			case 1:
				return Open;
			default:
				return null;
		}
	}
}
