package skyray.waol2000.Core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import skyray.waol2000.Utils.DateOperator;
import skyray.waol2000.controller.CalibrateCfgController;
import skyray.waol2000.controller.FlowController;
import skyray.waol2000.controller.MeasureCfgController;
import skyray.waol2000.datamodel.CalibrateCoefficient;
import skyray.waol2000.datamodel.FlowInfo;
import skyray.waol2000.datamodel.FlowType;
import skyray.waol2000.datamodel.IActionInfo;
import skyray.waol2000.datamodel.MeasureConfig;
import skyray.waol2000.datamodel.MeasureMode;

/**
 * 测量流程运行管理类
 */

public class FlowEngine {

    private FlowEngine() {
    }

    private static FlowEngine _FG = new FlowEngine();

    public static FlowEngine getFG() {
        return _FG;
    }

    boolean isRunning = false;

    RunFlowManage RunM = new RunFlowManage();
    FlowInfo SampleFlow = null;
    FlowInfo CalibrateFlow1 = null;
    FlowInfo CalibrateFlow2 = null;

    ThreadFun ThreadF = null;

    public boolean isOver = false;

    public double getOriginalLightVoltage() {
        return MainStatusManage.get_OriginalLightVoltage();
    }

    public double getFinalLightVoltage()
    {
        return MainStatusManage.get_FinalLightVoltage();
    }

    public MeasureStatus getCurrentStatus() {
        return MainStatusManage.getCurrentSatus();
    }

    /// <summary>
    /// 检查当前运行状态与需要运行的状态是否冲突
    /// </summary>
    /// <param name="ms">需要运行的状态</param>
    String CheckStatus(MeasureStatus ms) {
        String result = "";
        if (MainStatusManage.getCurrentSatus() == MeasureStatus.Idle) {
            return result;
        }
        if (ms != MeasureStatus.Idle) {
            if (ms != MainStatusManage.getCurrentSatus()) {
                result = "系统运行中，请先停止！";
                if (MainStatusManage.getCurrentSatus() == MeasureStatus.StandardOne) {
                    result = "标定1运行中，请先停止！";
                } else if (MainStatusManage.getCurrentSatus() == MeasureStatus.StandardTwo) {
                    result = "标定2运行中，请先停止！";
                }
            }
        }
        return result;
    }

    /// <summary>
    /// 标定1
    /// </summary>
    /// <param name="content"></param>
    public String BeginCalibrateOne(double content) {
        String msg = CheckStatus(MeasureStatus.StandardOne);
        if (msg.equals("")) {
            MainStatusManage.ChangeStatus(MeasureStatus.StandardOne, content);
            ConnectionManager.connect();
            msg = RunFlow(FlowType.S1);
        }
        return msg;
    }

    /// <summary>
    /// 标定2
    /// </summary>
    /// <param name="content"></param>
    public String BeginCalibrateTwo(double content) {
        String msg = CheckStatus(MeasureStatus.StandardTwo);
        if (msg.equals("")) {
            MainStatusManage.ChangeStatus(MeasureStatus.StandardTwo, content);
            ConnectionManager.connect();
            msg = RunFlow(FlowType.S2);
        }
        return msg;
    }

    public String StartMeasure() {
        String msg = CheckStatus(MeasureStatus.Sample);
        if (msg.equals("")) {
            MainStatusManage.ChangeStatus(MeasureStatus.Sample);
            _ErrorMsg = "";

            _MeasureCFG = MeasureCfgController.getMeasureConfigController().getMeasureConfig();
            MeasureTimes = MeasureCfgController.getMeasureConfigController().getPointTimeArray();

            ConnectionManager.connect();
            msg = RunFlow(FlowType.F);
        }
        return msg;
    }

    public void ChangeCoefficient(double k, double b) {
        CalibrateManage.ChangeCoefficient(k, b);
    }

    /**
     * 获取校准系数
     *
     * @return
     */
    public CalibrateCoefficient getCC() {
        return CalibrateManage.GetCalibrateCalculateKB();
    }

