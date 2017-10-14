package skyray.waol2000.datamodel;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;

/**
 * 泵动作类
 */
public class ActionPump extends SugarRecord implements IActionInfo ,IEqualble<ActionPump> {
    @NotNull
    private String ActionName = "";
    @NotNull
    private PumpInfo PumpInfoV = null;
    @NotNull
    private PumpSwitch SwitchValue = PumpSwitch.Close;
    @NotNull
    private int Speed = 0;

    public ActionPump() {
    }

    public ActionPump(String actionName, PumpInfo pumpInfo, PumpSwitch sValue, int speed) {
        setActionName(actionName);
        setPumpInfoV(pumpInfo);
        setSwitchValue(sValue);
        setSpeed(speed);
    }

    /**
     * @return 获取动作名称
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


    public PumpInfo getPumpInfoV() {
        return PumpInfoV;
    }

    public void setPumpInfoV(PumpInfo pumpInfoV) {
        PumpInfoV = pumpInfoV;
    }

    /**
     * @return 获取开关参数
     */
    public PumpSwitch getSwitchValue() {
        return SwitchValue;
    }

    /**
     * @param switchValue 开为1 关为0
     */
    public void setSwitchValue(PumpSwitch switchValue) {
        SwitchValue = switchValue;
    }

    /**
     * @return 获取速度档位
     */
    public int getSpeed() {
        return Speed;
    }

    /**
     * 设置速度档位
     *
     * @param speed 取值为1,2,4,8,16,32,64
     */
    public void setSpeed(int speed) {
        Speed = speed;
    }

    @Override
    public String toString() {
        return ActionName;
    }

    @Override
    public boolean isEqual(ActionPump actionPump) {
        boolean result = false;
        if (actionPump != null) {
            result = getActionName().equals(actionPump.getActionName());
        }
        return result;
    }
}
