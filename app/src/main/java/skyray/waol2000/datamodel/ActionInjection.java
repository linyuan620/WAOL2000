package skyray.waol2000.datamodel;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;

/**
 * 进样动作类
 * Created by PengJianbo on 2017/8/30.
 */

public class ActionInjection extends SugarRecord implements IActionInfo ,IEqualble<ActionInjection> {

    @NotNull
    private String ActionName = "";

    private InjectionInfo InjectionValue = null;

    @NotNull
    private int DefaultInjectionID = 0;

    public String getActionName() {
        return ActionName;
    }

    public void setActionName(String actionName) {
        ActionName = actionName;
    }

    public InjectionInfo getInjectionValue() {
        return InjectionValue;
    }

    public void setInjectionValue(InjectionInfo injectionValue) {
        InjectionValue = injectionValue;
    }

    public int getDefaultInjectionID() {
        return DefaultInjectionID;
    }

    public void setDefaultInjectionID(int defaultInjectionID) {
        DefaultInjectionID = defaultInjectionID;
    }

    public ActionInjection(){}

    public ActionInjection(String actionName,InjectionInfo injectionInfo)
    {
        setActionName(actionName);
        setInjectionValue(injectionInfo);
    }

    @Override
    public String toString() {
        return ActionName;
    }

    @Override
    public boolean isEqual(ActionInjection actionInjection) {
        boolean result = false;
        if (actionInjection != null) {
            result = getActionName().equals(actionInjection.getActionName());
        }
        return result;
    }
}
