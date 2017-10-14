package skyray.waol2000;

import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import skyray.progressbarlib.ProgressWheel;
import skyray.waol2000.Core.ConnectionManager;
import skyray.waol2000.Core.FlowEngine;
import skyray.waol2000.Core.MeasureResultCache;
import skyray.waol2000.Core.MyLogManager;
import skyray.waol2000.Utils.FullScreenController;
import skyray.waol2000.Utils.NumberPointManage;
import skyray.waol2000.Utils.SystemTimeShower;
import skyray.waol2000.Utils.SimpleMessageShower;
import skyray.waol2000.datamodel.IActionInfo;
import skyray.waol2000.datamodel.MeasureData;
import skyray.waol2000.datamodel.Messages;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 切换显示子窗体方法
     *
     * @param c 子窗体类型
     */
    public void showChildActivity(Class c) {
        Intent intent = new Intent(MainActivity.this, c);
        startActivity(intent);
    }

    /**
     * 显示仪表维护界面
     */
    public void showMaintainActivity(View view) {
        showChildActivity(ActivityMaintain.class);
    }

    /**
     * 显示历史数据界面
     */
    public void showHistoryActivity(View view) {
        showChildActivity(ActivityHistory.class);
    }

    /**
     * 显示运行日志界面
     */
    public void showLogActivity(View view) {
        showChildActivity(ActivityLog.class);
    }

    /**
     * 显示设置界面
     */
    public void showSettingActivity(View view) {
        showChildActivity(ActivitySetting.class);
    }

    public void exitApp(View view) {
        SugarORMApp.exit(MainActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FullScreenController.setNoTitleAndHideNavigationBar(this);
        setContentView(R.layout.activity_main);

        MyLogManager.addLogInfo("系统启动");

        initAllControls();
        SystemTimeShower.getSystemTimeShower().startShowSystemTime(handler);
        new myTester().start();
    }

    @Override
    protected void onDestroy() {
        SystemTimeShower.getSystemTimeShower().stopShowSystemTime();
        super.onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            FullScreenController.hideNavigationBar(this);
        }
    }

    @Override
    public void onBackPressed() {
        SugarORMApp.exit(this);
    }

    TextView tvSystemTime = null;
    TextView tvCurrentAction = null;
    TextView tvMeasureResult = null;
    Button btnStartMeasure = null;
    Button btnStopMeasure = null;

    private void initAllControls() {
        tvSystemTime = (TextView) findViewById(R.id.tvSystemTime);

        tvCurrentAction = (TextView) findViewById(R.id.tvCurrentAction);
        tvMeasureResult = (TextView) findViewById(R.id.tvCurrentResult);

        btnStartMeasure = (Button) findViewById(R.id.btnStartMeasure);
        btnStartMeasure.setOnClickListener(this);
        btnStopMeasure = (Button) findViewById(R.id.btnStopMeasure);
        btnStopMeasure.setOnClickListener(this);
    }

    private void showSystemTime(Object msg) {
        if (msg != null) {
            tvSystemTime.setText(msg.toString());
        }
    }

    private void showMeasureInfoInMain() {
        IActionInfo currentAction = FlowEngine.getFG().getCurrentAction();
        if (currentAction != null) {
            tvCurrentAction.setText(currentAction.toString());
        }
        MeasureData mdata = MeasureResultCache.getMCache().getMeasureData();
        if (mdata != null) {
            tvMeasureResult.setText(NumberPointManage.toString(mdata.getMeasureValue(), 2));
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Messages.ShowSystemTime_Msg:
                    showSystemTime(msg.obj);
                    break;
                case Messages.Measure_Msg:
                    showMeasureInfoInMain();
                    break;
                default:
                    break;
            }
        }
    };

    private void startMeasure() {
        String msg = FlowEngine.getFG().StartMeasure();
        if (msg.equals("")) {
            startShowMeasureStatus();
            btnStopMeasure.setEnabled(true);
            btnStartMeasure.setEnabled(false);
            MyLogManager.addLogInfo("开始测量");
        } else {
            SimpleMessageShower.showMsg(this, msg);
        }
    }

    private void stopMeasure() {
        stopShowMeasureStatus();
        FlowEngine.getFG().StopFlow();
        btnStartMeasure.setEnabled(true);
        btnStopMeasure.setEnabled(false);
        MyLogManager.addLogInfo("停止测量");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartMeasure:
                startMeasure();
                break;
            case R.id.btnStopMeasure:
                stopMeasure();
                break;
        }
    }

    boolean ifStopShowMeasureStatus = false;

    private void sendShowMeasureStatusMsg() {
        while (!ifStopShowMeasureStatus) {
            Message msg = Message.obtain(handler, Messages.Measure_Msg);
            handler.sendMessage(msg);
            try {
                for (int i = 0; i < 5; i++) {
                    if (!ifStopShowMeasureStatus) {
                        Thread.sleep(200);
                    }
                }
            } catch (Exception ex) {
            }
        }
    }

    private void startShowMeasureStatus() {
        ifStopShowMeasureStatus = false;
        new showMeasureStatusThread().start();
    }

    private void stopShowMeasureStatus() {
        ifStopShowMeasureStatus = true;
    }

    /**
     * 测量过程显示状态
     */
    private class showMeasureStatusThread extends Thread {
        @Override
        public void run() {
            MainActivity.this.sendShowMeasureStatusMsg();
        }
    }

    /**
     * 连接管理
     */
    private class myTester extends Thread {
        @Override
        public void run() {
            try {
                ConnectionManager.connect();
            } catch (Exception ex) {
                ex.printStackTrace();
                SimpleMessageShower.showMsg(MainActivity.this, ex.getMessage());
            }
        }
    }

}
