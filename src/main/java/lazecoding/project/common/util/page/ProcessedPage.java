package lazecoding.project.common.util.page;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * 加工后的分页属性
 *
 * @author lazecoding
 */
@Schema(description = "加工后的分页属性")
public class ProcessedPage<T> {


    /**
     * 页码
     */
    @Schema(description = "页码", example = "1")
    private int page;

    /**
     * 数量
     */
    @Schema(description = "数量", example = "15")
    private int size;

    /**
     * 偏移量
     */
    @Schema(description = "偏移量", example = "0")
    private int offset;


    /**
     * 总数
     */
    @Schema(description = "总数", example = "0")
    private int total;

    /**
     * 返回值
     */
    @Schema(description = "返回值")
    private List<T> returns;


    public ProcessedPage() {
        this.page = 1;
        this.size = 15;
        this.offset = 0;
    }

    public ProcessedPage(int page, int size) {
        this.page = page;
        this.size = size;
        this.offset = (page - 1) * size;
    }

    /**
     * 加工分页参数
     */
    public static <T> ProcessedPage<T> process(PageParam pageParam) {
        if (pageParam == null) {
            return new ProcessedPage<>(1, 15);
        }
        Integer page = pageParam.getPage();
        Integer size = pageParam.getSize();
        if (page == null || page < 1) {
            page = 1;
        }
        if (size == null || size < 1) {
            size = 15;
        }
        return new ProcessedPage<>(page, size);
    }


    /**
     * 加工分页参数
     */
    public static <T> ProcessedPage<T> process(PageParam pageParam, long total) {
        int intValue = (total > Integer.MAX_VALUE) ? Integer.MAX_VALUE : (int) total;
        return process(pageParam, intValue);
    }


    /**
     * 加工分页参数
     */
    public static <T> ProcessedPage<T> process(PageParam pageParam, int total) {
        ProcessedPage<T> processedPage = process(pageParam);
        // 如果没数据
        if (total < 1) {
            processedPage.setPage(1);
            processedPage.setOffset(0);
            processedPage.setTotal(0);
            return processedPage;
        } else {
            // 有数据则计算最大页数，如果 page 超过最大页数则赋予最大页数
            processedPage.setTotal(total);
            int maxPage = (int) Math.ceil((double) total / processedPage.getSize());;
            if (processedPage.getPage() > maxPage) {
                processedPage.setPage(maxPage);
                int offset = (maxPage - 1) * processedPage.getSize();
                processedPage.setOffset(offset);
            }
            return processedPage;
        }
    }


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

    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotal() {
        return total;
    }

    /**
     * 只修改 total，不校验其他属性
     */
    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getReturns() {
        return returns;
    }

    public void setReturns(List<T> returns) {
        this.returns = returns;
    }

}
