package lazecoding.project.common.util.script.function;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
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
     * SM3
     */
    public String sm3(Object obj) {
        if (obj == null) {
            return "";
        }
        // TODO SM3(obj.toString().getBytes(StandardCharsets.UTF_8))
        return "SM3";
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
}
