package lazecoding.project.service.user;

import lazecoding.project.common.entity.User;
import lazecoding.project.common.exception.BusException;
import lazecoding.project.common.util.page.PageParam;
import lazecoding.project.common.model.user.UserAddParam;
import lazecoding.project.common.model.user.UserListParam;
import lazecoding.project.common.model.user.UserVo;
import lazecoding.project.common.util.JsonUtil;
import lazecoding.project.common.util.page.ProcessedPage;
import lazecoding.project.common.util.validator.ValidatorUtil;
import lazecoding.project.repository.UserRepository;
import lazecoding.project.repository.UserRepositoryExtender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 用户业务
 *
 * @author lazecoding
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepositoryExtender userRepositoryExtender;

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

    /**
     * 新增用户
     */
    public boolean add(UserAddParam userAddParam) {
        if (userAddParam == null) {
            return false;
        }
        boolean violations = ValidatorUtil.validate(userAddParam);
        if (!violations) {
            throw new BusException("请求参数校验未通过");
        }
        // 先检查用户名是否重复
        String uname = userAddParam.getUname();
        User existUser = userRepository.findByName(uname);
        if (existUser != null) {
            throw new BusException("该用户名已存在");
        }
        User user = new User();
        user.setUname(uname);
        user.setPwd(userAddParam.getPwd());
        user.setPhone(userAddParam.getPhone());
        user.setMail(userAddParam.getMail());
        user.setRoles(userAddParam.getRoles());
        User addUser = userRepository.save(user);
        String uid = addUser.getUid();
        logger.info("新增用户 id:[{}] info:[{}]", uid, JsonUtil.GSON.toJson(addUser));
        return true;
    }

    /**
     * 新增用户
     */
    public User add(User user) {
        if (user == null) {
            return null;
        }
        return userRepository.save(user);
    }


    /**
     * 用户列表
     */
    public ProcessedPage<UserVo> list(UserListParam userListParam, PageParam pageParam) {
        ProcessedPage<User> processedPage = userRepositoryExtender.list(userListParam, pageParam);
        if (processedPage == null) {
            return new ProcessedPage<>();
        }
        // 组织 vo
        List<User> users = processedPage.getReturns();
        List<UserVo> userVos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(users)) {
            for (User user : users) {
                UserVo userVo = new UserVo();
                userVo.setUid(user.getUid());
                userVo.setUname(user.getUname());
                userVo.setRealName(user.getRealName());
                userVo.setPhone(user.getPhone());
                userVo.setMail(user.getMail());
                userVo.setState(user.getState());
                userVo.setRoles(user.getRoleSet());
                userVos.add(userVo);
            }
        }
        // 组织返回值
        ProcessedPage<UserVo> voProcessedPage = new ProcessedPage<>();
        voProcessedPage.setPage(processedPage.getPage());
        voProcessedPage.setSize(processedPage.getSize());
        voProcessedPage.setOffset(processedPage.getOffset());
        voProcessedPage.setTotal(processedPage.getTotal());
        voProcessedPage.setReturns(userVos);
        return voProcessedPage;
    }

}