    void SetRunFlow(FlowType ftd) {
        List<FlowInfo> allF = FlowController.getASC().getAllInfos();
        SampleFlow = null;
        CalibrateFlow1 = null;
        CalibrateFlow2 = null;
        if (allF != null && allF.size() > 0) {
            for (int i = 0; i < allF.size(); i++) {
                if (ftd == FlowType.F) {
                    if (allF.get(i).getFlowTypeV() == FlowType.F) {
                        SampleFlow = allF.get(i);
                    } else if (allF.get(i).getFlowTypeV() == FlowType.S1) {
                        CalibrateFlow1 = allF.get(i);
                    } else if (allF.get(i).getFlowTypeV() == FlowType.S2) {
                        CalibrateFlow2 = allF.get(i);
                    }
                } else if (ftd == FlowType.S1 && allF.get(i).getFlowTypeV() == ftd) {
                    CalibrateFlow1 = allF.get(i);
                } else if (ftd == FlowType.S2 && allF.get(i).getFlowTypeV() == ftd) {
                    CalibrateFlow2 = allF.get(i);
                }
            }
        }
    }

    String CheckConfigRight(FlowType ftd) {
        String result = "";
        if (ftd == FlowType.F) {
            if (SampleFlow == null) {
                result = "请先配置类型为 " + ftd.toString() + " 的流程！";
            } else if (CalibrateFlow1 == null) {
                result = "请先配置类型为 " + FlowType.S1.toString() + " 的流程！";
            } else if (CalibrateFlow2 == null) {
                result = "请先配置类型为 " + FlowType.S2.toString() + " 的流程！";
            } else if (CalibrateCfgController.getCalibrateConfigController().GetCalibrateConfig() == null) {
                result = "请先配置自动标定参数！";
            } else if (_MeasureCFG == null) {
                result = "请先配置测量参数！";
            }
        } else if (ftd == FlowType.S1) {
            if (CalibrateFlow1 == null) {
                result = "请先配置类型为 " + FlowType.S1.toString() + " 的流程！";
            }
        } else if (ftd == FlowType.S2) {
            if (CalibrateFlow2 == null) {
                result = "请先配置类型为 " + FlowType.S2.toString() + " 的流程！";
            }
        }
        return result;
    }

    /// <summary>
    /// 启动所有有效流程
    /// </summary>
    String RunFlow(FlowType ftd) {
        isOver = false;

        SetRunFlow(ftd);

        String configR = CheckConfigRight(ftd);
        if (configR.equals("")) {
            MainStatusManage.setIfStopOperation(false);
            ThreadF = new ThreadFun();
            ThreadF.start();
            isRunning = true;
        }
        return configR;
    }

    private class ThreadFun extends Thread {
        @Override
        public void run() {
            FlowEngine.this.RunFlowProcess();
        }
    }

