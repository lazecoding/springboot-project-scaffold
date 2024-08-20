package lazecoding.project.common.util;

import java.text.DecimalFormat;

/**
 * 单位转换
 *
 * @author lazecoding
 */
public class UnitConversion {

    /**
     * 私有化
     */
    private UnitConversion() {
    }

    /**
     * 字节转其他单位
     *
     * @param size
     * @return
     */
    public static String fileSizeConversion(long size) {
        StringBuffer bytes = new StringBuffer();
        DecimalFormat format = new DecimalFormat("###.00");
        if (size >= 1024L * 1024L * 1024L * 1024L) {
            double i = (size / (1024.0 * 1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("TB");
        } else if (size >= 1024L * 1024L * 1024L) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("GB");
        } else if (size >= 1024L * 1024L) {
            double i = (size / (1024.0 * 1024.0));
            bytes.append(format.format(i)).append("MB");
        } else if (size >= 1024L) {
            double i = (size / (1024.0));
            bytes.append(format.format(i)).append("KB");
        } else if (size < 1024L) {
            bytes.append("0B");
        }
        return bytes.toString();
    }

}
