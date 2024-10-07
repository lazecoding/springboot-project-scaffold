package lazecoding.project.service.user;

import lazecoding.project.common.constant.CacheConstants;
import lazecoding.project.common.constant.user.DefaultValues;
import lazecoding.project.common.constant.user.UserStates;
import lazecoding.project.common.entity.User;
import lazecoding.project.common.exception.BusException;
import lazecoding.project.common.util.JsonUtil;
import lazecoding.project.common.util.cache.CacheOperator;
import lazecoding.project.common.util.security.constant.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * 注册用户
 *
 * @author lazecoding
 */
@Service
public class RegisterService {

    private static final Logger logger = LoggerFactory.getLogger(RegisterService.class);


    @Autowired
    private UserService userService;

    /**
     * 获取注册验证码
     *
     * @param phone 手机号
     */
    public boolean phoneRegisterSms(String phone) {
        // 为手机号生成验证码并存储到缓存
        String code = "1234";
        boolean isSuccess = CacheOperator.set(CacheConstants.PHONE_REGISTER.getCacheKey(phone), code, 60);
        if (isSuccess) {
            // TODO 发送短信
        } else {
            logger.error("验证码缓存失败 phone:[{}]", phone);
        }
        return isSuccess;
    }

    /**
     * 注册用户
     */
    public boolean phoneRegisterDo(String phone, String code) {
        // 校验参数
        if (!StringUtils.hasText(phone)) {
            throw new BusException("手机号为空");
        }
        if (!StringUtils.hasText(code)) {
            throw new BusException("验证码为空");
        }
        // 通过手机号校验验证码
        String beforeCode = CacheOperator.get(CacheConstants.PHONE_REGISTER.getCacheKey(phone));
        if (!StringUtils.hasText(beforeCode)) {
            throw new BusException("验证码不存在");
        }
        if (!beforeCode.equals(code)) {
            throw new BusException("验证码错误");
        }
        User user = new User();
        user.setUname(UUID.randomUUID().toString());
        user.setPwd(DefaultValues.PASSWORD);
        user.setPhone(phone);
        user.setMail("");
        user.setRealName("手机用户");
        user.setRoles(Role.USER);
        user.setState(UserStates.ACTIVATED);
        User addUser = userService.add(user);
        logger.info("注册用户 id:[{}] info:[{}]", addUser.getUid(), JsonUtil.GSON.toJson(addUser));
        return true;
    }

}
