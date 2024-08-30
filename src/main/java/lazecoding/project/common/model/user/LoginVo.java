package lazecoding.project.common.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lazecoding.project.common.model.BaseVo;

/**
 * 登录返回值
 *
 * @author lazecoding
 */
@Schema(description = "登录返回")
public class LoginVo extends BaseVo {


    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "admin")
    private String uname;

    /**
     * 登录 token
     */
    @Schema(description = "授权 token", example = "XXX")
    private String accessToken;


    public LoginVo() {
    }
    public LoginVo(String uname, String accessToken) {
        this.uname = uname;
        this.accessToken = accessToken;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
