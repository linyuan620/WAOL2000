package skyray.waol2000;

import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import skyray.waol2000.Adapter.ValveListAdapter;
import skyray.waol2000.Utils.AlertDialogManager;
import skyray.waol2000.Utils.SimpleMessageShower;
import skyray.waol2000.controller.DeleteChecker;
import skyray.waol2000.controller.ValveInfoController;
import skyray.waol2000.datamodel.ValveInfo;

/**
 * 手动操作界面
 */

public class FragmentSetting_Valve extends FragmentBase implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddValve:
                addValve();
                break;
            case R.id.btnDeleteValve:
                deleteValve();
                break;
        }
    }

    public FragmentSetting_Valve() {
        layoutID = R.layout.fragment_setting_valve;
    }

    /**
     * 阀列表显示适配器
     */
    ValveListAdapter listViewAdpater = null;
    /**
     * 阀名称
     */
    EditText etValveName = null;
    /**
     * 命令号索引
     */
    EditText etCommandIndex = null;
    /**
     * 所有阀信息
     */
    ListView lvValves = null;
    /**
     * 添加阀
     */
    Button btnAdd = null;
    /**
     * 删除阀
     */
    Button btnDelete = null;

    @Override
    protected void onInit() {
        etValveName = (EditText) (getRootView().findViewById(R.id.etValveName));
        etCommandIndex = (EditText) (getRootView().findViewById(R.id.etCommandIndex));
        lvValves = (ListView) (getRootView().findViewById(R.id.valve_list_view));
        btnAdd = (Button) (getRootView().findViewById(R.id.btnAddValve));
        btnAdd.setOnClickListener(this);
        btnDelete = (Button) (getRootView().findViewById(R.id.btnDeleteValve));
        btnDelete.setOnClickListener(this);

        listViewAdpater = new ValveListAdapter(getContext(), ValveInfoController.getAPC().getAllInfos());
        lvValves.setAdapter(listViewAdpater);

        lvValves.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                listViewAdpater.setSelectItem(arg2);
                showSelectValveInfo(arg2);
                refreshDatas();
            }
        });
    }

    private void showSelectValveInfo(int position) {
        selectedIndex = position;
        etValveName.setText(ValveInfoController.getAPC().getAllInfos().get(position).getValveName());
        etCommandIndex.setText(String.valueOf(ValveInfoController.getAPC().getAllInfos().get(position).getCmdIndex()));
    }

    private void refreshDatas() {
        listViewAdpater.notifyDataSetInvalidated();
        listViewAdpater.notifyDataSetChanged();
    }

    public void addValve() {
        if (etValveName.getText().toString().equals("") || etCommandIndex.getText().toString().equals("")) {
            Toast.makeText(getContext(), "请先输入 阀名称 和 命令号索引 ！", Toast.LENGTH_SHORT).show();
            return;
        }

        String valveName = etValveName.getText().toString();
        int cmdIndex = Integer.parseInt(etCommandIndex.getText().toString());
        ValveInfo valveInfo = new ValveInfo(valveName,cmdIndex);
        ValveInfoController.getAPC().updateInfo(valveInfo);

        clearInputAndSelect();
    }

    private int selectedIndex = -1;

    private void clearInputAndSelect() {
        refreshDatas();
        etValveName.setText("");
        etCommandIndex.setText("");
        selectedIndex = -1;
        listViewAdpater.setSelectItem(selectedIndex);
        etValveName.requestFocus();
    }

    public void deleteValve() {
        if (selectedIndex > -1 && selectedIndex < ValveInfoController.getAPC().getAllInfos().size()) {
            final ValveInfo valveInfo = ValveInfoController.getAPC().getAllInfos().get(selectedIndex);
            boolean using = DeleteChecker.checkValveInfoUsing(valveInfo);
            if (using) {
                SimpleMessageShower.showMsg(getContext(), "有阀动作使用选择阀信息，不能删除！");
            } else {
                AlertDialogManager.showDialog(getContext(), "确认删除？",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ValveInfoController.getAPC().deleteInfo(valveInfo);
                                clearInputAndSelect();
                            }
                        }
                );
            }
        }
    }
}
