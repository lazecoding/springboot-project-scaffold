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

    /**
     * 过期时间
     */
    private long exp;

    public JWTUser() {
    }

    public JWTUser(String uid, String uname, long exp) {
        this.uid = uid;
        this.uname = uname;
        this.exp = exp;
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

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }
}
