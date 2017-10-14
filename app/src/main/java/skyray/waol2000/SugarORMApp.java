package skyray.waol2000;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;

import com.orm.SugarContext;

import skyray.waol2000.Core.MyLogManager;
import skyray.waol2000.Utils.AlertDialogManager;

/**
 * Sugar ORM需要实现
 */
public class SugarORMApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

    /**
     * 退出app代码
     *
     * @param c 当前上下文
     */
    public static void exit(Context c) {
        AlertDialogManager.showDialog(c, c.getResources().getString(R.string.iExitConfirm),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyLogManager.addLogInfo("程序退出");
                        int id = android.os.Process.myPid();
                        android.os.Process.killProcess(id);
                    }
                }
        );
    }
}