    /// <summary>
    /// 停止流程
    /// </summary>
    public void StopFlow() {
        isRunning = false;
        MainStatusManage.setIfStopOperation(true);
        if (ThreadF != null) {
            try {
                Thread.sleep(300);
                ThreadF.stop();
                ThreadF = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        MainStatusManage.ChangeStatus(MeasureStatus.Idle);
        MainStatusManage.setIfStopOperation(false);
    }

    /// <summary>
    /// 获取当前运行流程中的当前动作
    /// </summary>
    public IActionInfo getCurrentAction() {
        IActionInfo current = null;
        if (RunM != null) {
            current = RunM.getCurrentActionInfo();
        }

        return current;
    }

    MeasureConfig _MeasureCFG = null;
    int[] MeasureTimes = null;

    /// <summary>
    /// 进入自动标定
    /// </summary>
    void DoAutoCalibrate(Date dt) {
        CalibratedTime.add(new Date(dt.getYear(), dt.getMonth(), dt.getDate(),
                CalibrateCfgController.getCalibrateConfigController().GetCalibrateConfig().getCalibrateTime(), 0, 0));

        MainStatusManage.ChangeStatus(MeasureStatus.StandardOne, CalibrateCfgController.getCalibrateConfigController().GetCalibrateConfig().getContentOne());
        RunM.RunFlow(CalibrateFlow1.getId().intValue());

        MainStatusManage.ChangeStatus(MeasureStatus.StandardTwo, CalibrateCfgController.getCalibrateConfigController().GetCalibrateConfig().getContentTwo());
        RunM.RunFlow(CalibrateFlow2.getId().intValue());

        CalibrateCoefficient cc = CalibrateManage.GetCalibrateCalculateKB();
        CalibrateManage.ChangeCoefficient(cc.getKValue(), cc.getBValue());
    }

    /// <summary>
    /// 进入样品
    /// </summary>
    void DoMeasureSample() {
        MainStatusManage.ChangeStatus(MeasureStatus.Sample);
        RunM.RunFlow(SampleFlow.getId().intValue());
    }

    List<Date> CalibratedTime = new ArrayList<>();

    /// <summary>
    /// 检查是否达到标定间隔和时间
    /// </summary>
    /// <param name="dt"></param>
    /// <returns></returns>
    boolean CheckArriveCalibrateTime(Date dt) {
        boolean ifArriveCalibrateTime = false;
        if (CalibrateCfgController.getCalibrateConfigController().GetCalibrateConfig().isAutoCalibrate()) {
            if (dt.getHours() >= CalibrateCfgController.getCalibrateConfigController().GetCalibrateConfig().getCalibrateTime()) {
                if (CalibratedTime.size() > 0) {
                    if (DateOperator.addDay(dt, -CalibrateCfgController.getCalibrateConfigController().GetCalibrateConfig().getCalibrateIntervalTime()).getTime() >= CalibratedTime.get(CalibratedTime.size() - 1).getTime()) {
                        ifArriveCalibrateTime = true;
                    }
                } else {
                    ifArriveCalibrateTime = CalibrateCfgController.getCalibrateConfigController().GetCalibrateConfig().isFirstLoopCalibrate();
                }
            }
        }
        return ifArriveCalibrateTime;
    }

    void WaitForIntervalMode() {
        while (!MainStatusManage.isIfStopOperation()) {
            Date dt = DateOperator.getNow();
            if (CheckArriveCalibrateTime(dt)) {
                //开始标定
                DoAutoCalibrate(dt);
                break;
            } else if (DateOperator.getDateMinuteSub(dt, LastMeasureTime) >= _MeasureCFG.getIntervalTime()) {
                LastMeasureTime = DateOperator.getNow();
                //开始测量
                DoMeasureSample();
                break;
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {

                }
            }
        }
    }

    void WaitForPointTimeMode() {
        while (!MainStatusManage.isIfStopOperation()) {
            if (MeasureTimes != null && MeasureTimes.length > 0) {
                Date dt = DateOperator.getNow();
                if (CheckArriveCalibrateTime(dt)) {
                    //开始标定
                    DoAutoCalibrate(dt);
                    break;
                } else {
                    boolean arriveMeasurePointTime = false;
                    for (int i = 0; i < MeasureTimes.length; i++) {
                        if (dt.getHours() == MeasureTimes[i] && dt.getMinutes() == 0
                                && dt.getSeconds() >= 0 && dt.getSeconds() <= 59) {
                            arriveMeasurePointTime = true;
                            //开始测量
                            DoMeasureSample();
                            break;
                        }
                    }
                    if (!arriveMeasurePointTime) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                        }
                    }
                }
            }
        }
    }

    /**
     * 整点测量模式时等待时间到达
     */
    void WaitPointTime() {
        if (_MeasureCFG != null) {
            if (_MeasureCFG.getMode() == MeasureMode.IntervalMode.value()) {
                WaitForIntervalMode();
            }
            if (_MeasureCFG.getMode() == MeasureMode.PointTimeMode.value()) {
                WaitForPointTimeMode();
            }
        }
    }

    String _ErrorMsg = "";

    public String getErrorMsg() {
        return _ErrorMsg;
    }

    boolean ifFirstLoop = false;
    Date LastMeasureTime = DateOperator.getNow();

    /**
     * 运行流程
     */
    void RunFlowProcess() {
        try {
            if (MainStatusManage.getCurrentSatus() == MeasureStatus.Sample) {
                CalibratedTime.clear();
                ifFirstLoop = true;
                while (!MainStatusManage.isIfStopOperation()) {
                    if (ifFirstLoop) {
                        if (_MeasureCFG.getMode() == MeasureMode.IntervalMode.value()) {
                            LastMeasureTime = DateOperator.addMinute(DateOperator.getNow(), -(2 * _MeasureCFG.getIntervalTime()));
                        }
                        ifFirstLoop = false;
                    }

                    WaitPointTime();
                }
            } else if (MainStatusManage.getCurrentSatus() == MeasureStatus.StandardOne) {
                RunM.RunFlow(CalibrateFlow1.getId().intValue());
            } else if (MainStatusManage.getCurrentSatus() == MeasureStatus.StandardTwo) {
                RunM.RunFlow(CalibrateFlow2.getId().intValue());
            }
        } catch (Exception ex) {
            _ErrorMsg = ex.getMessage();
        }

        MainStatusManage.ChangeStatus(MeasureStatus.Idle);
        isRunning = false;
        isOver = true;
        ThreadF = null;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
