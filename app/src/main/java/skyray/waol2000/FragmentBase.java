package skyray.waol2000;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 仪表维护 片段界面基类
 */

public class FragmentBase extends Fragment {

    protected void onInit() {

    }

    /**
     * 界面ID
     */
    protected int layoutID = 0;

    private View rootView = null;

    protected View getRootView() {
        return rootView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(layoutID, container, false);
        }
        onInit();
        return rootView;
    }

}
