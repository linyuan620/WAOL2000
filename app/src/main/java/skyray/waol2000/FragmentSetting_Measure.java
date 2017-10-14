package skyray.waol2000;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import skyray.waol2000.controller.MeasureCfgController;
import skyray.waol2000.datamodel.MeasureConfig;
import skyray.waol2000.datamodel.MeasureMode;

import static skyray.waol2000.R.id.sm_intervalMode;

/**
 * 手动操作界面
 */

public class FragmentSetting_Measure extends FragmentBase implements View.OnClickListener, CheckBox.OnCheckedChangeListener {
    public FragmentSetting_Measure() {
        layoutID = R.layout.fragment_setting_measure;
    }

    RadioButton rbtnIntervalMode = null;
    RadioButton rbtnPointTimeMode = null;

    List<CheckBox> allTimes = null;
    EditText etIntervalTime = null;

    TableLayout pointTimeContainer = null;

    Button btnSave = null;
    Button btnCancel = null;

    /**
     * 时间0-23全选文本
     */
    String allTimeString = "";

    @Override
    protected void onInit() {
        rbtnIntervalMode = (RadioButton) (getRootView().findViewById(sm_intervalMode));
        rbtnPointTimeMode = (RadioButton) (getRootView().findViewById(R.id.sm_pointTimeMode));
        rbtnIntervalMode.setOnCheckedChangeListener(this);
        rbtnPointTimeMode.setOnCheckedChangeListener(this);

        etIntervalTime = (EditText) (getRootView().findViewById(R.id.sm_intervalTime));
        btnSave = (Button) (getRootView().findViewById(R.id.btnConfirmMeasureConfig));
        btnSave.setOnClickListener(this);
        btnCancel = (Button) (getRootView().findViewById(R.id.btnCancelMeasureConfig));
        btnCancel.setOnClickListener(this);

        allTimeString = getContext().getString(R.string.allTimes);

        getAllPointTimeCheckBox();

        loadConfigAndShow();
    }

