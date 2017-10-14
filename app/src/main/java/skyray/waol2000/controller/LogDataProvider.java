package skyray.waol2000.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import skyray.waol2000.Utils.DateOperator;
import skyray.waol2000.datamodel.LogInfo;

/**
 * Created by aurel on 11/10/14.
 */
public class LogDataProvider {

    private LinkedHashMap<LogInfo, Boolean> items;
    private List<LogInfo> addedItems;

    public LogDataProvider() {
        this.items = new LinkedHashMap<LogInfo, Boolean>();
        this.addedItems = new ArrayList<LogInfo>();

        Date dtNow = DateOperator.getNow();
        SetData(DateOperator.addDay(dtNow, -7), dtNow);
    }

    private List<LogInfo> LogInfos = null;

    public void SetData(Date begin, Date end) {
        if (LogInfos != null) {
            LogInfos.clear();
            LogInfos = null;

            items.clear();
            addedItems.clear();
        }

        Calendar calendarBegin = Calendar.getInstance();
        calendarBegin.setTime(begin);

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(end);

        LogInfos = LogInfo.find(LogInfo.class, " log_TIME between ? and ? ",
                String.valueOf(calendarBegin.getTimeInMillis()),
                String.valueOf(calendarEnd.getTimeInMillis()));

        if (LogInfos != null && LogInfos.size() > 0) {
            LogInfo mdata = LogInfos.get(0);
            LogInfos.add(0, mdata);
            for (int i = 1; i < LogInfos.size(); i++) {
                items.put(LogInfos.get(i), true);
            }
        }

        buildAddedItems();
    }

    public List<LogInfo> getItems() {
        return addedItems;
    }

    public void remove(int position) {
        items.put(addedItems.get(position), false);
        buildAddedItems();
    }

    public int insertAfter(int position) {
        LogInfo addAfter = addedItems.get(position);
        Iterator<LogInfo> iterator = items.keySet().iterator();
        LogInfo next = iterator.next();

        while (iterator.hasNext() && !next.equals(addAfter)) {
            next = iterator.next();
        }

        do {
            next = iterator.next();
        }
        while (iterator.hasNext() && items.get(next));

        items.put(next, true);
        buildAddedItems();

        return addedItems.lastIndexOf(next);
    }

    private void buildAddedItems() {
        addedItems.clear();
        for (Map.Entry<LogInfo, Boolean> entry : items.entrySet()) {
            if (entry.getValue()) {
                addedItems.add(entry.getKey());
            }
        }
        //Collections.sort(addedItems);
    }

    public void update(int position, LogInfo mdata) {
        addedItems.set(position, mdata);
    }
}
