package lazecoding.project.service.user;

import lazecoding.project.common.constant.CacheConstants;
import lazecoding.project.common.constant.user.UserStates;
import lazecoding.project.common.entity.User;
import lazecoding.project.common.exception.BusException;
import lazecoding.project.common.model.user.CurrentUser;
import lazecoding.project.common.model.user.LoginParam;
import lazecoding.project.common.model.user.LoginVo;
import lazecoding.project.common.util.cache.CacheOperator;
import lazecoding.project.common.util.security.JWTOperator;
import lazecoding.project.common.util.security.JWTUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 用户登录
 *
 * @author lazecoding
 */
@Service
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);


    @Autowired
    private UserService userService;

    /**
     * 登录
     */
    public LoginVo login(LoginParam loginParam) {
        if (loginParam == null) {
            throw new BusException("登录参数异常");
        }
        String uname = loginParam.getUname();
        String pwd = loginParam.getPwd();
        if (!StringUtils.hasText(uname)) {
            throw new BusException("用户名不得为空");
        }
        if (!StringUtils.hasText(pwd)) {
            throw new BusException("密码不得为空");
        }
        User user = userService.findByUname(uname);
        if (user == null) {
            throw new BusException("用户不存在");
        }
        if (!pwd.equals(user.getPwd())) {
            throw new BusException("密码错误");
        }
        // 登录校验用户状态
        int state = user.getState();
        if (state != UserStates.ACTIVATED) {
            if (state == UserStates.UNACTIVATED) {
                throw new BusException("用户未激活");
            }
            if (state == UserStates.FREEZING) {
                throw new BusException("用户已冻结");
            }
            if (state == UserStates.CANCELLED) {
                throw new BusException("用户已注销");
            }
            // 如果不合规状态，默认 用户未激活
            throw new BusException("用户未激活");
        }
        String uid = user.getUid();
        JWTUser jwtUser = new JWTUser(uid, uname);
        String accessToken = JWTOperator.createAccessToken(jwtUser);
        if (!StringUtils.hasText(accessToken)) {
            throw new BusException("获取 token 异常，登录失败");
        }
        CurrentUser currentUser = new CurrentUser();
        currentUser.setUid(uid);
        currentUser.setUname(uname);
        currentUser.setRoles(user.getRoleSet());
        currentUser.setAccessToken(accessToken);
        currentUser.setExp(jwtUser.getExp());
        // 获取 token 成功，将 token 持久化到缓存表示 token 有效，并设置有效期
        boolean isSuccess = CacheOperator.set(CacheConstants.CURRENT_USER.getCacheKey(accessToken), currentUser,
                CacheConstants.CURRENT_USER.getTtl(), CacheConstants.CURRENT_USER.getTimeUnit());
        if (!isSuccess) {
            throw new BusException("token 存储异常，登录失败");
        }
        LoginVo loginVo = new LoginVo(uname, accessToken);
        logger.debug("用户 {} 登录成功", uname);
        return loginVo;
    }

    /**
     * 登出
     */
    public boolean logout() {
        String accessToken = JWTOperator.getAccessToken();
        if (!StringUtils.hasText(accessToken)) {
            return true;
        }
        // 移除 token
        CacheOperator.delete(CacheConstants.CURRENT_USER.getCacheKey(accessToken));
        JWTUser jwtUser = JWTOperator.getJwtUserByToken(accessToken);
        if (jwtUser == null) {
            return true;
        }
        logger.debug("用户 {} 登出", jwtUser.getUname());
        return true;
    }

    /**
     * 获取当前用户
     **/
    public CurrentUser currentUser() {
        String accessToken = JWTOperator.getAccessToken();
        if (!StringUtils.hasText(accessToken)) {
            return null;
        }
        JWTUser jwtUser = JWTOperator.getJwtUserByToken(accessToken);
        if (jwtUser == null) {
            return null;
        }
        String uid = jwtUser.getUid();
        if (!StringUtils.hasText(uid)) {
            return null;
        }
        CurrentUser currentUser = CacheOperator.get(CacheConstants.CURRENT_USER.getCacheKey(accessToken));
        if (currentUser == null) {
            throw new BusException("请重新登录");
        }
        // 如果需要用户 token 实时生效，需要从 User 中读取权限，不建议
        return currentUser;
    }

}
