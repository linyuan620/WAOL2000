package skyray.waol2000;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import skyray.waol2000.Core.FlowEngine;
import skyray.waol2000.Core.MainStatusManage;
import skyray.waol2000.Core.MeasureStatus;
import skyray.waol2000.Utils.AlertDialogManager;
import skyray.waol2000.Utils.NumberPointManage;
import skyray.waol2000.Utils.SimpleMessageShower;
import skyray.waol2000.controller.CalibrateCoefficientCfgController;
import skyray.waol2000.datamodel.IActionInfo;
import skyray.waol2000.datamodel.Messages;

/**
 * 手动操作界面
 */

public class FragmentMaintain_HandCalibrate extends FragmentBase implements View.OnClickListener {
    public FragmentMaintain_HandCalibrate() {
        layoutID = R.layout.fragment_handcalibrate;
    }

    @Override
    protected void onInit() {
        initAllControls();

        loadAndShowKB();
    }

    EditText etContentOne = null;
    EditText etContentTwo = null;
    EditText etOriginalVoltageOne = null;
    EditText etFinalVoltageOne = null;
    EditText etOriginalVoltageTwo = null;
    EditText etFinalVoltageTwo = null;
    Button btnCalibrateOne = null;
    Button btnCalibrateTwo = null;

    TextView tvCurrentAction = null;
    Button btnStopCalibrate = null;

    EditText etCurrentK = null;
    EditText etCurrentB = null;
    EditText etCalibrateK = null;
    EditText etCalibrateB = null;
    Button btnUpdateKB = null;

    private void initAllControls() {
        etContentOne = (EditText) (getRootView().findViewById(R.id.etContentOne));
        etContentTwo = (EditText) (getRootView().findViewById(R.id.etContentTwo));
        etOriginalVoltageOne = (EditText) (getRootView().findViewById(R.id.etOriginalVoltage1));
        etFinalVoltageOne = (EditText) (getRootView().findViewById(R.id.etFinalVoltage1));
        etOriginalVoltageTwo = (EditText) (getRootView().findViewById(R.id.etOriginalVoltage2));
        etFinalVoltageTwo = (EditText) (getRootView().findViewById(R.id.etFinalVoltage2));

        btnCalibrateOne = (Button) (getRootView().findViewById(R.id.btnCalibrateOne));
        btnCalibrateOne.setOnClickListener(this);
        btnCalibrateTwo = (Button) (getRootView().findViewById(R.id.btnCalibrateTwo));
        btnCalibrateTwo.setOnClickListener(this);

        tvCurrentAction = (TextView) (getRootView().findViewById(R.id.currentCalibrateAction));

        btnStopCalibrate = (Button) (getRootView().findViewById(R.id.btnStopCalibrate));
        btnStopCalibrate.setOnClickListener(this);

        etCurrentK = (EditText) (getRootView().findViewById(R.id.etCurrentK));
        etCurrentB = (EditText) (getRootView().findViewById(R.id.etCurrentB));
        etCalibrateK = (EditText) (getRootView().findViewById(R.id.etCalibrateK));
        etCalibrateB = (EditText) (getRootView().findViewById(R.id.etCalibrateB));
        btnUpdateKB = (Button) (getRootView().findViewById(R.id.btnUpdateKB));
        btnUpdateKB.setOnClickListener(this);
    }

    private void doCalibrateOne() {
        if (etContentOne.getText().toString().equals("")) {
            SimpleMessageShower.showMsg(getContext(), " 请输入 浓度1 ！");
            etContentOne.requestFocus();
            return;
        }
        ifStopCalibrate = false;
        String msg = FlowEngine.getFG().BeginCalibrateOne(Double.parseDouble(etContentOne.getText().toString().trim()));
        if (msg.equals("")) {
            new ShowCalibrateStatusThread().start();
            calibrateStatus = MeasureStatus.StandardOne;
        } else {
            SimpleMessageShower.showMsg(getContext(), msg);
        }
    }

