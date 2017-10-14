package skyray.waol2000.Utils;


import java.math.BigDecimal;

/**
 * 数字小数位数处理
 */

public class NumberPointManage {

    /**
     * 将数字设置为小数位数
     *
     * @param d      数字
     * @param points 小数位数
     * @return
     */
    public static String toString(double d, int points) {
        BigDecimal big = new BigDecimal(d);
        return String.valueOf(big.setScale(points, BigDecimal.ROUND_CEILING));
    }

}
