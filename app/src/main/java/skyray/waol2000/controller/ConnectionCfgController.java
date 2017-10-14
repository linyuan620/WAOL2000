package skyray.waol2000.controller;

import java.util.List;

import skyray.waol2000.datamodel.ConnectionCfg;

/**
 * 连接配置管理类
 * Created by Administrator on 2017/6/10.
 */

public class ConnectionCfgController {
    private ConnectionCfg CFG = new ConnectionCfg();

    public ConnectionCfg GetConnectionCfg() {
        return CFG;
    }

    private static ConnectionCfgController _CFGCtrl = null;

    /**
     * 加载配置并获取之
     *
     * @return
     */
    public static ConnectionCfgController GetConnectionCfgController() {
        if (_CFGCtrl == null) {
            _CFGCtrl = new ConnectionCfgController();
            List<ConnectionCfg> allConfigs = ConnectionCfg.listAll(ConnectionCfg.class);
            if (allConfigs != null && allConfigs.size() > 0) {
                _CFGCtrl.CFG = allConfigs.get(0);
            }
            if (_CFGCtrl.CFG == null) {
                _CFGCtrl.CFG = new ConnectionCfg();
            }
        }
        return _CFGCtrl;
    }

    public void SaveConnectionCfg(ConnectionCfg connectionCfg) {
        CFG.setSlaveID(connectionCfg.getSlaveID());
        CFG.setComPort(connectionCfg.getComPort());
        CFG.setBautRate(connectionCfg.getBautRate());
        CFG.save();
    }
}
