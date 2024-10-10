package lazecoding.project.common.util.script.js.function;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Java 实现的方法
 *
 * @author lazecoding
 */
public class JavaImplFunctions {

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
        // TODO SM3(obj.toString().getBytes(StandardCharsets.UTF_8))
        return "SM3";
    }


}
