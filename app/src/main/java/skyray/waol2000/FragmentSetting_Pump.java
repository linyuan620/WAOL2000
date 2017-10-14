package skyray.waol2000;

import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;

import skyray.waol2000.Adapter.PumpListAdapter;
import skyray.waol2000.Utils.AlertDialogManager;
import skyray.waol2000.Utils.SimpleMessageShower;
import skyray.waol2000.controller.DeleteChecker;
import skyray.waol2000.controller.PumpInfoController;
import skyray.waol2000.datamodel.PumpInfo;

/**
 * 手动操作界面
 */

public class FragmentSetting_Pump extends FragmentBase implements View.OnClickListener {
    public FragmentSetting_Pump() {
        layoutID = R.layout.fragment_setting_pump;
    }

    EditText pumpName = null;
    EditText impulseCount = null;
    SwitchButton direction = null;
    Button btnAddPump = null;
    Button btnDeletePump = null;
    ListView lvPumps = null;
    /**
     * 阀列表显示适配器
     */
    PumpListAdapter listViewAdpater = null;

    @Override
    protected void onInit() {
        lvPumps = (ListView) (getRootView().findViewById(R.id.pump_list_view));
        pumpName = (EditText) (getRootView().findViewById(R.id.etPumpName));
        impulseCount = (EditText) (getRootView().findViewById(R.id.etImpulseCount));
        direction = (SwitchButton) (getRootView().findViewById(R.id.sb_pump_direction));
        direction.setChecked(true);
        btnAddPump = (Button) (getRootView().findViewById(R.id.btnAddPump));
        btnAddPump.setOnClickListener(this);
        btnDeletePump = (Button) (getRootView().findViewById(R.id.btnDeletePump));
        btnDeletePump.setOnClickListener(this);

        listViewAdpater = new PumpListAdapter(getContext(), PumpInfoController.getAPC().getAllInfos());
        lvPumps.setAdapter(listViewAdpater);

        lvPumps.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                listViewAdpater.setSelectItem(arg2);
                showSelectPumpInfo(arg2);
                refreshDatas();
            }
        });
    }

    private void showSelectPumpInfo(int position) {
        selectedIndex = position;
        pumpName.setText(PumpInfoController.getAPC().getAllInfos().get(position).getPumpName());
        impulseCount.setText(String.valueOf(PumpInfoController.getAPC().getAllInfos().get(position).getImpulseCount()));
        int directionValue = PumpInfoController.getAPC().getAllInfos().get(position).getDirection();
        direction.setChecked(directionValue == 0);
    }

    private void refreshDatas() {
        listViewAdpater.notifyDataSetInvalidated();
        listViewAdpater.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddPump:
                addPump();
                break;
            case R.id.btnDeletePump:
                deletePump();
                break;
        }
    }

    private void deletePump() {
        if (selectedIndex > -1 && selectedIndex < PumpInfoController.getAPC().getAllInfos().size()) {
            final PumpInfo info = PumpInfoController.getAPC().getAllInfos().get(selectedIndex);
            boolean using = DeleteChecker.checkPumpInfoUsing(info);
            if (using) {
                SimpleMessageShower.showMsg(getContext(), "有泵动作使用选择泵信息，不能删除！");
            } else {
                AlertDialogManager.showDialog(getContext(), "确认删除？",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                PumpInfoController.getAPC().deleteInfo(info);
                                clearInputAndSelect();
                            }
                        }
                );
            }
        }
    }

    private void clearInputAndSelect() {
        pumpName.setText("");
        impulseCount.setText("");
        direction.setChecked(true);
        refreshDatas();
        selectedIndex = -1;
        listViewAdpater.setSelectItem(selectedIndex);
        pumpName.requestFocus();
    }

    void addPump() {
        if (pumpName.getText().toString().equals("") || impulseCount.getText().toString().equals("")) {
            Toast.makeText(getContext(), "请先输入 泵名称 和 脉冲计数 ！", Toast.LENGTH_SHORT).show();
            return;
        }

        String pNameValue = pumpName.getText().toString();
        int impulseCountV = Integer.parseInt(impulseCount.getText().toString());
        int directionV = direction.isChecked() ? 0 : 1;

        PumpInfo pinfo = new PumpInfo(pNameValue, impulseCountV, directionV);

        PumpInfoController.getAPC().updateInfo(pinfo);
        clearInputAndSelect();
    }

    /**
     * 当前选中项
     */
    private int selectedIndex = -1;

}
