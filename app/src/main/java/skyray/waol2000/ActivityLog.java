package skyray.waol2000;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.eowise.recyclerview.stickyheaders.StickyHeadersBuilder;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;

import java.util.Date;

import skyray.waol2000.Adapter.LogBigramHeaderAdapter;
import skyray.waol2000.Adapter.LogAdapter;
import skyray.waol2000.Utils.DateOperator;
import skyray.waol2000.Utils.FullScreenController;
import skyray.waol2000.controller.LogDataProvider;

public class ActivityLog extends AppCompatActivity {

    DatePicker dtBeginDate = null;
    TimePicker tpBeginTime = null;
    DatePicker dtEndTime = null;
    TimePicker tpEndTime = null;

    private void initControls() {
        Date dtNow = DateOperator.getNow();
        dtBeginDate = (DatePicker) findViewById(R.id.dp_begin);
        Date date = DateOperator.addDay(dtNow, -7);
        dtBeginDate.updateDate(date.getYear() + 1900, date.getMonth(), date.getDate());

        tpBeginTime = (TimePicker) findViewById(R.id.tp_begin);
        tpBeginTime.setIs24HourView(true);
        tpBeginTime.setCurrentHour(date.getHours());
        tpBeginTime.setCurrentMinute(date.getMinutes());

        dtEndTime = (DatePicker) findViewById(R.id.dp_end);
        dtEndTime.updateDate(dtNow.getYear() + 1900, dtNow.getMonth(), dtNow.getDate());

        tpEndTime = (TimePicker) findViewById(R.id.tp_end);
        tpEndTime.setIs24HourView(true);
        tpEndTime.setCurrentHour(dtNow.getHours());
        tpEndTime.setCurrentMinute(dtNow.getMinutes());
    }

    private StickyHeadersItemDecoration top;
    private RecyclerView list;
    private LogDataProvider logDataProvider = null;
    private LogAdapter logAdapter = null;

    private void initList() {
        list = (RecyclerView) findViewById(R.id.log_list);

        list.setLayoutManager(new LinearLayoutManager(ActivityLog.this, LinearLayoutManager.VERTICAL, false));

        logDataProvider = new LogDataProvider();
        logAdapter = new LogAdapter(this, logDataProvider);

        top = new StickyHeadersBuilder()
                .setAdapter(logAdapter)
                .setRecyclerView(list)
                .setStickyHeadersAdapter(new LogBigramHeaderAdapter(logDataProvider.getItems()))
                .build();

        list.setAdapter(logAdapter);
        list.addItemDecoration(top);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FullScreenController.setNoTitleAndHideNavigationBar(this);
        setContentView(R.layout.activity_log);

        initControls();
        initList();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityLog.this.finish();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            FullScreenController.hideNavigationBar(this);
        }
    }

}
