package lazecoding.project.common.model.user;

import lazecoding.project.common.model.BaseVo;

/**
 * 登录返回值
 *
 * @author lazecoding
 */
public class LoginVo extends BaseVo {


    /**
     * 用户名
     */
    private String uname;

    /**
     * 登录 token
     */
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
