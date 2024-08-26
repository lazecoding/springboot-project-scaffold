package lazecoding.project.service.user;

import lazecoding.project.common.entity.User;
import lazecoding.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * 用户业务
 *
 * @author lazecoding
 */
@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    /**
     * 通过用户 Id 获取用户
     *
     * @param uid 用户 Id
     */
    public User findByUid(String uid) {
        if (!StringUtils.hasText(uid)) {
            return null;
        }
        Optional<User> userOptional = userRepository.findById(uid);
        return userOptional.orElse(null);
    }

    /**
     * 通过用户名获取用户
     *
     * @param uname 用户名
     */
    public User findByUname(String uname) {
        if (!StringUtils.hasText(uname)) {
            return null;
        }
        return userRepository.findByName(uname);
    }

}
