package skyray.waol2000.Utils;

import android.os.Message;
import android.os.Handler;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import skyray.waol2000.datamodel.Messages;

/**
 * 计时器用于定时向主界面发送系统时间
 */

public class SystemTimeShower {

    private static SystemTimeShower _shower = new SystemTimeShower();
    public static SystemTimeShower getSystemTimeShower()
    {
        return _shower;
    }

    boolean ifShowingSystemTime = false;
    void onShowSystemTime() {
        if (!ifShowingSystemTime) {
            ifShowingSystemTime = true;

            GregorianCalendar ge = new GregorianCalendar();
            Date dt = ge.getTime();
            String dtText = DateTimeFormater.getTimeString(dt);

            Message message = Message.obtain(_handler, Messages.ShowSystemTime_Msg);
            message.obj = dtText;

            _handler.sendMessage(message);

            ifShowingSystemTime = false;
        }
    }

    private Handler _handler = null;

    boolean ifStopShowSystemTime = false;
    Timer systemTimer = null;

    /**
     * 初始化病开始发送系统时间
     * @param handler
     */
    public void startShowSystemTime(Handler handler) {
        _handler = handler;
        try {
            ifStopShowSystemTime = false;
            systemTimer = new Timer();
            systemTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    SystemTimeShower.this.onShowSystemTime();
                }
            }, 0, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止显示时间
     */
    public void stopShowSystemTime() {
        ifStopShowSystemTime = true;
        if(systemTimer!=null)
        {
            systemTimer.cancel();
        }
    }
}
