package lazecoding.project.common.util.at;

import java.io.Serializable;
import java.util.Set;

/**
 * 解析结果
 *
 * @author lazecoding
 */
public class ParserResult implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 是否成功解析
     */
    private boolean success;

    /**
     * 原文
     */
    private String content;

    /**
     * 解析后文本
     */
    private String escapeContent;

    /**
     * 解析的 Id 集合
     */
    private Set<String> ids;

    public ParserResult() {
    }

    public ParserResult(String content) {
        this.content = content;
    }

    public ParserResult(String content, String escapeContent, Set<String> ids) {
        this.content = content;
        this.escapeContent = escapeContent;
        this.ids = ids;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEscapeContent() {
        return escapeContent;
    }

    public void setEscapeContent(String escapeContent) {
        this.escapeContent = escapeContent;
    }

    public Set<String> getIds() {
        return ids;
    }

    public void setIds(Set<String> ids) {
        this.ids = ids;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
