package lazecoding.project.common.model.user;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户列表参数
 *
 * @author lazecoding
 */
@Schema(description = "用户列表参数")
public class UserListParam implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String uname;

    /**
     * 真实姓名
     */
    @Schema(description = "真实姓名")
    private String realName;


    @Schema(description = "用户状态")
    private Integer state;


    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
