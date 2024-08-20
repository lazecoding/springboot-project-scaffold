package lazecoding.project.common.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Gson 常量实例
 *
 * @author lazecoding
 */
public class JsonUtil {

    /**
     * Gson 对象
     */
    public static final Gson GSON = new GsonBuilder()
            .setLenient() // json 宽松
            .enableComplexMapKeySerialization() // 支持 Map 的 key 为复杂对象的形式
            .serializeNulls() // 智能 null
            .setPrettyPrinting()// 调教格式
            .disableHtmlEscaping() // 默认是 GSON 把 HTML 转义的
            .create();

    /**
     * 私有
     */
    private JsonUtil() {
    }
}
