package skyray.waol2000;

import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.kyleduo.switchbutton.SwitchButton;

import skyray.waol2000.Adapter.ActionADSampleListAdapter;
import skyray.waol2000.Adapter.ActionInjectionListAdapter;
import skyray.waol2000.Adapter.ActionPumpListAdapter;
import skyray.waol2000.Adapter.ActionValveListAdapter;
import skyray.waol2000.Adapter.ActionWaitTimeListAdapter;
import skyray.waol2000.Utils.AlertDialogManager;
import skyray.waol2000.controller.ActionADSampleController;
import skyray.waol2000.controller.ActionInjectionController;
import skyray.waol2000.controller.ActionPumpController;
import skyray.waol2000.controller.ActionValveController;
import skyray.waol2000.controller.ActionWaitTimeController;
import skyray.waol2000.Utils.SimpleMessageShower;
import skyray.waol2000.controller.DeleteChecker;
import skyray.waol2000.controller.InjectionInfoController;
import skyray.waol2000.controller.PumpInfoController;
import skyray.waol2000.controller.ValveInfoController;
import skyray.waol2000.datamodel.ActionADSample;
import skyray.waol2000.datamodel.ActionInjection;
import skyray.waol2000.datamodel.ActionPump;
import skyray.waol2000.datamodel.ActionValve;
import skyray.waol2000.datamodel.ActionWaitTime;
import skyray.waol2000.datamodel.InjectionInfo;
import skyray.waol2000.datamodel.PumpInfo;
import skyray.waol2000.datamodel.PumpSwitch;
import skyray.waol2000.datamodel.ValveInfo;
import skyray.waol2000.datamodel.ValveSwitch;

/**
 * 手动操作界面
 */

public class FragmentSetting_Action extends FragmentBase implements View.OnClickListener {
    public FragmentSetting_Action() {
        layoutID = R.layout.fragment_setting_action;
    }

    //阀动作相关
    Button btnValveAction = null;
    LinearLayout LL_ActionValveContrainer = null;
    ListView lvActionValve = null;
    EditText etActionValveName = null;
    Spinner sp_ActionValveInfos = null;
    SwitchButton sb_ActionValveStatus = null;
    Button btnAddActionValve = null;
    Button btnDeleteActionValve = null;
    ActionValveListAdapter actionValveAdapter = null;

