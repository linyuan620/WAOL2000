package skyray.waol2000;

import android.icu.util.Measure;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.eowise.recyclerview.stickyheaders.StickyHeadersBuilder;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;

import java.util.Date;

import skyray.waol2000.Adapter.HistoryAdapter;
import skyray.waol2000.Adapter.HistoryBigramHeaderAdapter;
import skyray.waol2000.Utils.DateOperator;
import skyray.waol2000.Utils.FullScreenController;
import skyray.waol2000.controller.HistoryDataProvider;
import skyray.waol2000.datamodel.MeasureData;

public class ActivityHistory extends AppCompatActivity implements View.OnClickListener {

    DatePicker dtBeginDate = null;
    TimePicker tpBeginTime = null;
    DatePicker dtEndTime = null;
    TimePicker tpEndTime = null;
    Button btnQueryHistory = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FullScreenController.setNoTitleAndHideNavigationBar(this);
        setContentView(R.layout.activity_history);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityHistory.this.finish();
            }
        });

        initControls();
    }

    private StickyHeadersItemDecoration top;
    private RecyclerView list;
    private HistoryDataProvider historyDataProvider = null;
    private HistoryAdapter historyAdapter = null;

    private void initControls() {
        Date dtNow = DateOperator.getNow();
        dtBeginDate = (DatePicker) findViewById(R.id.dp_begin);
        Date date = DateOperator.addDay(dtNow, -7);
        int y = date.getYear() + 1900;
        dtBeginDate.updateDate(y, date.getMonth(), date.getDate());

        tpBeginTime = (TimePicker) findViewById(R.id.tp_begin);
        tpBeginTime.setIs24HourView(true);
        tpBeginTime.setCurrentHour(date.getHours());
        tpBeginTime.setCurrentMinute(date.getMinutes());

        dtEndTime = (DatePicker) findViewById(R.id.dp_end);
        int ye = dtNow.getYear() + 1900;
        dtEndTime.updateDate(ye, dtNow.getMonth(), dtNow.getDate());

        tpEndTime = (TimePicker) findViewById(R.id.tp_end);
        tpEndTime.setIs24HourView(true);
        tpEndTime.setCurrentHour(dtNow.getHours());
        tpEndTime.setCurrentMinute(dtNow.getMinutes());

        btnQueryHistory = (Button) findViewById(R.id.btnQueryHistory);
        btnQueryHistory.setOnClickListener(this);

        list = (RecyclerView) findViewById(R.id.history_list);

        list.setLayoutManager(new LinearLayoutManager(ActivityHistory.this, LinearLayoutManager.VERTICAL, false));

        historyDataProvider = new HistoryDataProvider();
        historyAdapter = new HistoryAdapter(this, historyDataProvider);

        top = new StickyHeadersBuilder()
                .setAdapter(historyAdapter)
                .setRecyclerView(list)
                .setStickyHeadersAdapter(new HistoryBigramHeaderAdapter(historyDataProvider.getItems()))
                .build();

        list.setAdapter(historyAdapter);
        list.addItemDecoration(top);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            FullScreenController.hideNavigationBar(this);
        }
    }

    /**
     * 查询历史数据
     */
    private void queryHistoryData() {
        Date begin = new Date(dtBeginDate.getYear()-1900,
                dtBeginDate.getMonth(), dtBeginDate.getDayOfMonth(),
                tpBeginTime.getCurrentHour(), tpBeginTime.getCurrentMinute(), 0);

        Date end = new Date(dtEndTime.getYear()-1900,
                dtEndTime.getMonth(), dtEndTime.getDayOfMonth(),
                tpEndTime.getCurrentHour(), tpEndTime.getCurrentMinute(), 59);

        historyDataProvider.SetData(begin, end);
        historyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnQueryHistory) {
            queryHistoryData();
        }
    }
}
