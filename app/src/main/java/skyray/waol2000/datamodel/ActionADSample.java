package skyray.waol2000.datamodel;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;

/**
 * AD采样动作类
 */
public class ActionADSample extends SugarRecord implements IActionInfo,IEqualble<ActionADSample> {

	@NotNull
	private String ActionName = "";
	@NotNull
	private int SampleCount = 0;
	@NotNull
	private boolean FinalVoltage=false;

	public ActionADSample() {
	}

	public ActionADSample(String actionName, int sampleCount,boolean finalVoltage) {
		setActionName(actionName);
		setSampleCount(sampleCount);
		setFinalVoltage(finalVoltage);
	}

	/**
	 * @return 获取动作名称
	 */
	public String getActionName() {
		return ActionName;
	}

	/**
	 * 设置动作名称
	 *
	 * @param actionName
	 *            动作名称
	 */
	public void setActionName(String actionName) {
		ActionName = actionName;
	}

	/**
	 * @return 获取采样次数
	 */
	public int getSampleCount() {
		return SampleCount;
	}

	/**
	 * 设置采样次数
	 *
	 * @param sampleCount
	 *            采样次数
	 */
	public void setSampleCount(int sampleCount) {
		SampleCount = sampleCount;
	}

	public boolean isFinalVoltage() {
		return FinalVoltage;
	}

	public void setFinalVoltage(boolean finalVoltage) {
		FinalVoltage = finalVoltage;
	}

	@Override
	public String toString() {
		return ActionName;
	}

	@Override
	public boolean isEqual(ActionADSample actionADSample) {
		boolean result = false;
		if (actionADSample != null) {
			result = getActionName().equals(actionADSample.getActionName());
		}
		return result;
	}
}
