package skyray.waol2000.datamodel;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;

/**
 * 泵基础数据类
 */
public class PumpInfo extends SugarRecord implements  IActionInfo ,IEqualble<PumpInfo> {

	@NotNull
	private String PumpName = "";
	@NotNull
	private int ImpulseCount = 0;
	@NotNull
	private int Direction = 0;

	public PumpInfo() {
	}

	public PumpInfo(String pumpName, int impulseCount, int direction) {
		setPumpName(pumpName);
		setImpulseCount(impulseCount);
		setDirection(direction);
	}

	public String getPumpName() {
		return PumpName;
	}

	public void setPumpName(String pumpName) {
		PumpName = pumpName;
	}

	public int getImpulseCount() {
		return ImpulseCount;
	}

	/**
	 * 设置蠕动泵脉冲计数
	 *
	 * @param impulseCount 蠕动泵脉冲计数模式为1时使用
	 */
	public void setImpulseCount(int impulseCount) {
		ImpulseCount = impulseCount;
	}

	/**
	 * @return 获取泵运动方向0为正，1为反
	 */
	public int getDirection() {
		return Direction;
	}

	/**
	 * 设置泵运动方向
	 *
	 * @param direction 方向0为正，1为负
	 */
	public void setDirection(int direction) {
		Direction = direction;
	}

	@Override
	public String toString() {
		return PumpName;
	}

	@Override
	public boolean isEqual(PumpInfo pumpInfo) {
		boolean result = false;
		if (pumpInfo != null) {
			result = getPumpName().equals(pumpInfo.getPumpName());
		}
		return result;
	}
}
