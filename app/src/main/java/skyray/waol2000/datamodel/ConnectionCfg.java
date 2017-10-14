package skyray.waol2000.datamodel;

import com.orm.SugarRecord;

/**
 * 串口连接配置类
 */
public class ConnectionCfg extends SugarRecord {

    private int SlaveID  = 1;
    private String ComPort = "COM1";
    private int BautRate = 9600;

    public ConnectionCfg() {
    }

    public ConnectionCfg(int slaveID, String comPort, int bautRate) {
        setSlaveID(slaveID);
        setComPort(comPort);
        setBautRate(bautRate);
    }

    public int getSlaveID() {
        return SlaveID;
    }

    public void setSlaveID(int slaveID) {
        SlaveID = slaveID;
    }

    /**
     * COM端口号
     */
    public String getComPort() {
        return ComPort;
    }

    public void setComPort(String comPort) {
        ComPort = comPort;
    }

    /**
     * 波特率
     */
    public int getBautRate() {
        return BautRate;
    }

    public void setBautRate(int bautRate) {
        BautRate = bautRate;
    }
}