    private void doCalibrateTwo() {
        if (etContentTwo.getText().toString().trim().equals("")) {
            SimpleMessageShower.showMsg(getContext(), " 请输入 浓度2 ！");
            etContentTwo.requestFocus();
            return;
        }
        ifStopCalibrate = false;
        String msg = FlowEngine.getFG().BeginCalibrateTwo(Double.parseDouble(etContentTwo.getText().toString().trim()));
        if (msg.equals("")) {
            new ShowCalibrateStatusThread().start();
            calibrateStatus = MeasureStatus.StandardTwo;
        } else {
            SimpleMessageShower.showMsg(getContext(), msg);
        }
    }

    public void stopCalibrate() {
        ifStopCalibrate = true;
        FlowEngine.getFG().StopFlow();
    }

    private void updateKB() {
        if (etCalibrateK.getText().toString().trim().equals("")
                || etCalibrateB.getText().toString().trim().equals("")) {
            SimpleMessageShower.showMsg(getContext(), " 请先输入 标定K 和 标定B ！");
            return;
        }

        AlertDialogManager.showDialog(getContext(), "确定替换？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        double k = Double.parseDouble(etCalibrateK.getText().toString().trim());
                        double b = Double.parseDouble(etCalibrateB.getText().toString().trim());

                        FlowEngine.getFG().ChangeCoefficient(k, b);

                        loadAndShowKB();
                    }
                }
        );
    }

    private void loadAndShowKB() {
        double kV = CalibrateCoefficientCfgController.GetCalibrateCoefficientController().GetCalibrateCoefficient().getKValue();
        double bV = CalibrateCoefficientCfgController.GetCalibrateCoefficientController().GetCalibrateCoefficient().getBValue();
        etCurrentK.setText(NumberPointManage.toString(kV, 3));
        etCurrentB.setText(NumberPointManage.toString(bV, 3));
    }

    /**
     * 显示当前标定状态消息控制器
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case Messages.CalibrateStatus_Msg:
                    showCalibrateStatus();
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    private MeasureStatus calibrateStatus = MeasureStatus.Idle;

    private void showCalibrateStatus() {
        if (!FlowEngine.getFG().isOver) {
            IActionInfo currentAction = FlowEngine.getFG().getCurrentAction();
            if (currentAction != null) {
                tvCurrentAction.setText(currentAction.toString());
            }
        } else {
            tvCurrentAction.setText("标定完成");
            if (calibrateStatus == MeasureStatus.StandardOne) {
                etOriginalVoltageOne.setText(NumberPointManage.toString(MainStatusManage.get_OriginalLightVoltage(), 2));
                etFinalVoltageOne.setText(NumberPointManage.toString(MainStatusManage.get_FinalLightVoltage(), 2));
            } else if (calibrateStatus == MeasureStatus.StandardTwo) {
                etOriginalVoltageTwo.setText(NumberPointManage.toString(MainStatusManage.get_OriginalLightVoltage(), 2));
                etFinalVoltageTwo.setText(NumberPointManage.toString(MainStatusManage.get_FinalLightVoltage(), 2));
                etCalibrateK.setText(NumberPointManage.toString(FlowEngine.getFG().getCC().getKValue(), 3));
                etCalibrateB.setText(NumberPointManage.toString(FlowEngine.getFG().getCC().getBValue(), 3));
            }
        }
    }

    boolean ifStopCalibrate = false;

    private void sendShowCalibrateStatusMessage() {
        while (!ifStopCalibrate) {
            Message msg = handler.obtainMessage(Messages.CalibrateStatus_Msg);
            handler.sendMessage(msg);
            for (int i = 0; i < 5; i++) {
                if (!ifStopCalibrate) {
                    try {
                        Thread.sleep(200);
                    } catch (Exception ex) {
                    }
                }
            }
        }
    }

    private class ShowCalibrateStatusThread extends Thread {
        @Override
        public void run() {
            FragmentMaintain_HandCalibrate.this.sendShowCalibrateStatusMessage();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCalibrateOne:
                doCalibrateOne();
                break;
            case R.id.btnCalibrateTwo:
                doCalibrateTwo();
                break;
            case R.id.btnStopCalibrate:
                stopCalibrate();
                break;
            case R.id.btnUpdateKB:
                updateKB();
                break;
        }
    }
}
