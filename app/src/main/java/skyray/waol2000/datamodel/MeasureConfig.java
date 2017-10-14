package skyray.waol2000.datamodel;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;

/**
 * 测量配置
 */

public class MeasureConfig extends SugarRecord {
    @NotNull
    private int Mode = 0;

    @NotNull
    private int IntervalTime = 50;

    @NotNull
    private String PointTimes = "";

    public MeasureConfig() {
    }

    public MeasureConfig(int mode, int intervalTime, String pointTimes) {
        setMode(mode);
        setIntervalTime(intervalTime);
        setPointTimes(pointTimes);
    }

    /**
     * 启动模式 0为间隔模式 1为整点时间模式 2为触发模式
     */
    public int getMode() {
        return Mode;
    }

    public void setMode(int mode) {
        Mode = mode;
    }

    /**
     * 间隔模式时 间隔时间分钟
     */
    public int getIntervalTime() {
        return IntervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        IntervalTime = intervalTime;
    }

    /**
     * 整点时间时 整点值 用逗号隔开
     */
    public String getPointTimes() {
        return PointTimes;
    }

    public void setPointTimes(String pointTimes) {
        PointTimes = pointTimes;
    }
}
