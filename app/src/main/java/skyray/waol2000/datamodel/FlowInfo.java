package skyray.waol2000.datamodel;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;

/**
 * 流程数据类
 */
public class FlowInfo extends SugarRecord implements IEqualble<FlowInfo> {
    /**
     * 流程名称
     */
    @NotNull
    private String FlowName = "";

    /**
     * 流程类型 区分测量 校准 和 单步调试动作
     */
    @NotNull
    private FlowType FlowTypeV = FlowType.P;

    public FlowInfo() {
    }

    public FlowInfo(String flowName, FlowType flowTypeV) {
        setFlowName(flowName);
        setFlowTypeV(flowTypeV);
    }

    public String getFlowName() {
        return FlowName;
    }

    public void setFlowName(String flowName) {
        FlowName = flowName;
    }

    public FlowType getFlowTypeV() {
        return FlowTypeV;
    }

    public void setFlowTypeV(FlowType flowTypeV) {
        FlowTypeV = flowTypeV;
    }

    @Override
    public String toString() {
        return FlowName;
    }

    @Override
    public boolean isEqual(FlowInfo flowInfo) {
        boolean result = false;
        if (flowInfo != null) {
            result = getFlowName().equals(flowInfo.getFlowName());
        }
        return result;
    }
}
