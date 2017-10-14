package skyray.waol2000.Core;

import java.util.LinkedList;
import java.util.Queue;

import skyray.waol2000.datamodel.MeasureData;

/**
 * 测量结果缓存
 */

public class MeasureResultCache {

    private Queue<MeasureData> MeasureResults = new LinkedList<MeasureData>();

    private static MeasureResultCache _MCache = new MeasureResultCache();

    private MeasureResultCache() {
    }

    public static MeasureResultCache getMCache() {
        return _MCache;
    }

    /**
     * 添加
     * @param measureData
     */
    public void addMeasureData(MeasureData measureData) {
        MeasureResults.add(measureData);
    }

    /**
     * 获取当前测量结果
      * @return
     */
    public MeasureData getMeasureData() {
        if (MeasureResults.size() > 0) {
            if (MeasureResults.size() > 1) {
                return MeasureResults.poll();
            } else {
                return MeasureResults.peek();
            }
        }
        return null;
    }

    /**
     * 清空所有结果
     */
    public void clearMeasureData() {
        MeasureResults.clear();
    }

}
