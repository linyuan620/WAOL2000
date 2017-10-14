package skyray.waol2000.datamodel;

import android.support.annotation.NonNull;

import com.orm.SugarRecord;

/**
 * 流程详细信息类
 */

public class FlowDetail extends SugarRecord implements  Comparable<FlowDetail>,IEqualble<FlowDetail> {
    /**
     * 执行顺序值 从小到大执行
     */
    private int OrderValue = 0;

    public int getOrderValue() {
        return OrderValue;
    }

    public void setOrderValue(int orderValue) {
        OrderValue = orderValue;
    }

    /**
     * 流程编号
     */
    private int FlowID = 0;

    public int getFlowID() {
        return FlowID;
    }

    public void setFlowID(int flowID) {
        FlowID = flowID;
    }

    /**
     * 动作包编号
     */
    private int ActionBundleID = 0;

    public int getActionBundleID() {
        return ActionBundleID;
    }

    public void setActionBundleID(int actionBundleID) {
        ActionBundleID = actionBundleID;
    }

    @Override
    public int compareTo(@NonNull FlowDetail o) {
        if (getFlowID() == o.getFlowID()) {
            return Integer.compare(getOrderValue(), o.getOrderValue());
        }
        return Integer.compare(getFlowID(), o.getFlowID());
    }

    @Override
    public boolean isEqual(FlowDetail flowDetail) {
        boolean equal = false;
        if (flowDetail != null) {
            equal = (getFlowID() == flowDetail.getFlowID() && getActionBundleID() == flowDetail.getActionBundleID()
                    && getOrderValue() == flowDetail.getOrderValue());
        }
        return equal;
    }
}
