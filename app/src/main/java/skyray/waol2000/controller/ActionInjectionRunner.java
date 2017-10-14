package skyray.waol2000.controller;

import skyray.waol2000.Core.MainStatusManage;
import skyray.waol2000.datamodel.ActionInjection;
import skyray.waol2000.datamodel.CommandDefine;
import skyray.waol2000.datamodel.IActionInfo;

/**
 * 进样动作运行类
 * Created by PengJianbo on 2017/8/30.
 */

public class ActionInjectionRunner implements IActionRunner {

    private static final int StatusIdle = 0x55;
    private static final int StatusBusy = 0xAA;

    @Override
    public void runAction(IActionInfo ainfo) {
        if (ainfo != null && ainfo instanceof ActionInjection) {
            ActionInjection action = (ActionInjection) ainfo;
            excuteActionInjection(action);
        }
    }

    private void excuteActionInjection(ActionInjection aInjection) {
        if (aInjection != null) {
            byte slaveID = (byte) (ConnectionCfgController.GetConnectionCfgController().GetConnectionCfg().getSlaveID());

            waitSystemIdle(slaveID);

            if (aInjection.getInjectionValue() != null) {
                short[] cmd = new short[3];
                cmd[0] = (short) aInjection.getInjectionValue().getInputPort();
                cmd[1] = (short) aInjection.getInjectionValue().getOutputPort();
                cmd[2] = (short) aInjection.getInjectionValue().getHeight();

                SerialMaster.writeRegisters(slaveID, CommandDefine.InjectionParameterStartAddr, cmd);

                try {
                    Thread.sleep(50);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                SerialMaster.writeRegisters(slaveID, CommandDefine.InjectionOperation, new short[]{1});
            } else {
                SerialMaster.writeRegisters(slaveID, aInjection.getDefaultInjectionID(), new short[]{1});
            }

            waitInjectionStart(slaveID);

            waitSystemIdle(slaveID);
        }
    }

    private void waitSystemStaus(byte slaveID, int status) {
        while (!MainStatusManage.isIfStopOperation()) {
            short[] data = SerialMaster.readHoldingRegisters(slaveID, CommandDefine.SystemStatusAddr, 1);
            if (data != null && data.length == 1) {
                if (data[0] == status) {
                    break;
                } else {
                    try {
                        Thread.sleep(300);
                    } catch (Exception ex) {
                    }
                }
            }
        }
    }

    private void waitSystemIdle(byte slaveID) {
        waitSystemStaus(slaveID, StatusIdle);
    }

    private void waitInjectionStart(byte slaveID) {
        waitSystemStaus(slaveID, StatusBusy);
    }
}
