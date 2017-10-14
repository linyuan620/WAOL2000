package skyray.waol2000.datamodel;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;

/**
 * 等待时间动作类
 * */
public class ActionWaitTime extends SugarRecord implements IActionInfo,IEqualble<ActionWaitTime> {
	@NotNull
	private String ActionName = "";
	@NotNull
	private int TimeValue = 0;

	public ActionWaitTime() {
	}

	public ActionWaitTime(String actionName, int tValue) {
		setActionName(actionName);
		setTimeValue(tValue);
	}

	/**
	 * @return 动作名称
	 */
	public String getActionName() {
		return ActionName;
	}

	/**
	 * @param actionName 动作名称
	 */
	public void setActionName(String actionName) {
		ActionName = actionName;
	}

	/**
	 * 获取等待时间 分钟
	 *
	 * @return
	 */
	public int getTimeValue() {
		return TimeValue;
	}

	public void setTimeValue(int timeValue) {
		TimeValue = timeValue;
	}

	@Override
	public String toString() {
		return ActionName;
	}

	@Override
	public boolean isEqual(ActionWaitTime actionWaitTime) {
		boolean result = false;
		if (actionWaitTime != null) {
			result = getActionName().equals(actionWaitTime.getActionName());
		}
		return result;
	}
}
