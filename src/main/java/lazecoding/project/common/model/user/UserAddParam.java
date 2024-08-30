package lazecoding.project.common.model.user;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户新增参数
 *
 * @author lazecoding
 */
@Schema(description = "用户新增参数")
public class UserAddParam implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 用户名
     */
    @NotBlank
    @Schema(description = "用户名", example = "admin")
    private String uname;

    /**
     * 密码
     */
    @NotBlank
    @Schema(description = "密码", example = "123456")
    private String pwd;

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
     * 用户角色
     */
    @Schema(description = "角色", example = "admin,info")
    private String roles;

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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
