package skyray.waol2000.datamodel;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;

/**
 * 阀基础数据信息
 */
public class ValveInfo extends SugarRecord implements IActionInfo,IEqualble<ValveInfo> {

    @NotNull
    private String ValveName;
    @NotNull
    private int CmdIndex = 0;

    public ValveInfo() {
    }

    public ValveInfo(String valveName, int cmdIndex) {
        setValveName(valveName);
        setCmdIndex(cmdIndex);
    }

    public String getValveName() {
        return ValveName;
    }

    public void setValveName(String valveName) {
        ValveName = valveName;
    }

    /**
     * 获取命令索引位
     */
    public int getCmdIndex() {
        return CmdIndex;
    }

    /**
     * 设置命令索引位
     *
     * @param cmdIndex 命令索引位
     */
    public void setCmdIndex(int cmdIndex) {
        CmdIndex = cmdIndex;
    }

    @Override
    public String toString() {
        return ValveName;
    }

    @Override
    public boolean isEqual(ValveInfo valveInfo) {
        boolean result = false;
        if (valveInfo != null) {
            result = getValveName().equals(valveInfo.getValveName());
        }
        return result;
    }
}
