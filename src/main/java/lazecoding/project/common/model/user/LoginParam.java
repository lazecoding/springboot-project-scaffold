package lazecoding.project.common.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 登录参数
 *
 * @author lazecoding
 */
@Schema(description = "用户登录参数")
public class LoginParam implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 登录名
     */
    @NotNull
    @Schema(description = "用户名", example = "admin")
    private String uname;

    /**
     * 密码
     */
    @NotNull
    @Schema(description = "密码", example = "123456")
    private String pwd;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
