package skyray.waol2000.Core;

import skyray.waol2000.controller.SerialMaster;

/**
 * 连接管理
 */

public class ConnectionManager {
    /**
     * 标志是否连接过设备
     */
    private static boolean _IfConnected = false;

    public static void connect() {
        if (!_IfConnected) {

            SerialMaster.InitConnection();
            _IfConnected = true;
        }
    }
}
