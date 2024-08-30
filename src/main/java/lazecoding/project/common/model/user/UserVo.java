package lazecoding.project.common.model.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lazecoding.project.common.model.BaseVo;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * 用户
 *
 * @author lazecoding
 */
@Schema(description = "用户")
public class UserVo extends BaseVo {

    /**
     * 用户 Id
     */
    @Schema(description = "用户 Id", example = "uid")
    private String uid;

    /**
     * 用户名
     */
    @NotBlank
    @Schema(description = "用户名", example = "admin")
    private String uname;

    /**
     * 真实姓名
     */
    @Schema(description = "真实姓名", example = "张三")
    private String realName;

    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "11122223333")
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱", example = "lazy@mail.com")
    private String mail;

    /**
     * 用户状态
     */
    @Schema(description = "用户状态", example = "0")
    private int state;

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

    public @NotBlank String getUname() {
        return uname;
    }

    public void setUname(@NotBlank String uname) {
        this.uname = uname;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
