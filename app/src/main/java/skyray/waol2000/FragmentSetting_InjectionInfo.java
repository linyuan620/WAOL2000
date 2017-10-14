package skyray.waol2000;

import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import skyray.waol2000.Adapter.InjectionInfoListAdapter;
import skyray.waol2000.Utils.AlertDialogManager;
import skyray.waol2000.Utils.SimpleMessageShower;
import skyray.waol2000.controller.DeleteChecker;
import skyray.waol2000.controller.InjectionInfoController;
import skyray.waol2000.datamodel.InjectionInfo;

/**
 * 手动操作界面
 */

public class FragmentSetting_InjectionInfo extends FragmentBase implements View.OnClickListener {
    public FragmentSetting_InjectionInfo() {
        layoutID = R.layout.fragment_setting_injectioninfo;
    }

    EditText etInjectionName = null;
    EditText etInputPort = null;
    EditText etOutputPort = null;
    Spinner spHeightType = null;
    Button btnAddInjection = null;
    Button btnDeleteInjection = null;
    ListView lvInjectionInfos = null;
    /**
     * 进样列表显示适配器
     */
    InjectionInfoListAdapter listViewAdpater = null;

    private void bindHeightTypes() {
        ArrayAdapter<CharSequence> adapterBR = ArrayAdapter.createFromResource(
                getContext(), R.array.heightTypes,
                android.R.layout.simple_spinner_item);
        spHeightType.setAdapter(adapterBR);
    }

    @Override
    protected void onInit() {
        lvInjectionInfos = (ListView) (getRootView().findViewById(R.id.injectioninfo_list_view));
        etInjectionName = (EditText) (getRootView().findViewById(R.id.etInjectionName));
        etInputPort = (EditText) (getRootView().findViewById(R.id.etInputPort));
        etOutputPort = (EditText) (getRootView().findViewById(R.id.etOutputPort));
        spHeightType = (Spinner) (getRootView().findViewById(R.id.ddl_HeightType));
        bindHeightTypes();

        btnAddInjection = (Button) (getRootView().findViewById(R.id.btnAddInjectionInfo));
        btnAddInjection.setOnClickListener(this);
        btnDeleteInjection = (Button) (getRootView().findViewById(R.id.btnDeleteInjectionInfo));
        btnDeleteInjection.setOnClickListener(this);

        listViewAdpater = new InjectionInfoListAdapter(getContext(), InjectionInfoController.getAPC().getAllInfos());
        lvInjectionInfos.setAdapter(listViewAdpater);

        lvInjectionInfos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                listViewAdpater.setSelectItem(arg2);
                showSelectInjectionInfoInfo(arg2);
                refreshDatas();
            }
        });
    }

    private void showSelectInjectionInfoInfo(int position) {
        selectedIndex = position;
        etInjectionName.setText(InjectionInfoController.getAPC().getAllInfos().get(position).getInjectionName());
        etInputPort.setText(String.valueOf(InjectionInfoController.getAPC().getAllInfos().get(position).getInputPort()));
        etOutputPort.setText(String.valueOf(InjectionInfoController.getAPC().getAllInfos().get(position).getOutputPort()));
        int height = InjectionInfoController.getAPC().getAllInfos().get(position).getHeight();
        spHeightType.setSelection(height);
    }

    private void refreshDatas() {
        listViewAdpater.notifyDataSetInvalidated();
        listViewAdpater.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddInjectionInfo:
                addInjectionInfo();
                break;
            case R.id.btnDeleteInjectionInfo:
                deleteInjectionInfo();
                break;
        }
    }

    private void deleteInjectionInfo() {
        if (selectedIndex > -1 && selectedIndex < InjectionInfoController.getAPC().getAllInfos().size()) {
            final InjectionInfo info = InjectionInfoController.getAPC().getAllInfos().get(selectedIndex);
            boolean using = DeleteChecker.checkInjectionInfoUsing(info);
            if (using) {
                SimpleMessageShower.showMsg(getContext(), "有进样动作使用选择进样信息，不能删除！");
            } else {
                AlertDialogManager.showDialog(getContext(), "确认删除？",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                InjectionInfoController.getAPC().deleteInfo(info);
                                clearInputAndSelect();
                            }
                        }
                );
            }
        }
    }

    private void clearInputAndSelect() {
        etInjectionName.setText("");
        etInputPort.setText("");
        etOutputPort.setText("");
        spHeightType.setSelection(0);
        refreshDatas();
        selectedIndex = -1;
        listViewAdpater.setSelectItem(selectedIndex);
        etInjectionName.requestFocus();
    }

    void addInjectionInfo() {
        if (etInjectionName.getText().toString().equals("") || etInputPort.getText().toString().equals("")
                || etOutputPort.getText().toString().equals("")) {
            Toast.makeText(getContext(), "请先输入 进样名称、输入端口和输出端口 ！", Toast.LENGTH_SHORT).show();
            return;
        }

        String pNameValue = etInjectionName.getText().toString();
        int inputP = Integer.parseInt(etInputPort.getText().toString());
        int outputP = Integer.parseInt(etOutputPort.getText().toString());
        int height = spHeightType.getSelectedItemPosition();
        InjectionInfo pinfo = new InjectionInfo(pNameValue, inputP, outputP, height);
        InjectionInfoController.getAPC().updateInfo(pinfo);

        clearInputAndSelect();
    }

    /**
     * 当前选中项
     */
    private int selectedIndex = -1;
}
