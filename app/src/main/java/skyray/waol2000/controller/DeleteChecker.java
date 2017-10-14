package skyray.waol2000.controller;

import java.util.List;

import skyray.waol2000.datamodel.ActionADSample;
import skyray.waol2000.datamodel.ActionBundle;
import skyray.waol2000.datamodel.ActionBundleDetail;
import skyray.waol2000.datamodel.ActionInjection;
import skyray.waol2000.datamodel.ActionPump;
import skyray.waol2000.datamodel.ActionType;
import skyray.waol2000.datamodel.ActionValve;
import skyray.waol2000.datamodel.ActionWaitTime;
import skyray.waol2000.datamodel.FlowDetail;
import skyray.waol2000.datamodel.FlowInfo;
import skyray.waol2000.datamodel.IActionInfo;
import skyray.waol2000.datamodel.InjectionInfo;
import skyray.waol2000.datamodel.PumpInfo;
import skyray.waol2000.datamodel.ValveInfo;

/**
 * Created by PengJianbo on 2017/7/13.
 */

public class DeleteChecker {

    /**
     * 检查是否有阀动作使用阀信息
     *
     * @param valveInfo 阀信息
     * @return
     */
    public static boolean checkValveInfoUsing(ValveInfo valveInfo) {
        boolean using = false;
        if (valveInfo != null) {
            List<ActionValve> list = ActionValve.find(ActionValve.class, " VALVE_INFO_V = ? ", valveInfo.getId().toString());
            if (list != null && list.size() > 0) {
                using = true;
            }
        }
        return using;
    }

    /**
     * 检查是否有泵动作使用泵信息
     *
     * @param pumpInfo 泵信息
     * @return
     */
    public static boolean checkPumpInfoUsing(PumpInfo pumpInfo) {
        boolean using = false;
        if (pumpInfo != null) {
            List<ActionPump> list = ActionPump.find(ActionPump.class, " PUMP_INFO_V = ? ", pumpInfo.getId().toString());
            if (list != null && list.size() > 0) {
                using = true;
            }
        }
        return using;
    }

    /**
     * 检查进样信息是否被使用
     *
     * @param injectionInfo 进样信息
     * @return
     */
    public static boolean checkInjectionInfoUsing(InjectionInfo injectionInfo) {
        boolean using = false;
        if (injectionInfo != null) {
            List<ActionInjection> list = ActionInjection.find(ActionInjection.class, " Injection_Value = ? ", injectionInfo.getId().toString());
            if (list != null && list.size() > 0) {
                using = true;
            }
        }
        return using;
    }

    /**
     * 检查阀、泵、AD采样、等待时间动作是否使用中
     *
     * @param actionInfo
     * @return
     */
    public static boolean checkActionUsing(IActionInfo actionInfo) {
        boolean using = false;
        if (actionInfo != null) {
            String type = "";
            String id = "";
            Class c = actionInfo.getClass();
            if (c.equals(ActionValve.class)) {
                type = ActionType.Valve.toString();
                id = ((ActionValve) actionInfo).getId().toString();
            } else if (c.equals(ActionPump.class)) {
                type = ActionType.Pump.toString();
                id = ((ActionPump) actionInfo).getId().toString();
            } else if (c.equals(ActionADSample.class)) {
                type = ActionType.ADSample.toString();
                id = ((ActionADSample) actionInfo).getId().toString();
            } else if (c.equals(ActionWaitTime.class)) {
                type = ActionType.WaitTime.toString();
                id = ((ActionWaitTime) actionInfo).getId().toString();
            }
            else if(c.equals(ActionInjection.class)) {
                type = ActionType.Injection.toString();
                id = ((ActionInjection) actionInfo).getId().toString();
            }
            List<ActionBundleDetail> actionBundleDetail = ActionBundleDetail.find(ActionBundleDetail.class,
                    " ACTION_TYPE_V = ? and ACTION_ID = ? ", type, id);
            if (actionBundleDetail != null && actionBundleDetail.size() > 0) {
                using = true;
            }
        }
        return using;
    }

    /**
     * 检查动作包是否使用中
     *
     * @param actionBundle
     * @return
     */
    public static boolean checkActionBundleUsing(ActionBundle actionBundle) {
        boolean using = false;
        if (actionBundle != null) {
            List<FlowDetail> flowDetail = FlowDetail.find(FlowDetail.class,
                    " ACTION_BUNDLE_ID = ? ", actionBundle.getId().toString());
            if (flowDetail != null && flowDetail.size() > 0) {
                using = true;
            }

            if (!using) {
                for (int i = 0; i < ActionBundleDetailController.getASC().getAllInfos().size(); i++) {
                    if (ActionBundleDetailController.getASC().getAllInfos().get(i).getBundleInfo().getBundleName().equals(actionBundle.getBundleName())) {
                        using = true;
                        break;
                    }
                }
            }
        }
        return using;
    }

    /**
     * 检查流程是否使用中
     *
     * @param flowInfo
     * @return
     */
    public static boolean checkFlowUsing(FlowInfo flowInfo) {
        boolean using = false;
        if (flowInfo != null) {
            List<FlowDetail> flowDetail = FlowDetail.find(FlowDetail.class,
                    " FLOW_ID = ? ", flowInfo.getId().toString());
            if (flowDetail != null && flowDetail.size() > 0) {
                using = true;
            }
        }
        return using;
    }

}
