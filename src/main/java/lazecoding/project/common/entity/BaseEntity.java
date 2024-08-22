package lazecoding.project.common.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体基础字段
 *
 * @author lazecoding
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 创建时间
     */
    @Column(name = "create_time", updatable = false)
    @CreatedDate
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @LastModifiedDate
    private Date updateTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
