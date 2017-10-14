package skyray.waol2000.Utils;

import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Administrator on 2017/6/21.
 */

public class AlertDialogManager {

    /**
     * 提示框
     *
     * @param context
     * @param message        信息
     * @param cancelHandler  点击取消执行代码
     * @param confirmHandler 点击确认执行代码
     */
    public static void showDialog(Context context, String message, DialogInterface.OnClickListener cancelHandler,
                                  DialogInterface.OnClickListener confirmHandler) {
          /*
          这里使用了 android.support.v7.app.AlertDialog.Builder
          可以直接在头部写 import android.support.v7.app.AlertDialog
          那么下面就可以写成 AlertDialog.Builder
          */
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(message);
        builder.setNegativeButton("取消", cancelHandler);
        builder.setPositiveButton("确定", confirmHandler);
        builder.show();
    }

    /**
     * 只处理确定点击时间对话框
     * @param context
     * @param message 提示信息
     * @param confirmHandler 点击确认执行代码
     */
    public static void showDialog(Context context, String message, DialogInterface.OnClickListener confirmHandler) {
        showDialog(context, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }, confirmHandler);
    }
}
