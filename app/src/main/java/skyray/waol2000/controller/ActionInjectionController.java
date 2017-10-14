package skyray.waol2000.controller;

import java.util.ArrayList;
import java.util.List;

import skyray.waol2000.datamodel.ActionInjection;

/**
 * Created by PengJianbo on 2017/8/30.
 * 进样动作 数据管理类
 */

public class ActionInjectionController extends DataControllerBase<ActionInjection> {

    @Override
    protected void InitClass() {
        cc = ActionInjection.class;
        order = " Injection_Value desc ";
    }

    private static ActionInjectionController AIC = new ActionInjectionController();

    public static ActionInjectionController getAIC() {
        AIC.SetDefaultInjection();
        return AIC;
    }

    private List<ActionInjection> defaultInjection = new ArrayList<>();

    public List<ActionInjection> getAllInfoContainDefault() {
        List<ActionInjection> a = new ArrayList<>();
        a.addAll(allInfos);
        a.addAll(defaultInjection);
        return a;
    }

    private boolean ifSettedDefaultInjection = false;

    private void SetDefaultInjection() {
        if (!ifSettedDefaultInjection) {
            String[] defaultName = {"计量管满管进样", "计量管满管排废", "计量管高液位进样", "计量管低液位进样", "消解罐排空", "消解罐清水进样"};
            short[] operationAddr = {152, 153, 154, 155, 156, 157};

            for (int i = 0; i < operationAddr.length; i++) {
                ActionInjection ainfo = new ActionInjection(defaultName[i], null);
                ainfo.setDefaultInjectionID(operationAddr[i]);
                ActionInjection aexist = getExistAV(ainfo);
                if (aexist == null) {
                    updateInfo(ainfo);
                    aexist = ainfo;
                }
                defaultInjection.add(aexist);
            }

            for (int i = 0; i < defaultName.length; i++) {
                allInfos.remove(allInfos.size() - 1);
            }

            ifSettedDefaultInjection = true;
        }
    }

    @Override
    protected void updateItem(ActionInjection exist, ActionInjection av) {
        exist.setActionName(av.getActionName());
        exist.setInjectionValue(av.getInjectionValue());
    }

    @Override
    public ActionInjection getItemByID(int id) {
        ActionInjection t = null;
        List<ActionInjection> infos = getAllInfoContainDefault();
        if (infos != null) {
            for (int i = 0; i < infos.size(); i++) {
                if (infos.get(i).getId().intValue() == id) {
                    t = infos.get(i);
                    break;
                }
            }
        }
        return t;
    }
}