    /**
     * 获取所有表示时间的选择框
     */
    private void getAllPointTimeCheckBox() {
        allTimes = new ArrayList<>();
        pointTimeContainer = (TableLayout) (getRootView().findViewById(R.id.sm_pointTimes));
        int rowCount = pointTimeContainer.getChildCount();
        for (int i = 0; i < rowCount; i++) {
            TableRow tr = (TableRow) pointTimeContainer.getChildAt(i);
            int cbCount = tr.getChildCount();
            for (int cb = 0; cb < cbCount; cb++) {
                View view = tr.getChildAt(cb);
                if (view instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) view;
                    checkBox.setOnCheckedChangeListener(this);
                    allTimes.add(checkBox);
                }
            }
        }
    }

    /**
     * 保存测量配置
     */
    private void saveConfig() {

        MeasureConfig measureConfig = new MeasureConfig();

        MeasureMode mmode = MeasureMode.IntervalMode;
        if (rbtnPointTimeMode.isChecked()) {
            mmode = MeasureMode.PointTimeMode;
        }
        measureConfig.setMode(mmode.value());

        if (rbtnIntervalMode.isChecked()) {
            String intervalTime = etIntervalTime.getText().toString().trim();
            if (intervalTime.equals("")) {
                Toast.makeText(getContext(), " 请设置 间隔时间 ！", Toast.LENGTH_SHORT).show();
                etIntervalTime.requestFocus();
                return;
            } else {
                measureConfig.setIntervalTime(Integer.parseInt(intervalTime));
                measureConfig.setPointTimes("");
            }
        }
        else if(rbtnPointTimeMode.isChecked()) {
            String checkedTimes = "";
            for (int i = 0; i < allTimes.size(); i++) {
                if (!allTimes.get(i).getText().toString().equals(allTimeString)) {
                    if (allTimes.get(i).isChecked()) {
                        checkedTimes += allTimes.get(i).getText().toString() + MeasureCfgController.PointTimeSplitStr;
                    }
                }
            }
            if(checkedTimes.equals(""))
            {
                Toast.makeText(getContext(), " 请设置 测量时间 ！", Toast.LENGTH_SHORT).show();
                return;
            }
            measureConfig.setIntervalTime(0);
            measureConfig.setPointTimes(checkedTimes);
        }

        MeasureCfgController.getMeasureConfigController().saveMeasureConfig(measureConfig);

    }

    /**
     * 执行加载配置时程序控制时间选择
     */
    boolean loadCheck = false;

    /**
     * 加载测量配置
     */
    void loadConfigAndShow() {
        MeasureConfig measureConfig = MeasureCfgController.getMeasureConfigController().getMeasureConfig();
        rbtnIntervalMode.setChecked(measureConfig.getMode() == MeasureMode.IntervalMode.value());
        rbtnPointTimeMode.setChecked(measureConfig.getMode() == MeasureMode.PointTimeMode.value());
        etIntervalTime.setText(String.valueOf(measureConfig.getIntervalTime()));

        String times = measureConfig.getPointTimes();
        if (!times.equals("")) {
            String[] measureTimes = times.split(MeasureCfgController.PointTimeSplitStr);
            if (measureTimes != null && measureTimes.length > 0) {
                loadCheck = true;

                //取消选择所有
                for (int c = 0; c < allTimes.size(); c++) {
                    allTimes.get(c).setChecked(false);
                }

                //根据配置选择
                for (int i = 0; i < measureTimes.length; i++) {
                    for (int c = 0; c < allTimes.size(); c++) {
                        if (measureTimes[i].equals(allTimes.get(c).getText().toString().trim())) {
                            allTimes.get(c).setChecked(true);
                            break;
                        }
                    }
                }

                loadCheck = false;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConfirmMeasureConfig:
                saveConfig();
                break;
            case R.id.btnCancelMeasureConfig:
                loadConfigAndShow();
                break;
        }
    }

    /**
     * 切换测量模式
     * @param checkID
     * @param isChecked
     */
    private void excuteModeChange(int checkID, boolean isChecked) {
        if (checkID == R.id.sm_intervalMode) {
            if (isChecked) {
                etIntervalTime.setEnabled(true);

                for(int i=0;i<allTimes.size();i++)
                {
                    allTimes.get(i).setEnabled(false);
                }
            }
        } else if (checkID == R.id.sm_pointTimeMode) {
            if (isChecked) {
                etIntervalTime.setEnabled(false);
                for(int i=0;i<allTimes.size();i++)
                {
                    allTimes.get(i).setEnabled(true);
                }
            }
        }
    }

    /**
     * 执行选择测量时间小时值
     * @param buttonView
     * @param isChecked
     */
    private void excuteCheckPointTime(CompoundButton buttonView, boolean isChecked)
    {
        if (loadCheck) {
            return;
        }
        String text = buttonView.getText().toString();

        //当前操作项为 全选
        if (text.equals(allTimeString)) {
            for (int i = 0; i < allTimes.size(); i++) {
                if (!allTimes.get(i).getText().toString().equals(allTimeString)) {
                    allTimes.get(i).setChecked(isChecked);
                }
            }
        } else {
            boolean allchecked = true;
            for (int i = 0; i < allTimes.size(); i++) {
                if (!allTimes.get(i).getText().toString().equals(allTimeString)) {
                    allchecked = allchecked && allTimes.get(i).isChecked();
                    if (!allchecked) {
                        break;
                    }
                }
            }
            loadCheck = true;
            for (int i = 0; i < allTimes.size(); i++) {
                if (allTimes.get(i).getText().toString().equals(allTimeString)) {
                    allTimes.get(i).setChecked(allchecked);
                    break;
                }
            }
            loadCheck = false;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        int checkID = buttonView.getId();
        //模式选择事件
        if (checkID == R.id.sm_intervalMode || checkID == R.id.sm_pointTimeMode) {
            excuteModeChange(checkID, isChecked);
        } else {
            excuteCheckPointTime(buttonView, isChecked);
        }
    }
}
