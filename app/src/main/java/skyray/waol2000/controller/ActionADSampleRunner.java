package skyray.waol2000.controller;

import java.util.Date;

import skyray.waol2000.Core.CalibrateManage;
import skyray.waol2000.Core.MainStatusManage;
import skyray.waol2000.Core.MeasureResultCache;
import skyray.waol2000.Core.MeasureStatus;
import skyray.waol2000.Utils.DateOperator;
import skyray.waol2000.datamodel.ActionADSample;
import skyray.waol2000.datamodel.CalibrateLog;
import skyray.waol2000.datamodel.CommandDefine;
import skyray.waol2000.datamodel.IActionInfo;
import skyray.waol2000.datamodel.MeasureData;

/**
 * AD采样执行
 */

public class ActionADSampleRunner implements IActionRunner {
    @Override
    public void runAction(IActionInfo ainfo) {
        if (ainfo != null && ainfo instanceof ActionADSample) {
            ActionADSample actionADSample = (ActionADSample) ainfo;

            short[] values = {1, (short) actionADSample.getSampleCount()};
            SerialMaster.writeRegisters(ConnectionCfgController.GetConnectionCfgController().GetConnectionCfg().getSlaveID(),
                    CommandDefine.ADOperation, values);

            waitADOK();

            ReadAndDealData(actionADSample);
        }
    }

    private void waitADOK() {
        short[] data = null;
        int timeout = 40;//最多等待40次200毫秒
        try {
            for (int i = 0; i < timeout; i++) {
                data = SerialMaster.readHoldingRegisters(ConnectionCfgController.GetConnectionCfgController().GetConnectionCfg().getSlaveID(),
                        CommandDefine.ADOperation, 1);
                if (data != null && data.length > 0) {
                    if (data[0] == 0) {
                        break;
                    }
                    Thread.sleep(200);
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     * 读取和处理数据
     */
    void ReadAndDealData(ActionADSample actionADSample) {
        if (actionADSample != null && !MainStatusManage.isIfStopOperation()) {
            short[] d = SerialMaster.readInputRegisters(ConnectionCfgController.GetConnectionCfgController().GetConnectionCfg().getSlaveID(),
                    CommandDefine.ADSampleAddr, 1);
            double result = 0;
            if (d != null && d.length > 0) {
                result = getUnsignedShort(d[0]); //5000.0 * d[0] / 32768.0;
                MainStatusManage.setLightVoltage(result, actionADSample.isFinalVoltage());

                SaveResult(actionADSample);
            }
        }
    }

    public int getUnsignedShort(short data) {      //将data字节型数据转换为0~65535 (0xFFFF 即 WORD)。
        return data & 0x0FFFF;
    }

    /**
     * 测量时保存结果
     */
    void SaveResult(ActionADSample actionADSample) {
        if (actionADSample != null && actionADSample.isFinalVoltage()) {
            Date dt = DateOperator.getNow();
            Date operTime = new Date(dt.getYear(), dt.getMonth(), dt.getDate(), dt.getHours(), dt.getMinutes(), dt.getSeconds());

            if (MainStatusManage.getCurrentSatus() == MeasureStatus.StandardOne
                    || MainStatusManage.getCurrentSatus() == MeasureStatus.StandardTwo) {
                CalibrateLog cl = new CalibrateLog();
                cl.setCalibrateTime(operTime);
                cl.setOriginalLightVoltage(MainStatusManage.get_OriginalLightVoltage());
                cl.setFinalLightVoltage(MainStatusManage.get_FinalLightVoltage());
                cl.setCalibrateType(MainStatusManage.getCurrentSatus().value());
                if (MainStatusManage.getCurrentSatus() == MeasureStatus.StandardOne) {
                    cl.setContent(MainStatusManage.getStandardOneContent());
                    CalibrateManage.SetStandardOneLightVoltage(MainStatusManage.get_OriginalLightVoltage(), MainStatusManage.get_FinalLightVoltage());
                } else if (MainStatusManage.getCurrentSatus() == MeasureStatus.StandardTwo) {
                    cl.setContent(MainStatusManage.getStandardTwoContent());
                    CalibrateManage.SetStandardTwoLightVoltage(MainStatusManage.get_OriginalLightVoltage(), MainStatusManage.get_FinalLightVoltage());
                }
                cl.save();
            } else if (MainStatusManage.getCurrentSatus() == MeasureStatus.Sample) {
                double result = CalibrateManage.GetResult(MainStatusManage.get_OriginalLightVoltage(),
                        MainStatusManage.get_FinalLightVoltage());

                MeasureData measureData = new MeasureData();
                measureData.setMeasureTime(operTime);
                measureData.setOriginalLightVoltage(MainStatusManage.get_OriginalLightVoltage());
                measureData.setFinalLightVoltage(MainStatusManage.get_FinalLightVoltage());
                measureData.setMeasureValue(result);
                measureData.save();

                MeasureResultCache.getMCache().addMeasureData(measureData);
            }
        }
    }
}
