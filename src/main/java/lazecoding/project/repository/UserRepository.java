package lazecoding.project.repository;

import lazecoding.project.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 用户
 *
 * @author lazecoding
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * 根据用户名查询用户
     *
     * @param uname 用户名
     */
    @Query("SELECT u FROM User u WHERE u.uname = ?1 ")
    User findByName(String uname);

    /**
     * 根据状态查询用户列表
     *
     * @param state 账号状态
     */
    @Query("SELECT u FROM User u WHERE u.state = ?1 ")
    List<User> findByState(int state);

    /**
     * 按时间分页查询
     *
     * @param date     时间
     * @param pageable 分页属性
     */
    @Query("SELECT u FROM User u WHERE u.createTime >= ?1 ")
    Page<User> findByCreateTime(Date date, Pageable pageable);

}
