package lazecoding.project.common.util.page;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 分页参数
 *
 * @author lazecoding
 */
@Schema(description = "分页参数")
public class PageParam {


    /**
     * 页码
     */
    @Schema(description = "页码", example = "1")
    private Integer page;

    /**
     * 数量
     */
    @Schema(description = "数量", example = "15")
    private Integer size;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
