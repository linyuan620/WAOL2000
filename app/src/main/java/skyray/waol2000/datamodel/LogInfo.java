package skyray.waol2000.datamodel;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;

import java.util.Date;

import skyray.waol2000.Utils.DateTimeFormater;

/**
 * 日志信息
 */

public class LogInfo extends SugarRecord {
    @NotNull
    private Date logTime = null;
    @NotNull
    private String message = "";

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return DateTimeFormater.getTimeString(logTime) + "\t\t" + message;
    }
}
