package lazecoding.project.common.entity;

import lazecoding.project.common.constant.Tables;
import lazecoding.project.repository.base.CustomUUIDGenerator;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户实体
 *
 * @author lazecoding
 */
@Entity
@Table(name = Tables.USER,
        indexes = {
                @Index(columnList = "phone"),
                @Index(columnList = "state"),
                @Index(columnList = "create_time")
        })
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseEntity {

    /**
     * 用户 Id
     */
    @Id
    @GeneratedValue(generator = CustomUUIDGenerator.GENERATOR_NAME)
    @GenericGenerator(name = CustomUUIDGenerator.GENERATOR_NAME, strategy = CustomUUIDGenerator.CLASS_PATH)
    @Column(name = "uid", unique = true, length = 36, nullable = false, columnDefinition = "varchar(36)")
    private String uid;

    /**
     * 用户名
     */
    @Column(name = "uname", unique = true, length = 50, nullable = false, columnDefinition = "varchar(50)")
    private String uname;

    /**
     * 密码
     */
    @Column(name = "pwd", length = 1000, nullable = false, columnDefinition = "varchar(1000)")
    private String pwd;

    /**
     * 真实姓名
     */
    @Column(name = "real_name", length = 50, columnDefinition = "varchar(50)")
    private String realName;

    /**
     * 手机号
     */
    @Column(name = "phone", length = 50, columnDefinition = "varchar(50) default ''")
    private String phone;

    /**
     * 邮箱
     */
    @Column(name = "mail", length = 50, columnDefinition = "varchar(50) default ''")
    private String mail;


    /**
     * 用户角色
     */
    @Column(name = "roles", length = 1024, columnDefinition = "varchar(1024) default ''")
    private String roles;


    /**
     * 用户状态 0 未激活；1 已激活；2 已冻结；3.已注销
     */
    @Column(name = "state", nullable = false, columnDefinition = "int(11) default 0")
    private int state;

    public User() {
    }

    public User(String uid) {
        this.uid = uid;
    }

    public User(String uname, String pwd) {
        this.uname = uname;
        this.pwd = pwd;
    }

    public User(String uid, String pwd, String uname) {
        this.uid = uid;
        this.pwd = pwd;
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
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

    public String getRoles() {
        return roles;
    }

    /**
     * 获取用户权限集合
     */
    public Set<String> getRoleSet() {
        if (roles == null || roles.isEmpty()) {
            return new HashSet<>();
        }
        String[] roleArray = roles.split(",");
        if (roleArray.length == 0) {
            return new HashSet<>();
        }
        Set<String> roleSet = new HashSet<>();
        for (String role : roleArray) {
            if (role != null && !role.isEmpty()) {
                roleSet.add(role);
            }
        }
        return roleSet;
    }


    public void setRoles(String roles) {
        this.roles = roles;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
