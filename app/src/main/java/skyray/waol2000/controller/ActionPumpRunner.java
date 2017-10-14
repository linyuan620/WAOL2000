package skyray.waol2000.controller;

import skyray.waol2000.datamodel.ActionPump;
import skyray.waol2000.datamodel.CommandDefine;
import skyray.waol2000.datamodel.IActionInfo;
import skyray.waol2000.datamodel.PumpSwitch;

/**
 * Created by Administrator on 2017/6/28.
 */

public class ActionPumpRunner implements  IActionRunner {

    void ExcutePump(ActionPump actionPump) {
        if (actionPump != null) {
            byte slaveID = (byte) (ConnectionCfgController.GetConnectionCfgController().GetConnectionCfg().getSlaveID());
            if (actionPump.getSwitchValue().equals(PumpSwitch.Close)) {
                SerialMaster.writeRegister(slaveID, CommandDefine.PumpOperation, CommandDefine.PumpClose);
            } else {
                //脉冲计数值 设置为0时 采用非脉冲模式执行
                int impulseCount = actionPump.getPumpInfoV().getImpulseCount();
                short mode = 1;
                if (impulseCount == 0) {
                    mode = 0;
                } else {
                    //设置脉冲数
                    short high = (short) (impulseCount >> 16);
                    short low = (short) (0xFFFF & impulseCount);

                    short[] count = {high, low};
                    SerialMaster.writeRegisters(slaveID, CommandDefine.PumpImpulseCount, count);
                }

                //设置速度
                SerialMaster.writeRegister(slaveID, CommandDefine.PumpSpeed, (short) actionPump.getSpeed());

                //开关信号 模式信号 方向信号
                short[] data = {(short) CommandDefine.PumpOpen, mode, (short) (actionPump.getPumpInfoV().getDirection())};
                SerialMaster.writeRegisters(slaveID, CommandDefine.PumpOperation, data);
            }
        }
    }

    @Override
    public void runAction(IActionInfo ainfo) {
        if (ainfo != null && ainfo instanceof ActionPump) {
            ActionPump apump = (ActionPump) ainfo;
            ExcutePump(apump);
        }
    }
}
