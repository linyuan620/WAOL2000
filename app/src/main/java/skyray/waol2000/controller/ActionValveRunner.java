package skyray.waol2000.controller;

import skyray.waol2000.datamodel.ActionValve;
import skyray.waol2000.datamodel.CommandDefine;
import skyray.waol2000.datamodel.IActionInfo;
import skyray.waol2000.datamodel.ValveSwitch;

/**
 * 阀动作执行控制类
 */

public class ActionValveRunner implements IActionRunner {

    private short GetValveManageData() {
        double result = 0;
        for (int i = 0; i < valveStatus.length; i++) {
            if (valveStatus[i]) {
                result += Math.pow(2, i);
            }
        }
        return (short) result;
    }

    private void DealValve() {
        short value = GetValveManageData();
        SerialMaster.writeRegister(ConnectionCfgController.GetConnectionCfgController().GetConnectionCfg().getSlaveID(),
                CommandDefine.ValveBeginAddress, value);
    }

    private static boolean[] valveStatus = {false, false, false, false, false};

    private void OpenValve(int vinfo) {
        ActionValveRunner.valveStatus[vinfo] = true;
        DealValve();
    }

    private void CloseValve(int vinfo) {
        ActionValveRunner.valveStatus[vinfo] = false;
        DealValve();
    }

    private void ExcuteValve(ActionValve actionValve) {
        if (actionValve != null) {
            int index = actionValve.getValveInfoV().getCmdIndex();
            ValveSwitch valveSwitch = actionValve.getSwitchValue();
            if (valveSwitch.equals(ValveSwitch.Close)) {
                CloseValve(index);
            } else {
                OpenValve(index);
            }
        }
    }

    @Override
    public void runAction(IActionInfo ainfo) {
        if (ainfo != null && ainfo instanceof ActionValve) {
            ActionValve av = (ActionValve) ainfo;
            ExcuteValve(av);
        }
    }
}
