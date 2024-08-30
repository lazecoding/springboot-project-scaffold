package lazecoding.project.common.model.user;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Set;

/**
 * CurrentUser
 *
 * @author lazecoding
 */
@Schema(description = "当前登录用户")
public class CurrentUser implements Serializable {


    private static final long serialVersionUID = -1L;

    /**
     * 用户 Id
     */
    @Schema(description = "用户 Id", example = "uid")
    private String uid;

    /**
     * 用户名称
     */
    @Schema(description = "用户名", example = "admin")
    private String uname;

    /**
     * 用户 token
     */
    @Schema(description = "授权 token", example = "XXX")
    private String accessToken;

    /**
     * 用户角色
     */
    @Schema(description = "用户角色")
    private Set<String> roles;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
