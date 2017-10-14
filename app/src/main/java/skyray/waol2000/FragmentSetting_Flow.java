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

import com.orm.SugarRecord;

import skyray.waol2000.Adapter.ActionBundleDetailListAdapter;
import skyray.waol2000.Adapter.ActionBundleListAdapter;
import skyray.waol2000.Adapter.FlowDetailListAdapter;
import skyray.waol2000.Adapter.FlowListAdapter;
import skyray.waol2000.Utils.AlertDialogManager;
import skyray.waol2000.controller.ActionADSampleController;
import skyray.waol2000.controller.ActionBundleController;
import skyray.waol2000.controller.ActionBundleDetailController;
import skyray.waol2000.controller.ActionInjectionController;
import skyray.waol2000.controller.ActionPumpController;
import skyray.waol2000.controller.ActionValveController;
import skyray.waol2000.controller.ActionWaitTimeController;
import skyray.waol2000.controller.DeleteChecker;
import skyray.waol2000.controller.FlowController;
import skyray.waol2000.controller.FlowDetailController;
import skyray.waol2000.Utils.SimpleMessageShower;
import skyray.waol2000.datamodel.ActionADSample;
import skyray.waol2000.datamodel.ActionBundle;
import skyray.waol2000.datamodel.ActionBundleDetail;
import skyray.waol2000.datamodel.ActionInjection;
import skyray.waol2000.datamodel.ActionPump;
import skyray.waol2000.datamodel.ActionType;
import skyray.waol2000.datamodel.ActionValve;
import skyray.waol2000.datamodel.ActionWaitTime;
import skyray.waol2000.datamodel.FlowDetail;
import skyray.waol2000.datamodel.FlowInfo;
import skyray.waol2000.datamodel.FlowType;

/**
 * 手动操作界面
 */

public class FragmentSetting_Flow extends FragmentBase implements View.OnClickListener {
    public FragmentSetting_Flow() {
        layoutID = R.layout.fragment_setting_flow;
    }

    //动作包相关
    Button btnActionBundle = null;
    LinearLayout LL_ActionBundleContrainer = null;
    EditText etActionBundleName = null;
    Button btnAddActionBundle = null;
    Button btnDeleteActionBundle = null;
    ListView lvActionBundle = null;
    ActionBundleListAdapter actionBundleListAdapter = null;

