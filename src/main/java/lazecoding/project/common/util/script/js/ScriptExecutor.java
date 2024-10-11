package lazecoding.project.common.util.script.js;

import lazecoding.project.common.util.JsonUtil;
import lazecoding.project.common.util.script.js.function.Functions;
import lazecoding.project.common.util.script.js.function.JavaImplFunctions;
import lazecoding.project.common.util.script.js.function.Languages;
import lazecoding.project.common.util.script.js.model.FunctionHolder;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.springframework.util.StringUtils;

import java.util.Map;


/**
 * 自定义函数定义在 {@link Functions}
 *
 * @author lazecoding
 */
public class ScriptExecutor {


    /**
     * 定义语言为 JavaScript
     */
    private static final String LANGUAGE_ID = "js";

    static {
        // 禁止警告信息：指出你在运行的 JavaScript 代码是通过解释器执行的，而不是编译成本地代码
        System.setProperty("polyglot.engine.WarnInterpreterOnly", "false");
    }

    public static void main(String[] args) {

        FunctionHolder functionHolder = FunctionHolder.getInstance();
        System.out.println(JsonUtil.GSON.toJson(functionHolder));
        String script = " 5 + ABS(-1) + GETUUID() + '  SM3:' + SM3(111111111) +  ' MD5:' + MD5(222222)  ";
        boolean isOk = verify(script);
        System.out.println("isOk = " + isOk);
        String res = eval(script);
        System.out.println("res:" + res);
    }

    /**
     * 无参函数执行
     *
     * @param script 脚本
     */
    public static String eval(String script) {
        if (!StringUtils.hasText(script)) {
            return "";
        }
        return eval(script, null);
    }


    /**
     * 执行
     *
     * @param script 脚本
     * @param params 参数
     */
    public static String eval(String script, Map<String, Object> params) {
        if (!StringUtils.hasText(script)) {
            return "";
        }
        String returnValue;
        try (Context context = Context.newBuilder().allowAllAccess(true).build()) {
            // 定义一个全局函数
            Functions[] functions = Functions.values();
            JavaImplFunctions javaImplFunctions = new JavaImplFunctions();
            Value value = context.asValue(javaImplFunctions);
            Value bindings = context.getBindings(LANGUAGE_ID);
            for (Functions function : functions) {
                String functionName = function.name();
                if (script.contains(functionName)) {
                    if (function.getLanguage().equals(Languages.JS)) {
                        context.eval(LANGUAGE_ID, function.getScript());
                    }
                    if (function.getLanguage().equals(Languages.JAVA)) {
                        bindings.putMember(function.getName(), value.getMember(function.getScript()));
                    }
                }
            }
            // 获取 JavaScript 的命名空间，用于定义变量
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    bindings.putMember(entry.getKey(), entry.getValue());
                }
            }
            // 现在可以在JavaScript中调用 timesTwo 函数
            Value result = context.eval(LANGUAGE_ID, script);
            returnValue = result.toString();
        }
        return returnValue;
    }

    /**
     * 语法检查
     *
     * @param script 脚本
     */
    public static boolean verify(String script) {
        if (!StringUtils.hasText(script)) {
            return true;
        }
        try (Context context = Context.newBuilder(LANGUAGE_ID).build()) {
            // 尝试解析 JavaScript 代码
            Source source = Source.create(LANGUAGE_ID, script);
            context.parse(source);
            return true;
        } catch (PolyglotException e) {
            if (e.isSyntaxError()) {
                System.out.println("Syntax error: " + e.getMessage());
            } else {
                System.out.println("Runtime error: " + e.getMessage());
            }
            return false;
        }
    }

}