    private void initValveControls() {
        btnValveAction = (Button) (getRootView().findViewById(R.id.btnValveAction));
        btnValveAction.setOnClickListener(this);
        LL_ActionValveContrainer = (LinearLayout) (getRootView().findViewById(R.id.ll_valveAction));
        etActionValveName = (EditText) (getRootView().findViewById(R.id.etActionValveName));
        sp_ActionValveInfos = (Spinner) (getRootView().findViewById(R.id.ddl_valveAction));
        sb_ActionValveStatus = (SwitchButton) (getRootView().findViewById(R.id.sb_ActionValveStatus));
        btnAddActionValve = (Button) (getRootView().findViewById(R.id.btnAddValveAction));
        btnAddActionValve.setOnClickListener(this);
        btnDeleteActionValve = (Button) (getRootView().findViewById(R.id.btnDeleteValveAction));
        btnDeleteActionValve.setOnClickListener(this);
        initAllValveInfos();
        lvActionValve = (ListView) (getRootView().findViewById(R.id.action_valve_list_view));
        actionValveAdapter = new ActionValveListAdapter(getContext(), ActionValveController.getAVC().getAllInfos());
        lvActionValve.setAdapter(actionValveAdapter);

        lvActionValve.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                actionValveAdapter.setSelectItem(position);
                showSelectValveInfo(position);
                refreshActionValves();
            }
        });
    }

    //泵动作相关变量
    Button btnpumpAction = null;
    LinearLayout LL_ActionPumpContrainer = null;
    ListView lvActionPump = null;
    EditText etActionPumpName = null;
    Spinner sp_ActionPumpInfos = null;
    Spinner sp_PumpSpeeds = null;
    SwitchButton sb_ActionPumpStatus = null;
    Button btnAddActionPump = null;
    Button btnDeleteActionPump = null;
    ActionPumpListAdapter actionPumpAdapter = null;

    private void initPumpActionControls() {
        btnpumpAction = (Button) (getRootView().findViewById(R.id.btnPumpAction));
        btnpumpAction.setOnClickListener(this);
        LL_ActionPumpContrainer = (LinearLayout) (getRootView().findViewById(R.id.ll_pumpAction));
        lvActionPump = (ListView) (getRootView().findViewById(R.id.action_pump_list_view));
        etActionPumpName = (EditText) (getRootView().findViewById(R.id.etActionPumpName));
        sp_ActionPumpInfos = (Spinner) (getRootView().findViewById(R.id.ddl_pumpAction));
        initAllPumpInfos();
        sp_PumpSpeeds = (Spinner) (getRootView().findViewById(R.id.ddl_pumpSpeed));
        initPumpSpeeds();
        sb_ActionPumpStatus = (SwitchButton) (getRootView().findViewById(R.id.sb_ActionPumpStatus));
        btnAddActionPump = (Button) (getRootView().findViewById(R.id.btnAddPumpAction));
        btnAddActionPump.setOnClickListener(this);
        btnDeleteActionPump = (Button) (getRootView().findViewById(R.id.btnDeletePumpAction));
        btnDeleteActionPump.setOnClickListener(this);
        actionPumpAdapter = new ActionPumpListAdapter(getContext(), ActionPumpController.getAPC().getAllInfos());
        lvActionPump.setAdapter(actionPumpAdapter);
        lvActionPump.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                actionPumpAdapter.setSelectItem(position);
                showSelectPumpInfo(position);
                refreshActionPumps();
            }
        });
    }

    //AD采样动作相关
    Button btnadSampleAction = null;
    LinearLayout LL_ActionADSampleContrainer = null;
    EditText etActionADSampleName = null;
    EditText etActionADSampleCount = null;
    SwitchButton sbVoltageType = null;
    Button btnAddActionADSample = null;
    Button btnDeleteActionADSample = null;
    ListView lvActionADSample = null;
    ActionADSampleListAdapter actionADSampleListAdapter = null;

    private void initActionADSampleControls() {

        btnadSampleAction = (Button) (getRootView().findViewById(R.id.btnADSamplingAction));
        btnadSampleAction.setOnClickListener(this);
        LL_ActionADSampleContrainer = (LinearLayout) (getRootView().findViewById(R.id.ll_adSamplingAction));

        etActionADSampleName = (EditText) (getRootView().findViewById(R.id.etActionADSampleName));
        etActionADSampleCount = (EditText) (getRootView().findViewById(R.id.etActionADSampleCount));
        sbVoltageType = (SwitchButton)(getRootView().findViewById(R.id.sb_ActionADSampleVoltageType));

        btnAddActionADSample = (Button) (getRootView().findViewById(R.id.btnAddADSampleAction));
        btnAddActionADSample.setOnClickListener(this);
        btnDeleteActionADSample = (Button) (getRootView().findViewById(R.id.btnDeleteADSampleAction));
        btnDeleteActionADSample.setOnClickListener(this);
        lvActionADSample = (ListView) (getRootView().findViewById(R.id.action_adsample_list_view));
        actionADSampleListAdapter = new ActionADSampleListAdapter(getContext(), ActionADSampleController.getASC().getAllInfos());
        lvActionADSample.setAdapter(actionADSampleListAdapter);
        lvActionADSample.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                actionADSampleListAdapter.setSelectItem(position);
                showSelectADSampleInfo(position);
                refreshActionADSamples();
            }
        });
    }

    //等待动作相关
    Button btnwaitAction = null;
    LinearLayout LL_ActionWaitTimeContrainer = null;
    EditText etActionWaitTimeName = null;
    EditText etActionWaitTimeCount = null;
    Button btnAddActionWaitTime = null;
    Button btnDeleteActionWaitTime = null;
    ListView lvActionWaitTime = null;
    ActionWaitTimeListAdapter actionWaitTimeListAdapter = null;

    private void initActionWaitTimeControls() {
        btnwaitAction = (Button) (getRootView().findViewById(R.id.btnwaitingAction));
        btnwaitAction.setOnClickListener(this);
        LL_ActionWaitTimeContrainer = (LinearLayout) (getRootView().findViewById(R.id.ll_watingAction));

        etActionWaitTimeName = (EditText) (getRootView().findViewById(R.id.etActionWaittimeName));
        etActionWaitTimeCount = (EditText) (getRootView().findViewById(R.id.etActionWaitTimeValue));
        btnAddActionWaitTime = (Button) (getRootView().findViewById(R.id.btnAddWaitTimeAction));
        btnAddActionWaitTime.setOnClickListener(this);
        btnDeleteActionWaitTime = (Button) (getRootView().findViewById(R.id.btnDeleteWaitTimeAction));
        btnDeleteActionWaitTime.setOnClickListener(this);
        lvActionWaitTime = (ListView) (getRootView().findViewById(R.id.action_waittime_list_view));
        actionWaitTimeListAdapter = new ActionWaitTimeListAdapter(getContext(), ActionWaitTimeController.getASC().getAllInfos());
        lvActionWaitTime.setAdapter(actionWaitTimeListAdapter);
        lvActionWaitTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                actionWaitTimeListAdapter.setSelectItem(position);
                showSelectWaitTimeInfo(position);
                refreshActionWaitTimes();
            }
        });
    }

    //进样动作相关
    Button btnInjectionAction = null;
    LinearLayout LL_ActionInjectionContrainer = null;
    ListView lvActionInjection = null;
    EditText etActionInjectionName = null;
    Spinner sp_ActionInjectionInfos = null;
    Button btnAddActionInjection = null;
    Button btnDeleteActionInjection = null;
    ActionInjectionListAdapter actionInjectionAdapter = null;

    private void initInjectionControls() {
        btnInjectionAction = (Button) (getRootView().findViewById(R.id.btnInjectionAction));
        btnInjectionAction.setOnClickListener(this);
        LL_ActionInjectionContrainer = (LinearLayout) (getRootView().findViewById(R.id.ll_injectionAction));
        etActionInjectionName = (EditText) (getRootView().findViewById(R.id.etActionInjectionName));
        sp_ActionInjectionInfos = (Spinner) (getRootView().findViewById(R.id.ddl_InjectionInfo));
        btnAddActionInjection = (Button) (getRootView().findViewById(R.id.btnAddInjectionAction));
        btnAddActionInjection.setOnClickListener(this);
        btnDeleteActionInjection = (Button) (getRootView().findViewById(R.id.btnDeleteInjectionAction));
        btnDeleteActionInjection.setOnClickListener(this);
        initAllInjectionInfos();
        lvActionInjection = (ListView) (getRootView().findViewById(R.id.action_injection_list_view));
        actionInjectionAdapter = new ActionInjectionListAdapter(getContext(), ActionInjectionController.getAIC().getAllInfos());
        lvActionInjection.setAdapter(actionInjectionAdapter);

        lvActionInjection.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                actionInjectionAdapter.setSelectItem(position);
                showSelectInjectionInfo(position);
                refreshActionInjections();
            }
        });
    }

    @Override
    protected void onInit() {

        //阀动作相关
        initValveControls();

        //泵动作相关
        initPumpActionControls();

        //AD采样动作相关
        initActionADSampleControls();

        //等待时间动作相关
        initActionWaitTimeControls();

        //进样动作相关
        initInjectionControls();
    }

    private void showValveActionConfig() {
        LL_ActionValveContrainer.setVisibility(View.VISIBLE);
        LL_ActionPumpContrainer.setVisibility(View.GONE);
        LL_ActionADSampleContrainer.setVisibility(View.GONE);
        LL_ActionWaitTimeContrainer.setVisibility(View.GONE);
        LL_ActionInjectionContrainer.setVisibility(View.GONE);
    }

    private void showPumpActionConfig() {
        LL_ActionValveContrainer.setVisibility(View.GONE);
        LL_ActionPumpContrainer.setVisibility(View.VISIBLE);
        LL_ActionADSampleContrainer.setVisibility(View.GONE);
        LL_ActionWaitTimeContrainer.setVisibility(View.GONE);
        LL_ActionInjectionContrainer.setVisibility(View.GONE);
    }

    private void showAdSamplingActionConfig() {
        LL_ActionValveContrainer.setVisibility(View.GONE);
        LL_ActionPumpContrainer.setVisibility(View.GONE);
        LL_ActionADSampleContrainer.setVisibility(View.VISIBLE);
        LL_ActionWaitTimeContrainer.setVisibility(View.GONE);
        LL_ActionInjectionContrainer.setVisibility(View.GONE);
    }

    private void showWaitActionConfig() {
        LL_ActionValveContrainer.setVisibility(View.GONE);
        LL_ActionPumpContrainer.setVisibility(View.GONE);
        LL_ActionADSampleContrainer.setVisibility(View.GONE);
        LL_ActionWaitTimeContrainer.setVisibility(View.VISIBLE);
        LL_ActionInjectionContrainer.setVisibility(View.GONE);
    }

    private void showInjectionActionConfig()
    {
        LL_ActionValveContrainer.setVisibility(View.GONE);
        LL_ActionPumpContrainer.setVisibility(View.GONE);
        LL_ActionADSampleContrainer.setVisibility(View.GONE);
        LL_ActionWaitTimeContrainer.setVisibility(View.GONE);
        LL_ActionInjectionContrainer.setVisibility(View.VISIBLE);
    }

    //阀动作控制相关开始
    private void initAllValveInfos() {
        ArrayAdapter<ValveInfo> myaAdapter = new ArrayAdapter<ValveInfo>(getContext(), android.R.layout.simple_spinner_item,
                ValveInfoController.getAPC().getAllInfos());
        sp_ActionValveInfos.setAdapter(myaAdapter);
    }

    //当前选中的阀动作
    int selectedActionValveIndex = -1;

    private void showSelectValveInfo(int position) {
        try {
            selectedActionValveIndex = position;
            etActionValveName.setText(ActionValveController.getAVC().getAllInfos().get(position).getActionName().toString());
            int ic = sp_ActionValveInfos.getAdapter().getCount();
            for (int i = 0; i < ic; i++) {
                ValveInfo valveInfo = (ValveInfo) sp_ActionValveInfos.getAdapter().getItem(i);
                if (valveInfo.getValveName().equals(ActionValveController.getAVC().getAllInfos().get(position).getValveInfoV().toString())) {
                    sp_ActionValveInfos.setSelection(i);
                    break;
                }
            }
            sb_ActionValveStatus.setChecked(ActionValveController.getAVC().getAllInfos().get(position).getSwitchValue() == ValveSwitch.Open);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshActionValves() {
        actionValveAdapter.notifyDataSetInvalidated();
        actionValveAdapter.notifyDataSetChanged();
    }

    private void addActionValve() {
        try {
            if (etActionValveName.getText().toString().trim().equals("")) {
                SimpleMessageShower.showMsg(getContext(), "请输入 动作名称 ！");
                return;
            }
            ActionValve av = new ActionValve();
            av.setActionName(etActionValveName.getText().toString().trim());
            ValveInfo vinfo = (ValveInfo) sp_ActionValveInfos.getSelectedItem();
            av.setValveInfoV(vinfo);
            av.setSwitchValue(sb_ActionValveStatus.isChecked() ? ValveSwitch.Open : ValveSwitch.Close);

            ActionValveController.getAVC().updateInfo(av);
            selectedActionValveIndex = -1;
            actionValveAdapter.setSelectItem(selectedActionValveIndex);
            refreshActionValves();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteActionValve() {
        if (selectedActionValveIndex > -1) {
            final ActionValve actionValve = ActionValveController.getAVC().getAllInfos().get(selectedActionValveIndex);
            boolean using = DeleteChecker.checkActionUsing(actionValve);
            if (using) {
                SimpleMessageShower.showMsg(getContext(), "有动作包使用选择动作，不能删除！");
            } else {
                AlertDialogManager.showDialog(getContext(), "确认删除？",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActionValveController.getAVC().deleteInfo(actionValve);
                                selectedActionValveIndex = -1;
                                refreshActionValves();
                            }
                        }
                );
            }
        }
    }
    //阀动作控制相关结束

    //泵动作控制相关开始
    private void initAllPumpInfos() {
        ArrayAdapter<PumpInfo> myaAdapter = new ArrayAdapter<PumpInfo>(getContext(), android.R.layout.simple_spinner_item,
                PumpInfoController.getAPC().getAllInfos());
        sp_ActionPumpInfos.setAdapter(myaAdapter);
    }

    private void initPumpSpeeds() {
        ArrayAdapter<CharSequence> adapterBR = ArrayAdapter.createFromResource(
                getContext(), R.array.pumpSpeed,
                android.R.layout.simple_spinner_item);
        sp_PumpSpeeds.setAdapter(adapterBR);
    }

    int selectedActionPumpIndex = -1;

    private void showSelectPumpInfo(int position) {
        try {
            selectedActionPumpIndex = position;
            etActionPumpName.setText(ActionPumpController.getAPC().getAllInfos().get(position).getActionName().toString());
            int ic = sp_ActionPumpInfos.getAdapter().getCount();
            for (int i = 0; i < ic; i++) {
                PumpInfo PumpInfo = (PumpInfo) sp_ActionPumpInfos.getAdapter().getItem(i);
                if (PumpInfo.getPumpName().equals(ActionPumpController.getAPC().getAllInfos().get(position).getPumpInfoV().toString())) {
                    sp_ActionPumpInfos.setSelection(i);
                    break;
                }
            }
            int speedPosition = (int) (Math.log(ActionPumpController.getAPC().getAllInfos().get(position).getSpeed()) / Math.log((double) 2));
            sp_PumpSpeeds.setSelection(speedPosition);
            sb_ActionPumpStatus.setChecked(ActionPumpController.getAPC().getAllInfos().get(position).getSwitchValue() == PumpSwitch.Open);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshActionPumps() {
        actionPumpAdapter.notifyDataSetInvalidated();
        actionPumpAdapter.notifyDataSetChanged();
    }

    private void addActionPump() {
        try {
            if (etActionPumpName.getText().toString().trim().equals("")) {
                SimpleMessageShower.showMsg(getContext(), "请输入 动作名称 ！");
                return;
            }
            ActionPump av = new ActionPump();
            av.setActionName(etActionPumpName.getText().toString().trim());
            PumpInfo vinfo = (PumpInfo) sp_ActionPumpInfos.getSelectedItem();
            av.setPumpInfoV(vinfo);
            av.setSpeed(Integer.parseInt(sp_PumpSpeeds.getSelectedItem().toString()));
            av.setSwitchValue(sb_ActionPumpStatus.isChecked() ? PumpSwitch.Open : PumpSwitch.Close);

            ActionPumpController.getAPC().updateInfo(av);
            selectedActionPumpIndex = -1;
            actionPumpAdapter.setSelectItem(selectedActionPumpIndex);
            refreshActionPumps();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteActionPump() {
        if (selectedActionPumpIndex > -1) {
            final ActionPump actionPump = ActionPumpController.getAPC().getAllInfos().get(selectedActionPumpIndex);
            boolean using = DeleteChecker.checkActionUsing(actionPump);
            if (using) {
                SimpleMessageShower.showMsg(getContext(), "有动作包使用选择动作，不能删除！");
            } else {
                AlertDialogManager.showDialog(getContext(), "确认删除？",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActionPumpController.getAPC().deleteInfo(actionPump);
                                selectedActionPumpIndex = -1;
                                refreshActionPumps();
                            }
                        }
                );
            }
        }
    }
    //泵动作控制相关结束

    //ad采样动作开始
    int selectedActionADSampleIndex = -1;

    private void showSelectADSampleInfo(int position) {
        try {
            selectedActionADSampleIndex = position;
            etActionADSampleName.setText(ActionADSampleController.getASC().getAllInfos().get(position).getActionName().toString());
            etActionADSampleCount.setText(String.valueOf(ActionADSampleController.getASC().getAllInfos().get(position).getSampleCount()));
            sbVoltageType.setChecked(ActionADSampleController.getASC().getAllInfos().get(position).isFinalVoltage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshActionADSamples() {
        actionADSampleListAdapter.notifyDataSetInvalidated();
        actionADSampleListAdapter.notifyDataSetChanged();
    }

    private void addActionADSample() {
        try {
            if (etActionADSampleName.getText().toString().trim().equals("")) {
                SimpleMessageShower.showMsg(getContext(), "请输入 动作名称 ！");
                return;
            }
            if (etActionADSampleCount.getText().toString().trim().equals("")) {
                SimpleMessageShower.showMsg(getContext(), "请输入 采样次数 ！");
                return;
            }

            ActionADSample av = new ActionADSample();
            av.setActionName(etActionADSampleName.getText().toString().trim());
            av.setSampleCount(Integer.parseInt(etActionADSampleCount.getText().toString().trim()));
            av.setFinalVoltage(sbVoltageType.isChecked());

            ActionADSampleController.getASC().updateInfo(av);
            selectedActionADSampleIndex = -1;
            actionADSampleListAdapter.setSelectItem(selectedActionADSampleIndex);
            refreshActionADSamples();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteActionADSample() {
        if (selectedActionADSampleIndex > -1) {
            final ActionADSample adSample = ActionADSampleController.getASC().getAllInfos().get(selectedActionADSampleIndex);
            boolean using = DeleteChecker.checkActionUsing(adSample);
            if (using) {
                SimpleMessageShower.showMsg(getContext(), "有动作包使用选择动作，不能删除！");
            } else {
                AlertDialogManager.showDialog(getContext(), "确认删除？",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActionADSampleController.getASC().deleteInfo(adSample);
                                selectedActionADSampleIndex = -1;
                                refreshActionADSamples();
                            }
                        }
                );
            }
        }
    }
    //ad采样动作结束

    //等待时间动作开始
    int selectedActionWaitTimeIndex = -1;

    private void showSelectWaitTimeInfo(int position) {
        try {
            selectedActionWaitTimeIndex = position;
            etActionWaitTimeName.setText(ActionWaitTimeController.getASC().getAllInfos().get(position).getActionName().toString());
            etActionWaitTimeCount.setText(String.valueOf(ActionWaitTimeController.getASC().getAllInfos().get(position).getTimeValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshActionWaitTimes() {
        actionWaitTimeListAdapter.notifyDataSetInvalidated();
        actionWaitTimeListAdapter.notifyDataSetChanged();
    }

    private void addActionWaitTime() {
        try {
            if (etActionWaitTimeName.getText().toString().trim().equals("")) {
                SimpleMessageShower.showMsg(getContext(), "请输入 动作名称 ！");
                return;
            }
            if (etActionWaitTimeCount.getText().toString().trim().equals("")) {
                SimpleMessageShower.showMsg(getContext(), "请输入 等待时间 ！");
                return;
            }

            ActionWaitTime av = new ActionWaitTime();
            av.setActionName(etActionWaitTimeName.getText().toString().trim());
            av.setTimeValue(Integer.parseInt(etActionWaitTimeCount.getText().toString().trim()));

            ActionWaitTimeController.getASC().updateInfo(av);
            selectedActionWaitTimeIndex = -1;
            actionWaitTimeListAdapter.setSelectItem(selectedActionWaitTimeIndex);
            refreshActionWaitTimes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteActionWaitTime() {
        if (selectedActionWaitTimeIndex > -1) {
            final ActionWaitTime actionWaitTime = ActionWaitTimeController.getASC().getAllInfos().get(selectedActionWaitTimeIndex);
            boolean using = DeleteChecker.checkActionUsing(actionWaitTime);
            if (using) {
                SimpleMessageShower.showMsg(getContext(), "有动作包使用选择动作，不能删除！");
            } else {
                AlertDialogManager.showDialog(getContext(), "确认删除？",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActionWaitTimeController.getASC().deleteInfo(actionWaitTime);
                                selectedActionWaitTimeIndex = -1;
                                refreshActionWaitTimes();
                            }
                        }
                );
            }
        }
    }
    //等待时间动作结束

    //进样动作控制相关开始
    private void initAllInjectionInfos() {
        ArrayAdapter<InjectionInfo> myaAdapter = new ArrayAdapter<InjectionInfo>(getContext(),
                android.R.layout.simple_spinner_item, InjectionInfoController.getAPC().getAllInfos());
        sp_ActionInjectionInfos.setAdapter(myaAdapter);
    }

    //当前选中的进样动作
    int selectedActionInjectionIndex = -1;

    private void showSelectInjectionInfo(int position) {
        try {
            selectedActionInjectionIndex = position;
            etActionInjectionName.setText(ActionInjectionController.getAIC().getAllInfos().get(position).getActionName().toString());
            int ic = sp_ActionInjectionInfos.getAdapter().getCount();
            for (int i = 0; i < ic; i++) {
                InjectionInfo InjectionInfo = (InjectionInfo) sp_ActionInjectionInfos.getAdapter().getItem(i);
                if (InjectionInfo.getInjectionName().equals(ActionInjectionController.getAIC().getAllInfos().get(position).getInjectionValue().getInjectionName().toString())) {
                    sp_ActionInjectionInfos.setSelection(i);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshActionInjections() {
        actionInjectionAdapter.notifyDataSetInvalidated();
        actionInjectionAdapter.notifyDataSetChanged();
    }

    private void addActionInjection() {
        try {
            if (etActionInjectionName.getText().toString().trim().equals("")) {
                SimpleMessageShower.showMsg(getContext(), "请输入 动作名称 ！");
                return;
            }
            ActionInjection av = new ActionInjection();
            av.setActionName(etActionInjectionName.getText().toString().trim());
            InjectionInfo vinfo = (InjectionInfo) sp_ActionInjectionInfos.getSelectedItem();
            av.setInjectionValue(vinfo);

            ActionInjectionController.getAIC().updateInfo(av);
            selectedActionInjectionIndex = -1;
            actionInjectionAdapter.setSelectItem(selectedActionInjectionIndex);
            refreshActionInjections();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteActionInjection() {
        if (selectedActionInjectionIndex > -1) {
            final ActionInjection actionInjection = ActionInjectionController.getAIC().getAllInfos().get(selectedActionInjectionIndex);
            boolean using = DeleteChecker.checkActionUsing(actionInjection);
            if (using) {
                SimpleMessageShower.showMsg(getContext(), "有动作包使用选择动作，不能删除！");
            } else {
                AlertDialogManager.showDialog(getContext(), "确认删除？",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActionInjectionController.getAIC().deleteInfo(actionInjection);
                                selectedActionInjectionIndex = -1;
                                refreshActionInjections();
                            }
                        }
                );
            }
        }
    }
    //进样动作控制相关结束

    //按钮事件对应函数
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnValveAction:
                showValveActionConfig();
                break;
            case R.id.btnAddValveAction:
                addActionValve();
                break;
            case R.id.btnDeleteValveAction:
                deleteActionValve();
                break;

            case R.id.btnPumpAction:
                showPumpActionConfig();
                break;
            case R.id.btnAddPumpAction:
                addActionPump();
                break;
            case R.id.btnDeletePumpAction:
                deleteActionPump();
                break;

            case R.id.btnADSamplingAction:
                showAdSamplingActionConfig();
                break;
            case R.id.btnAddADSampleAction:
                addActionADSample();
                break;
            case R.id.btnDeleteADSampleAction:
                deleteActionADSample();
                break;

            case R.id.btnwaitingAction:
                showWaitActionConfig();
                break;
            case R.id.btnAddWaitTimeAction:
                addActionWaitTime();
                break;
            case R.id.btnDeleteWaitTimeAction:
                deleteActionWaitTime();
                break;

            case R.id.btnInjectionAction:
                showInjectionActionConfig();
                break;
            case R.id.btnAddInjectionAction:
                addActionInjection();
                break;
            case R.id.btnDeleteInjectionAction:
                deleteActionInjection();
                break;
        }
    }
    //按钮事件对应函数结束
}
