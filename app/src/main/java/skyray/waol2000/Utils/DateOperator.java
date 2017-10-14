package skyray.waol2000.Utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/28.
 */

public class DateOperator {

    public static Date addMinute(Date value, int add) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(value);
        calendar.add(Calendar.MINUTE, add);
        return calendar.getTime();
    }

    public static Date addDay(Date value, int add) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(value);
        calendar.add(Calendar.DATE, add);
        return calendar.getTime();
    }

    public static Date getNow() {
        return Calendar.getInstance().getTime();
    }

    public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
        return new Date(year, month, day, hour, minute, second);
    }

    public static double getDateMinuteSub(Date one, Date two) {
        return (one.getTime() - two.getTime()) / (60 * 1000);
    }

    public static double getDateDaySub(Date one, Date two) {
        return (one.getTime() - two.getTime()) / (24 * 60 * 60 * 1000);
    }

    public static double getDateHourSub(Date one, Date two) {
        double between = one.getTime() - two.getTime();
        double day = between / (24 * 60 * 60 * 1000);
        return (between / (60 * 60 * 1000) - day * 24);
//         long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
//         long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
//         long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
//                 - min * 60 * 1000 - s * 1000);
    }
}
