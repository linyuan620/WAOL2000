package skyray.waol2000.Core;

import java.util.Date;
import java.util.List;

import skyray.waol2000.Utils.DateTimeFormater;
import skyray.waol2000.datamodel.MeasureData;

/**
 * Created by Administrator on 2017/6/28.
 */

public class MeasureDataManage {
    public static void addMeasureData(MeasureData mdata) {
        if (mdata != null) {
            mdata.save();
        }
    }

    public List<MeasureData> getAllLogInfo() {
        return MeasureData.listAll(MeasureData.class);
    }

    public List<MeasureData> getLogInfoByTime(Date begin, Date end) {
        return MeasureData.find(MeasureData.class, " Measure_Time between ? and ? ",
                DateTimeFormater.getTimeString(begin), DateTimeFormater.getTimeString(end));
    }
}
