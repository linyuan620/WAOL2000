package skyray.waol2000.datamodel;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;

/**
 * 进样信息类
 * Created by PengJianbo on 2017/8/30.
 */

public class InjectionInfo extends SugarRecord implements IActionInfo,IEqualble<InjectionInfo> {

    @NotNull
    private String InjectionName = "";
    @NotNull
    private int InputPort = 0;
    @NotNull
    private int OutputPort = 0;
    @NotNull
    private int Height = 0;

    public String getInjectionName() {
        return InjectionName;
    }

    public void setInjectionName(String injectionName) {
        InjectionName = injectionName;
    }

    public int getInputPort() {
        return InputPort;
    }

    public void setInputPort(int inputPort) {
        InputPort = inputPort;
    }

    public int getOutputPort() {
        return OutputPort;
    }

    public void setOutputPort(int outputPort) {
        OutputPort = outputPort;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        Height = height;
    }

    public InjectionInfo() {
    }

    public InjectionInfo(String injectionName, int inputPort, int outputPort, int height) {
        setInjectionName(injectionName);
        setInputPort(inputPort);
        setOutputPort(outputPort);
        setHeight(height);
    }

    @Override
    public String toString() {
        return InjectionName;
    }

    @Override
    public boolean isEqual(InjectionInfo injectionInfo) {
        boolean result = false;
        if (injectionInfo != null) {
            result = getInjectionName().equals(injectionInfo.getInjectionName());
        }
        return result;
    }
}
