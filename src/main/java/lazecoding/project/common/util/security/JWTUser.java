package lazecoding.project.common.util.security;


import java.io.Serializable;

/**
 *
 * JWT 用户
 *
 * @author lazecoding
 */
public class JWTUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户 Id
     */
    private String uid;

    /**
     * 用户名
     */
    private String uname;


    public JWTUser() {
    }

    public JWTUser(String uid, String uname) {
        this.uid = uid;
        this.uname = uname;
    }

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

}
