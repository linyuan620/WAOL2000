package skyray.waol2000.datamodel;

import android.support.annotation.NonNull;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;

/**
 * 动作与动作关系  集合
 */
public class ActionBundleDetail extends SugarRecord implements Comparable<ActionBundleDetail>, IEqualble<ActionBundleDetail> {
    @NotNull
    private ActionBundle BundleInfo = null;
    @NotNull
    private int ActionID = 0;
    @NotNull
    private ActionType ActionTypeV = ActionType.Valve;
    @NotNull
    private int OrderValue = 0;

    public ActionBundleDetail() {
    }

    public ActionBundleDetail(ActionBundle bundleInfo, int actionID, ActionType actionTypeV, int orderValue) {
        setBundleInfo(bundleInfo);
        setActionID(actionID);
        setActionTypeV(actionTypeV);
        setOrderValue(orderValue);
    }

    public ActionBundle getBundleInfo() {
        return BundleInfo;
    }

    public void setBundleInfo(ActionBundle bundleInfo) {
        BundleInfo = bundleInfo;
    }

    public int getActionID() {
        return ActionID;
    }

    public void setActionID(int actionID) {
        ActionID = actionID;
    }

    public ActionType getActionTypeV() {
        return ActionTypeV;
    }

    public void setActionTypeV(ActionType actionTypeV) {
        ActionTypeV = actionTypeV;
    }

    public int getOrderValue() {
        return OrderValue;
    }

    public void setOrderValue(int orderValue) {
        OrderValue = orderValue;
    }

    @Override
    public String toString() {
        return BundleInfo.getBundleName();
    }

    @Override
    public boolean isEqual(ActionBundleDetail actionBundleDetail) {
        boolean result = false;
        if (actionBundleDetail != null) {
            result = (BundleInfo.equals(actionBundleDetail.BundleInfo) && getActionID() == actionBundleDetail.getActionID());
        }
        return result;
    }

    @Override
    public int compareTo(@NonNull ActionBundleDetail o) {
        if (getBundleInfo() == o.getBundleInfo()) {
            return Integer.compare(getOrderValue(), o.getOrderValue());
        }
        return Integer.compare(getBundleInfo().getId().intValue(), o.getBundleInfo().getId().intValue());
    }
}
