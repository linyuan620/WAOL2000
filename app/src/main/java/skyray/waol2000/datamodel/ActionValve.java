package skyray.waol2000.datamodel;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;

/**
 * 阀动作类
 */
public class ActionValve extends SugarRecord implements IActionInfo,IEqualble<ActionValve> {

	@NotNull
	private String ActionName = "";
	@NotNull
	private ValveInfo ValveInfoV = null;
	@NotNull
	private ValveSwitch SwitchValue = ValveSwitch.Close;

	public ActionValve() {
	}

	/**
	 * 阀动作类构造函数
	 *
	 * @param actionName
	 *            动作名称
	 * @param valveInfoV
	 *            阀编号
	 * @param sValue
	 *            阀开关值 开为1 关为0
	 */
	public ActionValve(String actionName, ValveInfo valveInfoV, ValveSwitch sValue) {
		setActionName(actionName);
		setValveInfoV(valveInfoV);
		setSwitchValue(sValue);
	}

	/**
	 * @return 获取阀动作名称
	 */
	public String getActionName() {
		return ActionName;
	}

	/**
	 * 设置阀动作名称
	 *
	 * @param actionName
	 *            动作名称
	 */
	public void setActionName(String actionName) {
		ActionName = actionName;
	}

	/**
	 * @return 获取阀信息
	 */
	public ValveInfo getValveInfoV() {
		return ValveInfoV;
	}

	/**
	 * 设置阀信息
	 *
	 * @param valveInfoV
	 *            阀信息
	 */
	public void setValveInfoV(ValveInfo valveInfoV) {
		ValveInfoV = valveInfoV;
	}

	/**
	 * @return 获取开关参数
	 */
	public ValveSwitch getSwitchValue() {
		return SwitchValue;
	}

	/**
	 * @param switchValue
	 *            开为1 关为0
	 */
	public void setSwitchValue(ValveSwitch switchValue) {
		SwitchValue = switchValue;
	}

	@Override
	public String toString() {
		return ActionName;
	}

	@Override
	public boolean isEqual(ActionValve actionValve) {
		boolean result = false;
		if (actionValve != null) {
			result = getActionName().equals(actionValve.getActionName());
		}
		return result;
	}
}
