package skyray.waol2000.Core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import skyray.waol2000.datamodel.ActionBundle;
import skyray.waol2000.datamodel.FlowDetail;
import skyray.waol2000.datamodel.IActionInfo;

/**
 * 流程执行控制类
 */

public class RunFlowManage {
    public ActionBundle CurrentActionBundle = null;

    public IActionInfo getCurrentActionInfo() {
        return ActionBundleManage.getCurrentActionInfo();
    }

    public void RunFlow(int flowId) {
        List<FlowDetail> allProcess = FlowDetail.find(FlowDetail.class, " Flow_ID = ? ", String.valueOf(flowId));

        List<ActionBundle> bundles = null;

        if (allProcess != null && allProcess.size() > 0) {
            Collections.sort(allProcess);

            bundles = new ArrayList<>();
            for (int i = 0; i < allProcess.size(); i++) {
                ActionBundle ab = ActionBundle.findById(ActionBundle.class, allProcess.get(i).getActionBundleID());
                bundles.add(ab);
            }
            for (int i = 0; i < bundles.size(); i++) {
                if (!MainStatusManage.isIfStopOperation()) {
                    CurrentActionBundle = bundles.get(i);
                    ActionBundleManage.runActionBundle(bundles.get(i));
                    for (int m = 0; m < 3; m++) {
                        if (!MainStatusManage.isIfStopOperation()) {
                            try {
                                Thread.sleep(170);
                            } catch (Exception ex) {
                            }
                        }
                    }
                }
            }
        }
    }
}
