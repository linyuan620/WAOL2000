package skyray.waol2000.controller;

import skyray.waol2000.datamodel.IActionInfo;

/**
 * 基础动作执行基类
 */
public interface IActionRunner {

    /**
     * 运行动作
     *
     * @param ainfo 动作信息
     */
    void runAction(IActionInfo ainfo);
}
