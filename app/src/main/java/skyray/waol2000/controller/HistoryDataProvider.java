package skyray.waol2000.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import skyray.waol2000.Utils.DateOperator;
import skyray.waol2000.datamodel.MeasureData;

/**
 * Created by aurel on 11/10/14.
 */
public class HistoryDataProvider {

    private LinkedHashMap<MeasureData, Boolean> items;
    private List<MeasureData> addedItems;

    public HistoryDataProvider() {
        this.items = new LinkedHashMap<MeasureData, Boolean>();
        this.addedItems = new ArrayList<MeasureData>();

        Date dtNow = DateOperator.getNow();
        SetData(DateOperator.addDay(dtNow, -7), dtNow);
    }

    private List<MeasureData> measureDatas = null;

    public void SetData(Date begin, Date end) {
        if (measureDatas != null) {
            measureDatas.clear();
            measureDatas = null;

            items.clear();
            addedItems.clear();
        }

        Calendar calendarBegin = Calendar.getInstance();
        calendarBegin.setTime(begin);

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(end);

        measureDatas = MeasureData.find(MeasureData.class, " MEASURE_TIME between ? and ? ",
                String.valueOf(calendarBegin.getTimeInMillis()),
                String.valueOf(calendarEnd.getTimeInMillis()));

        if (measureDatas != null && measureDatas.size() > 0) {
            MeasureData mdata = measureDatas.get(0);
            measureDatas.add(0, mdata);
            for (int i = 1; i < measureDatas.size(); i++) {
                items.put(measureDatas.get(i), true);
            }
        }

        buildAddedItems();
    }

    public List<MeasureData> getItems() {
        return addedItems;
    }

    public void remove(int position) {
        items.put(addedItems.get(position), false);
        buildAddedItems();
    }

    public int insertAfter(int position) {
        MeasureData addAfter = addedItems.get(position);
        Iterator<MeasureData> iterator = items.keySet().iterator();
        MeasureData next = iterator.next();

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
        for (Map.Entry<MeasureData, Boolean> entry : items.entrySet()) {
            if (entry.getValue()) {
                addedItems.add(entry.getKey());
            }
        }
        //Collections.sort(addedItems);
    }

    public void update(int position, MeasureData mdata) {
        addedItems.set(position, mdata);
    }
}
