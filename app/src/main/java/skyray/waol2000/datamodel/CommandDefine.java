package skyray.waol2000.datamodel;

/**
 * Created by Administrator on 2017/6/10.
 */

public class CommandDefine {
    public static final short ValveBeginAddress = 50;

    public static final int ValveOpen = 0;
    public static final int ValveClose = 1;

    public static final int PumpOpen = 1;
    public static final int PumpClose = 0;
    public static final int PumpOperation = 100;
    public static final int PumpImpulseCount = 110;
    public static  final  short PumpSpeed = 130;

    public static final int ADOperation = 35;
    public static final int ADSampleAddr = 70;

    /**
     * 系统状态地址
     */
    public static final int SystemStatusAddr = 150;
    /**
     * 进样使能地址
     */
    public static final int InjectionOperation = 151;

    /**
     * 进样参数开始地址 160 试剂输入端口，161 试剂输出端口，162 取液高度
     */
    public static final int InjectionParameterStartAddr = 160;
}
