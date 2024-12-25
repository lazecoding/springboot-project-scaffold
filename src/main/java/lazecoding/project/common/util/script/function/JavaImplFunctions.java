package lazecoding.project.common.util.script.function;

import cn.hutool.core.date.DateUtil;
import lazecoding.project.common.util.time.DateConvertor;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Java 实现的方法
 *
 * @author lazecoding
 */
public class JavaImplFunctions {

    /**
     * 获取当前时间戳
     */
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取常规 UUID
     */
    public String getUuid() {
        return java.util.UUID.randomUUID().toString();
    }

    /**
     * 获取简短 UUID（去 -）
     */
    public String getSimpleUuid() {
        return java.util.UUID.randomUUID().toString();
    }

    /**
     * md5
     */
    public String md5(Object obj) {
        if (obj == null) {
            return "";
        }
        try {
            // 创建MessageDigest实例，指定MD5算法
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 对输入字符串进行MD5散列
            md.update(obj.toString().getBytes());
            // 获取散列值的字节数组
            byte[] digest = md.digest();
            // 将字节数组转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            // 返回MD5散列值的字符串表示
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }

    /**
     * 取交集
     */
    public List<Object> listInter(List<Object> list1, List<Object> list2) {
        if (list1 == null || list2 == null) {
            return new ArrayList<>();
        }
        list1.retainAll(list2);
        return list1;
    }

    /**
     * 取并集
     */
    public List<Object> listUnion(List<Object> list1, List<Object> list2) {
        List<Object> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list1)) {
            list.addAll(list1);
        }
        if (!CollectionUtils.isEmpty(list2)) {
            list.addAll(list2);
        }
        return list;
    }

    /**
     * 生成数组
     */
    public List<Object> initList(Object... obj) {
        if (obj == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(obj);
    }

    /**
     * 获取指定 index 的值
     */
    public Object indexValue(List<Object> list, int index) {
        return list.get(index);
    }

    /**
     * 获取指定 index 的值
     */
    public int listSize(List<Object> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        return list.size();
    }

    /**
     * 文本分割为列表
     */
    public List<Object> split(String text, String separator) {
        if (!StringUtils.hasText(text)) {
            return new ArrayList<>();
        }
        return Arrays.asList(text.split(separator));
    }

    /**
     * 日期转指定格式
     */
    public String dateToString(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 指定格式转日期
     */
    public Date stringToDate(String date, String pattern) {
        return DateUtil.parse(date, pattern);
    }


    /**
     * 获取年份
     */
    public int year(Date date) {
        if (date == null) {
            date = new Date();
        }
        return DateUtil.year(date);
    }

    /**
     * 获取月份
     */
    public int month(Date date) {
        if (date == null) {
            date = new Date();
        }
        return DateUtil.month(date);
    }

    /**
     * 获取年份的第几天
     */
    public int dayOfYear(Date date) {
        if (date == null) {
            date = new Date();
        }
        return DateUtil.dayOfYear(date);
    }

    /**
     * 获取月份的第几天
     */
    public int dayOfMonth(Date date) {
        if (date == null) {
            date = new Date();
        }
        return DateUtil.dayOfMonth(date);
    }

    /**
     * 获取一周的第几天
     */
    public int dayOfWeek(Date date) {
        if (date == null) {
            date = new Date();
        }
        return DateUtil.dayOfWeek(date);
    }



    /**
     * 获取小时
     */
    public int hour(Date date) {
        if (date == null) {
            date = new Date();
        }
        return DateUtil.hour(date, true);
    }

    /**
     * 获取分
     */
    public int minute(Date date) {
        if (date == null) {
            date = new Date();
        }
        return DateUtil.minute(date);
    }


    /**
     * 获取秒
     */
    public int second(Date date) {
        if (date == null) {
            date = new Date();
        }
        return DateUtil.second(date);
    }

    /**
     * 获取毫秒
     */
    public int millisecond(Date date) {
        if (date == null) {
            date = new Date();
        }
        return DateUtil.millisecond(date);
    }



}
