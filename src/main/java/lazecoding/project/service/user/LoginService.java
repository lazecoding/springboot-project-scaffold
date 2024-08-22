package lazecoding.project.service.user;

import lazecoding.project.common.exception.BusException;
import lazecoding.project.common.model.user.LoginParam;
import lazecoding.project.common.model.user.LoginVo;
import lazecoding.project.common.util.security.JWTOperator;
import lazecoding.project.common.util.security.JWTUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    /**
     * 登录
     */
    public LoginVo login(LoginParam loginParam) {
        if (loginParam == null) {
            throw new BusException("登录参数异常");
        }
        String uname = loginParam.getUname();
        String pwd = loginParam.getPwd();
        // TODO 查询用户名和密码校验如果 OK 获取 token
        String uid = "";
        String accessToken = JWTOperator.createAccessToken(uid, uname);
        if (!StringUtils.hasText(accessToken)) {
            throw new BusException("获取 Token 异常，登录失败");
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
        JWTUser jwtUser = JWTOperator.getJwtUserByToken(accessToken);
        if (jwtUser == null) {
            return true;
        }
        // 移除 token
        JWTOperator.dropAccessToken(accessToken);
        logger.debug("用户 {} 登出", jwtUser.getUname());
        return true;
    }


}