    private void initActionBundleControls() {
        btnActionBundle = (Button) (getRootView().findViewById(R.id.btnActionBundle));
        btnActionBundle.setOnClickListener(this);
        LL_ActionBundleContrainer = (LinearLayout) (getRootView().findViewById(R.id.ll_ActionBundle));

        etActionBundleName = (EditText) (getRootView().findViewById(R.id.etActionBundleName));

        btnAddActionBundle = (Button) (getRootView().findViewById(R.id.btnAddActionBundle));
        btnAddActionBundle.setOnClickListener(this);
        btnDeleteActionBundle = (Button) (getRootView().findViewById(R.id.btnDeleteActionBundle));
        btnDeleteActionBundle.setOnClickListener(this);
        lvActionBundle = (ListView) (getRootView().findViewById(R.id.actionbundle_list_view));
        actionBundleListAdapter = new ActionBundleListAdapter(getContext(), ActionBundleController.getASC().getAllInfos());
        lvActionBundle.setAdapter(actionBundleListAdapter);
        lvActionBundle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                actionBundleListAdapter.setSelectItem(position);
                showSelectActionBundleInfo(position);
                refreshActionBundles();
            }
        });
    }

    private void showActionBundleConfig() {
        LL_ActionBundleContrainer.setVisibility(View.VISIBLE);
        LL_ActionBundleDetailContrainer.setVisibility(View.GONE);
        LL_FlowContrainer.setVisibility(View.GONE);
        LL_FlowDetailContrainer.setVisibility(View.GONE);
    }

    //动作包细节开始
    Button btnActionBundleDetail = null;
    LinearLayout LL_ActionBundleDetailContrainer = null;
    Spinner ddl_ActionBundleNames = null;
    Spinner ddl_ActionTypes = null;
    Spinner ddl_ActionNames = null;
    EditText etActionOrder = null;
    Button btnAddActionBundleDetail = null;
    Button btnDeleteActionBundleDetail = null;
    ListView lvActionBundleDetail = null;
    ActionBundleDetailListAdapter actionBundleDetailListAdapter = null;

    private void bindActionTypes() {
        ArrayAdapter<CharSequence> adapterBR = ArrayAdapter.createFromResource(
                getContext(), R.array.actionTypes,
                android.R.layout.simple_spinner_item);
        ddl_ActionTypes.setAdapter(adapterBR);

        ddl_ActionTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bindActionNames(ActionType.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    private void showActionBundleDetailConfig() {
        LL_ActionBundleContrainer.setVisibility(View.GONE);
        LL_ActionBundleDetailContrainer.setVisibility(View.VISIBLE);
        LL_FlowContrainer.setVisibility(View.GONE);
        LL_FlowDetailContrainer.setVisibility(View.GONE);
    }

    void initActionBundleDetailControls() {
        btnActionBundleDetail = (Button) (getRootView().findViewById(R.id.btnActionBundleDetail));
        btnActionBundleDetail.setOnClickListener(this);
        LL_ActionBundleDetailContrainer = (LinearLayout) (getRootView().findViewById(R.id.ll_ActionBundleDetail));

        ddl_ActionBundleNames = (Spinner) (getRootView().findViewById(R.id.ddl_ActionBundleNames));
        bindActionBundleNames();
        ddl_ActionNames = (Spinner) (getRootView().findViewById(R.id.ddl_ActionName));
        ddl_ActionTypes = (Spinner) (getRootView().findViewById(R.id.ddl_ActionType));
        bindActionTypes();

        etActionOrder = (EditText) (getRootView().findViewById(R.id.etActionOrder));

        btnAddActionBundleDetail = (Button) (getRootView().findViewById(R.id.btnAddActionBundleDetail));
        btnAddActionBundleDetail.setOnClickListener(this);
        btnDeleteActionBundleDetail = (Button) (getRootView().findViewById(R.id.btnDeleteActionBundleDetail));
        btnDeleteActionBundleDetail.setOnClickListener(this);

        lvActionBundleDetail = (ListView) (getRootView().findViewById(R.id.actionbundledetail_list_view));
        actionBundleDetailListAdapter = new ActionBundleDetailListAdapter(getContext(), ActionBundleDetailController.getASC().getAllInfos());
        lvActionBundleDetail.setAdapter(actionBundleDetailListAdapter);
        lvActionBundleDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                actionBundleDetailListAdapter.setSelectItem(position);
                showSelectActionBundleDetailInfo(position);
                refreshActionBundleDetails();
            }
        });
    }

    //流程相关
    Button btnFlow = null;
    LinearLayout LL_FlowContrainer = null;
    EditText etFlowName = null;
    Spinner ddl_FlowTypes = null;
    Button btnAddFlow = null;
    Button btnDeleteFlow = null;
    ListView lvFlow = null;
    FlowListAdapter flowListAdapter = null;

    private void showFlowConfig() {
        LL_ActionBundleContrainer.setVisibility(View.GONE);
        LL_ActionBundleDetailContrainer.setVisibility(View.GONE);
        LL_FlowContrainer.setVisibility(View.VISIBLE);
        LL_FlowDetailContrainer.setVisibility(View.GONE);
    }

    private void bindFlowTypes() {
        ArrayAdapter<CharSequence> adapterBR = ArrayAdapter.createFromResource(
                getContext(), R.array.flowTypes,
                android.R.layout.simple_spinner_item);
        ddl_FlowTypes.setAdapter(adapterBR);
    }

    void initFlowControls() {
        btnFlow = (Button) (getRootView().findViewById(R.id.btnActionFlow));
        btnFlow.setOnClickListener(this);
        LL_FlowContrainer = (LinearLayout) (getRootView().findViewById(R.id.ll_Flow));

        etFlowName = (EditText) (getRootView().findViewById(R.id.etFlowName));
        ddl_FlowTypes = (Spinner) (getRootView().findViewById(R.id.ddl_FlowType));
        bindFlowTypes();

        btnAddFlow = (Button) (getRootView().findViewById(R.id.btnAddFlow));
        btnAddFlow.setOnClickListener(this);
        btnDeleteFlow = (Button) (getRootView().findViewById(R.id.btnDeleteFlow));
        btnDeleteFlow.setOnClickListener(this);

        lvFlow = (ListView) (getRootView().findViewById(R.id.flow_list_view));
        flowListAdapter = new FlowListAdapter(getContext(), FlowController.getASC().getAllInfos());
        lvFlow.setAdapter(flowListAdapter);
        lvFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                flowListAdapter.setSelectItem(position);
                showSelectFlowInfo(position);
                refreshFlows();
            }
        });
    }

    //流程细节相关
    Button btnFlowDetail = null;
    LinearLayout LL_FlowDetailContrainer = null;
    Spinner ddl_FlowNames = null;
    Spinner ddl_ActionBundles = null;
    EditText etExcuteOrder = null;
    Button btnAddFlowDetail = null;
    Button btnDeleteFlowDetail = null;
    ListView lvFlowDetail = null;
    FlowDetailListAdapter flowDetailListAdapter = null;

    private void showFlowDetailConfig() {
        LL_ActionBundleContrainer.setVisibility(View.GONE);
        LL_ActionBundleDetailContrainer.setVisibility(View.GONE);
        LL_FlowContrainer.setVisibility(View.GONE);
        LL_FlowDetailContrainer.setVisibility(View.VISIBLE);
    }

    ArrayAdapter<FlowInfo> ddlFlow_Adapter = null;

    private void bindFlowToSpinner() {
        ddlFlow_Adapter = new ArrayAdapter<FlowInfo>(getContext(), android.R.layout.simple_spinner_item, FlowController.getASC().getAllInfos());
        ddl_FlowNames.setAdapter(ddlFlow_Adapter);
    }

    ArrayAdapter<ActionBundle> ddl_ActionBundle_Adapter = null;

    private void bindActionBundleToSpinner() {
        if (ddl_ActionBundle_Adapter == null) {
            ddl_ActionBundle_Adapter = new ArrayAdapter<ActionBundle>(getContext(),
                    android.R.layout.simple_spinner_item, ActionBundleController.getASC().getAllInfos());
        }
        ddl_ActionBundles.setAdapter(ddl_ActionBundle_Adapter);
    }

    private void initFlowDetailControls() {
        btnFlowDetail = (Button) (getRootView().findViewById(R.id.btnFlowDetail));
        btnFlowDetail.setOnClickListener(this);
        LL_FlowDetailContrainer = (LinearLayout) (getRootView().findViewById(R.id.ll_FlowDetail));
        ddl_FlowNames = (Spinner) (getRootView().findViewById(R.id.ddl_flowNames));
        bindFlowToSpinner();
        ddl_ActionBundles = (Spinner) (getRootView().findViewById(R.id.ddl_ActionBundles));
        bindActionBundleToSpinner();
        etExcuteOrder = (EditText) (getRootView().findViewById(R.id.et_ExcuteOrder));
        btnAddFlowDetail = (Button) (getRootView().findViewById(R.id.btnAddFlowDetail));
        btnAddFlowDetail.setOnClickListener(this);
        btnDeleteFlowDetail = (Button) (getRootView().findViewById(R.id.btnDeleteFlowDetail));
        btnDeleteFlowDetail.setOnClickListener(this);
        lvFlowDetail = (ListView) (getRootView().findViewById(R.id.flowdetail_list_view));
        flowDetailListAdapter = new FlowDetailListAdapter(getContext(), FlowDetailController.getASC().getAllInfos());

        lvFlowDetail.setAdapter(flowDetailListAdapter);
        lvFlowDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                flowDetailListAdapter.setSelectItem(position);
                showSelectFlowDetailInfo(position);
                refreshFlowDetails();
            }
        });
    }

    @Override
    protected void onInit() {
        initActionBundleControls();
        initActionBundleDetailControls();
        initFlowControls();
        initFlowDetailControls();
    }

    //动作包开始
    int selectedActionBundleIndex = -1;

    private void showSelectActionBundleInfo(int position) {
        try {
            selectedActionBundleIndex = position;
            etActionBundleName.setText(ActionBundleController.getASC().getAllInfos().get(position).getBundleName().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshActionBundles() {
        actionBundleListAdapter.notifyDataSetInvalidated();
        actionBundleListAdapter.notifyDataSetChanged();
    }

    private void addActionBundle() {
        try {
            if (etActionBundleName.getText().toString().trim().equals("")) {
                SimpleMessageShower.showMsg(getContext(), "请输入 动作包名称 ！");
                return;
            }

            ActionBundle av = new ActionBundle();
            av.setBundleName(etActionBundleName.getText().toString().trim());
            ActionBundleController.getASC().updateInfo(av);
            ddl_ActionBundle_Adapter.notifyDataSetInvalidated();
            ddl_ActionBundle_Adapter.notifyDataSetChanged();

            selectedActionBundleIndex = -1;
            actionBundleListAdapter.setSelectItem(selectedActionBundleIndex);
            refreshActionBundles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteActionBundle() {
        if (selectedActionBundleIndex > -1) {
            final ActionBundle actionBundle = ActionBundleController.getASC().getAllInfos().get(selectedActionBundleIndex);
            boolean using = DeleteChecker.checkActionBundleUsing(actionBundle);
            if (using) {
                SimpleMessageShower.showMsg(getContext(), "有流程使用选择动作包或动作包包含动作，不能删除！");
            } else {
                AlertDialogManager.showDialog(getContext(), "确认删除？",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActionBundleController.getASC().deleteInfo(actionBundle);
                                selectedActionBundleIndex = -1;
                                refreshActionBundles();
                            }
                        }
                );
            }
        }
    }
    //动作包结束

    //动作包细节开始
    int selectedActionBundleDetailIndex = -1;

    private void bindActionNames(ActionType actionType) {
        switch (actionType) {
            case Valve:
                ArrayAdapter<ActionValve> adapter = new ArrayAdapter<ActionValve>(getContext(), android.R.layout.simple_spinner_item,
                        ActionValveController.getAVC().getAllInfos());
                ddl_ActionNames.setAdapter(adapter);
                break;
            case Pump:
                ArrayAdapter<ActionPump> adapter2 = new ArrayAdapter<ActionPump>(getContext(), android.R.layout.simple_spinner_item,
                        ActionPumpController.getAPC().getAllInfos());
                ddl_ActionNames.setAdapter(adapter2);
                break;
            case ADSample:
                ArrayAdapter<ActionADSample> adapter3 = new ArrayAdapter<ActionADSample>(getContext(), android.R.layout.simple_spinner_item,
                        ActionADSampleController.getASC().getAllInfos());
                ddl_ActionNames.setAdapter(adapter3);
                break;
            case WaitTime:
                ArrayAdapter<ActionWaitTime> adapter4 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,
                        ActionWaitTimeController.getASC().getAllInfos());
                ddl_ActionNames.setAdapter(adapter4);
                break;
            case Injection:
                ArrayAdapter<ActionInjection> adapter5 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,
                        ActionInjectionController.getAIC().getAllInfoContainDefault());
                ddl_ActionNames.setAdapter(adapter5);
                break;
        }
    }

    private void showSelectActionBundleDetailInfo(int position) {
        try {
            selectedActionBundleDetailIndex = position;

            int abNamesC = ddl_ActionBundleNames.getAdapter().getCount();
            for (int i = 0; i < abNamesC; i++) {
                if (ddl_ActionBundleNames.getAdapter().getItem(i).toString().equals(ActionBundleDetailController.getASC().getAllInfos().get(position).getBundleInfo().getBundleName())) {
                    ddl_ActionBundleNames.setSelection(i);
                    break;
                }
            }

            ddl_ActionTypes.setSelection(ActionBundleDetailController.getASC().getAllInfos().get(position).getActionTypeV().value());

            int actionNameSection = -1;
            boolean gettedActionName = false;
            int aNameC = ddl_ActionNames.getAdapter().getCount();
            for (int i = 0; i < aNameC; i++) {
                int aID = ActionBundleDetailController.getASC().getAllInfos().get(position).getActionID();
                SugarRecord sr = (SugarRecord) ddl_ActionNames.getAdapter().getItem(i);
                if (sr != null && sr.getId() == aID) {
                    gettedActionName = true;
                    actionNameSection = i;
                    break;
                }
            }
            if (gettedActionName) {
                ddl_ActionNames.setSelection(actionNameSection);
            }

            etActionOrder.setText(String.valueOf(ActionBundleDetailController.getASC().getAllInfos().get(position).getOrderValue()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshActionBundleDetails() {
        actionBundleDetailListAdapter.notifyDataSetInvalidated();
        actionBundleDetailListAdapter.notifyDataSetChanged();
    }

    private void bindActionBundleNames() {
        if (ddl_ActionBundle_Adapter == null) {
            ddl_ActionBundle_Adapter = new ArrayAdapter<ActionBundle>(getContext(), android.R.layout.simple_spinner_item, ActionBundleController.getASC().getAllInfos());
        }
        ddl_ActionBundleNames.setAdapter(ddl_ActionBundle_Adapter);
    }

    private void addActionBundleDetail() {
        try {
            if (etActionOrder.getText().toString().trim().equals("")) {
                SimpleMessageShower.showMsg(getContext(), "请输入 顺序 ！");
                return;
            }

            ActionBundleDetail av = new ActionBundleDetail();
            av.setBundleInfo((ActionBundle) ddl_ActionBundleNames.getSelectedItem());
            av.setActionTypeV(ActionType.valueOf(ddl_ActionTypes.getSelectedItemPosition()));
            SugarRecord aID = (SugarRecord) ddl_ActionNames.getSelectedItem();
            if (aID != null) {
                av.setActionID(aID.getId().intValue());
            }
            av.setOrderValue(Integer.parseInt(etActionOrder.getText().toString().trim()));
            ActionBundleDetailController.getASC().updateInfo(av);

            selectedActionBundleDetailIndex = -1;
            actionBundleDetailListAdapter.setSelectItem(selectedActionBundleDetailIndex);
            refreshActionBundleDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteActionBundleDetail() {
        if (selectedActionBundleDetailIndex > -1) {
            final ActionBundleDetail actionBundleDetail = ActionBundleDetailController.getASC().getAllInfos().get(selectedActionBundleDetailIndex);
            AlertDialogManager.showDialog(getContext(), "确认删除？",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActionBundleDetailController.getASC().deleteInfo(actionBundleDetail);
                            selectedActionBundleDetailIndex = -1;
                            refreshActionBundleDetails();
                        }
                    }
            );
        }
    }

    //动作包细节结束

    //流程开始
    int selectedFlowIndex = -1;

    private void showSelectFlowInfo(int position) {
        try {
            selectedFlowIndex = position;
            etFlowName.setText(FlowController.getASC().getAllInfos().get(position).getFlowName().toString());
            FlowType flowType = FlowController.getASC().getAllInfos().get(position).getFlowTypeV();
            ddl_FlowTypes.setSelection(flowType.value());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshFlows() {
        flowListAdapter.notifyDataSetInvalidated();
        flowListAdapter.notifyDataSetChanged();
    }

    private void addFlow() {
        try {
            if (etFlowName.getText().toString().trim().equals("")) {
                SimpleMessageShower.showMsg(getContext(), "请输入 流程名称 ！");
                return;
            }

            FlowInfo av = new FlowInfo();
            av.setFlowName(etFlowName.getText().toString().trim());
            av.setFlowTypeV(FlowType.valueOf(ddl_FlowTypes.getSelectedItemPosition()));
            FlowController.getASC().updateInfo(av);
            ddlFlow_Adapter.notifyDataSetInvalidated();
            ddlFlow_Adapter.notifyDataSetChanged();
            selectedFlowIndex = -1;
            flowListAdapter.setSelectItem(selectedFlowIndex);
            refreshFlows();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteFlow() {
        if (selectedFlowIndex > -1) {
            final FlowInfo flowInfo = FlowController.getASC().getAllInfos().get(selectedFlowIndex);
            boolean using = DeleteChecker.checkFlowUsing(flowInfo);
            if (using) {
                SimpleMessageShower.showMsg(getContext(), "流程包含细节信息，不能删除！");
            } else {
                AlertDialogManager.showDialog(getContext(), "确认删除？",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FlowController.getASC().deleteInfo(flowInfo);
                                selectedFlowIndex = -1;
                                refreshFlows();
                            }
                        }
                );
            }
        }
    }
    //流程结束

    //流程细节开始
    int selectedFlowDetailIndex = -1;

    private void showSelectFlowDetailInfo(int position) {
        try {
            selectedFlowDetailIndex = position;
            FlowDetail fd = FlowDetailController.getASC().getAllInfos().get(position);
            for (int i = 0; i < ddl_FlowNames.getAdapter().getCount(); i++) {
                FlowInfo finfo = (FlowInfo) ddl_FlowNames.getAdapter().getItem(i);
                if (finfo.getId() == fd.getFlowID()) {
                    ddl_FlowNames.setSelection(i);
                    break;
                }
            }
            for (int i = 0; i < ddl_ActionBundles.getAdapter().getCount(); i++) {
                ActionBundle finfo = (ActionBundle) ddl_ActionBundles.getAdapter().getItem(i);
                if (finfo.getId() == fd.getActionBundleID()) {
                    ddl_ActionBundles.setSelection(i);
                    break;
                }
            }

            etExcuteOrder.setText(String.valueOf(fd.getOrderValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshFlowDetails() {
        flowDetailListAdapter.notifyDataSetInvalidated();
        flowDetailListAdapter.notifyDataSetChanged();
    }

    private void addFlowDetail() {
        try {
            int order = 0;
            if (!etExcuteOrder.getText().toString().trim().equals("")) {
                order = Integer.parseInt(etExcuteOrder.getText().toString().trim());
            }

            FlowDetail av = new FlowDetail();
            av.setFlowID(((FlowInfo) ddl_FlowNames.getAdapter().getItem(ddl_FlowNames.getSelectedItemPosition())).getId().intValue());
            av.setActionBundleID(((ActionBundle) (ddl_ActionBundles.getAdapter().getItem(ddl_ActionBundles.getSelectedItemPosition()))).getId().intValue());
            av.setOrderValue(order);

            FlowDetailController.getASC().updateInfo(av);
            selectedFlowDetailIndex = -1;
            flowDetailListAdapter.setSelectItem(selectedFlowDetailIndex);
            refreshFlowDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteFlowDetail() {
        if (selectedFlowDetailIndex > -1) {
            AlertDialogManager.showDialog(getContext(), "确认删除？",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FlowDetailController.getASC().deleteInfo(FlowDetailController.getASC().getAllInfos().get(selectedFlowDetailIndex));
                            selectedFlowDetailIndex = -1;
                            refreshFlowDetails();
                        }
                    }
            );
        }
    }
    //流程细节结束

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnActionBundle:
                showActionBundleConfig();
                break;
            case R.id.btnAddActionBundle:
                addActionBundle();
                break;
            case R.id.btnDeleteActionBundle:
                deleteActionBundle();
                break;
            case R.id.btnActionBundleDetail:
                showActionBundleDetailConfig();
                break;
            case R.id.btnAddActionBundleDetail:
                addActionBundleDetail();
                break;
            case R.id.btnDeleteActionBundleDetail:
                deleteActionBundleDetail();
                break;
            case R.id.btnActionFlow:
                showFlowConfig();
                break;
            case R.id.btnAddFlow:
                addFlow();
                break;
            case R.id.btnDeleteFlow:
                deleteFlow();
                break;
            case R.id.btnFlowDetail:
                showFlowDetailConfig();
                break;
            case R.id.btnAddFlowDetail:
                addFlowDetail();
                break;
            case R.id.btnDeleteFlowDetail:
                deleteFlowDetail();
                break;
        }
    }
}
