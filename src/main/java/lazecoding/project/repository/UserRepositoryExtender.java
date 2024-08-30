package lazecoding.project.repository;

import lazecoding.project.common.entity.User;
import lazecoding.project.common.util.page.PageParam;
import lazecoding.project.common.model.user.UserListParam;
import lazecoding.project.common.util.page.ProcessedPage;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户持久层扩展类
 *
 * @author lazecoding
 */
@Repository
public class UserRepositoryExtender {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 用户列表
     */
    public ProcessedPage<User> list(UserListParam userListParam, PageParam pageParam) {
        String querySql = "SELECT u FROM User u ";
        String whereSql = "";
        Map<String, Object> parameters = new HashMap<>();
        if (userListParam != null) {
            whereSql += " WHERE 1=1 ";
            String uname = userListParam.getUname();
            if (StringUtils.hasText(uname)) {
                whereSql += " AND u.uname = :uname ";
                parameters.put("uname", uname);
            }
            String realName = userListParam.getRealName();
            if (StringUtils.hasText(realName)) {
                whereSql += " AND u.real_name = :realName ";
                parameters.put("realName", realName);
            }
            Integer state = userListParam.getState();
            if (state != null) {
                whereSql += " AND u.state = :state ";
                parameters.put("state", state);
            }
        }
        querySql += whereSql;
        // 查询总数
        String countSql = "SELECT COUNT(1) FROM User u " + whereSql;
        TypedQuery<Long> countQuery = entityManager.createQuery(countSql, Long.class);
        if (!CollectionUtils.isEmpty(parameters)) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                countQuery.setParameter(entry.getKey(), entry.getValue());
            }
        }
        long total = countQuery.getSingleResult();
        // 设置 total 并加工分页参数
        ProcessedPage<User> processedPage = ProcessedPage.process(pageParam, total);
        if (total == 0L) {
            return processedPage;
        }
        // 查询列表
        TypedQuery<User> query = entityManager.createQuery(querySql, User.class);
        if (!CollectionUtils.isEmpty(parameters)) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        query.setFirstResult(processedPage.getOffset());
        query.setMaxResults(processedPage.getSize());
        processedPage.setReturns(query.getResultList());
        return processedPage;
    }
}
