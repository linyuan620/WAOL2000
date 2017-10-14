package skyray.waol2000;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import skyray.progressbarlib.ProgressWheel;
import skyray.waol2000.Adapter.FlowPActionBundleListAdapter;
import skyray.waol2000.Adapter.FlowPListAdapter;
import skyray.waol2000.Core.ActionBundleManage;
import skyray.waol2000.Core.RunFlowManage;
import skyray.waol2000.Utils.SimpleMessageShower;
import skyray.waol2000.datamodel.ActionBundle;
import skyray.waol2000.datamodel.FlowDetail;
import skyray.waol2000.datamodel.FlowInfo;
import skyray.waol2000.datamodel.Messages;

/**
 * 手动操作界面
 */

public class FragmentMaintain_HandControl extends FragmentBase implements View.OnClickListener {
    public FragmentMaintain_HandControl() {
        layoutID = R.layout.fragment_handcontrol;
    }

    Button btnExcuteAllActionBundle = null;
    FlowPListAdapter flowPAdapter = null;
    ListView lvFlows = null;
    List<ActionBundle> abundles = null;
    FlowPActionBundleListAdapter flowPActionBundleAdapter = null;
    ListView lvActionBundles = null;

    Button btnExcuteFlowP = null;
    Button btnExcuteActionBundle = null;
    ProgressWheel progressWheel_excuteBundle = null;
    ProgressWheel progressWheel_excuteAction = null;

    private void bindActionBundlesByFlowP(FlowInfo flowInfo) {
        List<ActionBundle> abundles = null;
        if (abundles == null) {
            abundles = new ArrayList<>();
        } else {
            abundles.clear();
        }
        if (flowInfo != null) {
            List<FlowDetail> fd = FlowDetail.find(FlowDetail.class, " Flow_ID = ? ", flowInfo.getId().toString());
            for (int i = 0; i < fd.size(); i++) {
                ActionBundle actionBundle = ActionBundle.findById(ActionBundle.class, fd.get(i).getActionBundleID());
                abundles.add(actionBundle);
            }

            flowPActionBundleAdapter = new FlowPActionBundleListAdapter(getContext(), abundles);

            lvActionBundles.setAdapter(flowPActionBundleAdapter);
            lvActionBundles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    flowPActionBundleAdapter.setSelectItem(position);
                    flowPActionBundleAdapter.notifyDataSetInvalidated();
                    flowPActionBundleAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    protected void onInit() {
        lvFlows = (ListView) (getRootView().findViewById(R.id.flow_p_list_view));
        lvActionBundles = (ListView) (getRootView().findViewById(R.id.flow_p_actionbundle_list_view));

        final List<FlowInfo> flows = FlowInfo.listAll(FlowInfo.class); //FlowInfo.find(FlowInfo.class," Flow_Type_V = ? ", FlowType.P.toString());
        flowPAdapter = new FlowPListAdapter(getContext(), flows);

        lvFlows.setAdapter(flowPAdapter);
        lvFlows.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                flowPAdapter.setSelectItem(position);
                bindActionBundlesByFlowP(flows.get(position));
                flowPAdapter.notifyDataSetInvalidated();
                flowPAdapter.notifyDataSetChanged();
            }
        });

        btnExcuteFlowP = (Button) (getRootView().findViewById(R.id.btnExcuteFlowP));
        btnExcuteFlowP.setOnClickListener(this);
        btnExcuteActionBundle = (Button) (getRootView().findViewById(R.id.btnExcuteActionBundle));
        btnExcuteActionBundle.setOnClickListener(this);

        progressWheel_excuteBundle = (ProgressWheel) (getRootView().findViewById(R.id.progress_wheel_excute_bundle));
        progressWheel_excuteAction = (ProgressWheel) (getRootView().findViewById(R.id.progress_wheel_excute_action));
    }

    private void excuteFlowP() {
        if (flowPAdapter.getSelectItemPosition() > -1) {
            RunFlowManage rm = new RunFlowManage();
            rm.RunFlow(((FlowInfo) flowPAdapter.getItem(flowPAdapter.getSelectItemPosition())).getId().intValue());
        }
    }

    private void excuteActionBundle() {
        if (flowPActionBundleAdapter.getSelectItemPosition() > -1) {
            ActionBundle ab = (ActionBundle) flowPActionBundleAdapter.getItem(flowPActionBundleAdapter.getSelectItemPosition());
            ActionBundleManage.runActionBundle(ab);
        }
    }

    void setExcuteOver(Message msg) {
        if (msg.obj != null) {
            ExcuteResponse er = (ExcuteResponse) msg.obj;
            if (er.IfBundle) {
                progressWheel_excuteBundle.setVisibility(View.GONE);
            } else {
                progressWheel_excuteAction.setVisibility(View.GONE);
            }
            if (!er.ErrorMsg.equals("")) {
                SimpleMessageShower.showMsg(getContext(), er.ErrorMsg);
            }
        }
        isRunning = false;
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Messages.ExcuteHandControlOver:
                    setExcuteOver(msg);
                    break;
                default:
                    break;
            }
        }
    };

    boolean isRunning = false;

    private void excute(boolean ifBundle) {
        if (!isRunning) {
            isRunning = true;
            if (ifBundle) {
                progressWheel_excuteBundle.setVisibility(View.VISIBLE);
            } else {
                progressWheel_excuteAction.setVisibility(View.VISIBLE);
            }

            ExcuteThread excuteThread = new ExcuteThread();
            excuteThread.excute(ifBundle);
        } else {
            SimpleMessageShower.showMsg(getContext(), "后台执行中，请稍候！");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnExcuteFlowP:
                excute(true);
                break;
            case R.id.btnExcuteActionBundle:
                excute(false);
                break;
        }
    }

    class ExcuteResponse {
        public String ErrorMsg = "";
        public boolean IfBundle = false;
    }

    class ExcuteThread extends Thread {

        @Override
        public void run() {
            String msgStr = "";
            try {
                if (_ifBundle) {
                    FragmentMaintain_HandControl.this.excuteFlowP();
                } else {
                    FragmentMaintain_HandControl.this.excuteActionBundle();
                }
            } catch (Exception ex) {
                msgStr = ex.getMessage();
            }

            ExcuteResponse er = new ExcuteResponse();
            er.ErrorMsg = msgStr;
            er.IfBundle = _ifBundle;

            Message msg = Message.obtain(FragmentMaintain_HandControl.this.handler, Messages.ExcuteHandControlOver);
            msg.obj = er;
            FragmentMaintain_HandControl.this.handler.sendMessage(msg);
        }

        boolean _ifBundle = false;

        public void excute(boolean ifBundle) {
            _ifBundle = ifBundle;
            super.start();
        }
    }
}
