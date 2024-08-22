package lazecoding.project.common.model.user;

import java.io.Serializable;

/**
 * 登录参数
 *
 * @author lazecoding
 */
public class LoginParam implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 登录名
     */
    private String uname;

    /**
     * 密码
     */
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
