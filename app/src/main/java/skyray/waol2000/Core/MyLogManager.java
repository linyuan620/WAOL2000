package skyray.waol2000.Core;

import java.util.Date;
import java.util.List;

import skyray.waol2000.Utils.DateOperator;
import skyray.waol2000.Utils.DateTimeFormater;
import skyray.waol2000.datamodel.LogInfo;

/**
 * Created by Administrator on 2017/6/28.
 */

public class MyLogManager {
    public static  void addLogInfo(LogInfo logInfo) {
        if (logInfo != null) {
            logInfo.save();
        }
    }

    public static void addLogInfo(String msg)
    {
        LogInfo log = new LogInfo();
        log.setLogTime(DateOperator.getNow());
        log.setMessage(msg);
        log.save();
    }

    public List<LogInfo> getAllLogInfo()
    {
        return LogInfo.listAll(LogInfo.class);
    }

    public List<LogInfo> getLogInfoByTime(Date begin, Date end) {
        return LogInfo.find(LogInfo.class, " log_Time between ? and ? ",
                DateTimeFormater.getTimeString(begin), DateTimeFormater.getTimeString(end));
    }
}
