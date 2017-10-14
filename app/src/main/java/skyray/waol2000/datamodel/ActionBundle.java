package skyray.waol2000.datamodel;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;

/**
 * 动作包  简单动作类的集合
 */
public class ActionBundle extends SugarRecord implements IEqualble<ActionBundle> {
    @NotNull
    private String BundleName = "";

    public ActionBundle() {
    }

    public ActionBundle(String bundleName) {
        setBundleName(bundleName);
    }

    public String getBundleName() {
        return BundleName;
    }

    public void setBundleName(String bundleName) {
        BundleName = bundleName;
    }

    @Override
    public String toString() {
        return BundleName;
    }

    @Override
    public boolean isEqual(ActionBundle actionBundle) {
        boolean result = false;
        if (actionBundle != null) {
            result = getBundleName().equals(actionBundle.getBundleName());
        }
        return result;
    }
}
