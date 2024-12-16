package lazecoding.project.common.util.at;


import lazecoding.project.common.util.JsonUtil;
import lazecoding.project.service.user.RegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析 @_{"id":"Id值","name":"名称"}_@
 *
 * @author lazecoding
 */
public class Parser {


    private static final Logger logger = LoggerFactory.getLogger(Parser.class);


    /**
     * @ 开始标签
     */
    public static final String AT_START = "@_";

    /**
     * @ 结束标签
     */
    public static final String AT_END = "_@";

    /**
     * @ 正则 : @_.*?_@
     */
    public static final String AT_REGEX = AT_START + ".*?" + AT_END;


    /**
     * @ 正则
     */
    public static final Pattern AT_PATTERN = Pattern.compile(AT_REGEX);

    private Parser() {
    }

    public static void main(String[] args) {
        String content = "快点回复我 @_{\"id\":\"admin\",\"name\":\"管理员\"}_@";
        ParserResult parserResult = parser(content);
        if (parserResult.isSuccess()) {
            String escapeContent = parserResult.getEscapeContent();
            Set<String> ids = parserResult.getIds();
        }
        System.out.println(JsonUtil.GSON.toJson(parserResult));
    }

    /**
     * 解析
     *
     * @param content 原文
     * @return ParserResult 解析结果
     */
    @NotNull
    public static ParserResult parser(String content) {
        ParserResult parserResult = new ParserResult(content);
        if (!StringUtils.hasText(content)) {
            parserResult.setSuccess(true);
            parserResult.setEscapeContent(content);
            return parserResult;
        }

        String escapeContent = content;
        Set<String> ids = new HashSet<>();
        boolean isSuccess = false;
        try {
            Matcher matcher = AT_PATTERN.matcher(escapeContent);
            while (matcher.find()) {
                // 获取匹配到的标签数据
                String matcherStr = matcher.group(0).trim();
                // 获取标签内用户的数据
                String item = matcherStr.replace("@_", "").replace("_@", "");
                At at = JsonUtil.GSON.fromJson(item, At.class);
                if (at != null) {
                    String id = at.getId();
                    String name = at.getName();
                    if (StringUtils.hasText(id) && StringUtils.hasText(name)) {
                        ids.add(id);
                        // 替换处理后的数据，用于显示
                        escapeContent = escapeContent.replace(matcherStr, "@" + name);
                    }
                }
            }
            isSuccess = true;
        } catch (Exception e) {
            logger.error("解析 @ 标签异常", e);
        }
        parserResult.setSuccess(isSuccess);
        parserResult.setEscapeContent(escapeContent);
        parserResult.setIds(ids);
        return parserResult;
    }

}
