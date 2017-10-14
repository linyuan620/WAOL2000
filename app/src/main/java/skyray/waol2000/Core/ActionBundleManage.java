package skyray.waol2000.Core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import skyray.waol2000.controller.ActionADSampleController;
import skyray.waol2000.controller.ActionADSampleRunner;
import skyray.waol2000.controller.ActionBundleDetailController;
import skyray.waol2000.controller.ActionInjectionController;
import skyray.waol2000.controller.ActionInjectionRunner;
import skyray.waol2000.controller.ActionPumpController;
import skyray.waol2000.controller.ActionPumpRunner;
import skyray.waol2000.controller.ActionValveController;
import skyray.waol2000.controller.ActionValveRunner;
import skyray.waol2000.controller.ActionWaitTimeController;
import skyray.waol2000.controller.ActionWaitTimeRunner;
import skyray.waol2000.datamodel.ActionBundle;
import skyray.waol2000.datamodel.ActionBundleDetail;
import skyray.waol2000.datamodel.ActionType;
import skyray.waol2000.datamodel.IActionInfo;
import skyray.waol2000.controller.IActionRunner;

/**
 * 动作包执行
 */

public class ActionBundleManage {

    public static IActionInfo getCurrentActionInfo() {
        return aInfo;
    }

    static IActionInfo aInfo = null;

    public static void runActionBundle(ActionBundle d) {
        if (d != null) {
            List<ActionBundleDetail> details = new ArrayList<>();
            for (int i = 0; i < ActionBundleDetailController.getASC().getAllInfos().size(); i++) {
                if (ActionBundleDetailController.getASC().getAllInfos().get(i).getBundleInfo().getBundleName().equals(d.getBundleName())) {
                    details.add(ActionBundleDetailController.getASC().getAllInfos().get(i));
                }
            }

            Collections.sort(details);

            for (int i = 0; !MainStatusManage.isIfStopOperation() && i < details.size(); i++) {
                int aID = details.get(i).getActionID();
                ActionType at = details.get(i).getActionTypeV();
                IActionRunner runner = null;
                switch (at) {
                    case Valve:
                        aInfo = ActionValveController.getAVC().getItemByID(aID);// ActionValve.findById(ActionValve.class, aID);
                        runner = new ActionValveRunner();
                        break;
                    case Pump:
                        aInfo = ActionPumpController.getAPC().getItemByID(aID);// ActionPump.findById(ActionPump.class, aID);
                        runner = new ActionPumpRunner();
                        break;
                    case ADSample:
                        aInfo = ActionADSampleController.getASC().getItemByID(aID); //ActionADSample.findById(ActionADSample.class, aID);
                        runner = new ActionADSampleRunner();
                        break;
                    case WaitTime:
                        aInfo = ActionWaitTimeController.getASC().getItemByID(aID); //ActionWaitTime.findById(ActionWaitTime.class, aID);
                        runner = new ActionWaitTimeRunner();
                        break;
                    case Injection:
                        aInfo = ActionInjectionController.getAIC().getItemByID(aID); //ActionInjection.findById(ActionInjection.class, aID);
                        runner = new ActionInjectionRunner();
                        break;
                }
                runner.runAction(aInfo);
            }
        }
    }
}
