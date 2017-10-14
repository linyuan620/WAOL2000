package skyray.waol2000;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kyleduo.switchbutton.SwitchButton;

import skyray.waol2000.controller.CalibrateCfgController;
import skyray.waol2000.Utils.SimpleMessageShower;
import skyray.waol2000.datamodel.CalibrateConfig;

/**
 * 手动操作界面
 */

public class FragmentSetting_Calibrate extends FragmentBase implements View.OnClickListener {
    public FragmentSetting_Calibrate() {
        layoutID = R.layout.fragment_setting_calibrate;
    }

    EditText f_etContentOne = null;
    EditText f_etContentTwo = null;
    EditText f_etCalibrateTime = null;
    EditText f_etCalibrateInterval = null;
    SwitchButton f_AutoCalibrate = null;
    SwitchButton f_FirstLoopCalibrate = null;
    Button f_btnSvae = null;
    Button f_btnCancel = null;

    @Override
    protected void onInit() {
        f_etContentOne = (EditText) (getRootView().findViewById(R.id.etContentOne));
        f_etContentTwo = (EditText) (getRootView().findViewById(R.id.etContentTwo));
        f_etCalibrateTime = (EditText) (getRootView().findViewById(R.id.etCalibrateTime));
        f_etCalibrateInterval = (EditText) (getRootView().findViewById(R.id.etCalibrateInterval));
        f_AutoCalibrate = (SwitchButton) (getRootView().findViewById(R.id.autoCalibrate));
        f_FirstLoopCalibrate = (SwitchButton) (getRootView().findViewById(R.id.firstLoopCalibrate));
        f_btnSvae = (Button) (getRootView().findViewById(R.id.btnConfirmCalibrateConfig));
        f_btnSvae.setOnClickListener(this);
        f_btnCancel = (Button) (getRootView().findViewById(R.id.btnCancelCalibrateConfig));
        f_btnCancel.setOnClickListener(this);

        loadAndShowConfig();
    }

    private void saveConfig() {
        if (f_etContentOne.getText().toString().trim().equals("")) {
            SimpleMessageShower.showMsg(getContext(), " 请先设置 标样1浓度 ！");
            f_etContentOne.requestFocus();
            return;
        }
        if (f_etContentTwo.getText().toString().trim().equals("")) {
            SimpleMessageShower.showMsg(getContext(), " 请先设置 标样2浓度 ！");
            f_etContentTwo.requestFocus();
            return;
        }
        if (f_etCalibrateTime.getText().toString().trim().equals("")) {
            SimpleMessageShower.showMsg(getContext(), " 请先设置 校准时间 ！");
            f_etCalibrateTime.requestFocus();
            return;
        }
        if (f_etCalibrateInterval.getText().toString().trim().equals("")) {
            SimpleMessageShower.showMsg(getContext(), " 请先设置 校准间隔 ！");
            f_etCalibrateInterval.requestFocus();
            return;
        }

        CalibrateConfig config = new CalibrateConfig();
        config.setContentOne(Double.parseDouble(f_etContentOne.getText().toString().trim()));
        config.setContentTwo(Double.parseDouble(f_etContentTwo.getText().toString().trim()));
        config.setCalibrateTime(Integer.parseInt(f_etCalibrateTime.getText().toString().trim()));
        config.setCalibrateIntervalTime(Integer.parseInt(f_etCalibrateInterval.getText().toString().trim()));
        config.setAutoCalibrate(f_AutoCalibrate.isChecked());
        config.setFirstLoopCalibrate(f_FirstLoopCalibrate.isChecked());

        CalibrateCfgController.getCalibrateConfigController().saveCalibrateConfig(config);
    }

    private void loadAndShowConfig() {
        CalibrateConfig config = CalibrateCfgController.getCalibrateConfigController().GetCalibrateConfig();
        if (config != null) {
            f_etContentOne.setText(String.valueOf(config.getContentOne()));
            f_etContentTwo.setText(String.valueOf(config.getContentTwo()));
            f_etCalibrateTime.setText(String.valueOf(config.getCalibrateTime()));
            f_etCalibrateInterval.setText(String.valueOf(config.getCalibrateIntervalTime()));
            f_AutoCalibrate.setChecked(config.isAutoCalibrate());
            f_FirstLoopCalibrate.setChecked(config.isFirstLoopCalibrate());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConfirmCalibrateConfig:
                saveConfig();
                break;
            case R.id.btnCancelCalibrateConfig:
                loadAndShowConfig();
                break;
        }
    }
}
