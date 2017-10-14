package skyray.waol2000.controller;

import skyray.waol2000.Core.MainStatusManage;
import skyray.waol2000.datamodel.ActionWaitTime;
import skyray.waol2000.datamodel.IActionInfo;

/**
 * 等待时间执行类
 */

public class ActionWaitTimeRunner implements IActionRunner {
    @Override
    public void runAction(IActionInfo ainfo) {
        if (ainfo != null && ainfo instanceof ActionWaitTime) {

            ActionWaitTime aw = (ActionWaitTime) ainfo;
            int waitTime = aw.getTimeValue() * 300; //每秒等待5次200毫秒

            for (int i = 0; i < waitTime; i++) {
                if (!MainStatusManage.isIfStopOperation()) {
                    try {
                        Thread.sleep(200);
                    } catch (Exception ex) {
                    }
                }
                else
                {
                    break;
                }
            }
        }
    }
}
