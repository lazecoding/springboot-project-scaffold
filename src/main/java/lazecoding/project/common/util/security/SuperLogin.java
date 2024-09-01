package lazecoding.project.common.util.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lazecoding.project.common.constant.CacheConstants;
import lazecoding.project.common.exception.BusException;
import lazecoding.project.common.exception.NilParamException;
import lazecoding.project.common.model.user.CurrentUser;
import lazecoding.project.common.model.user.LoginVo;
import lazecoding.project.common.mvc.ResultBean;
import lazecoding.project.common.util.cache.CacheOperator;
import lazecoding.project.common.util.security.constant.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * 超级管理员登录
 *
 * @author lazecoding
 */
@Tag(name = "超级管理员登录", description = "超级管理员登入登出")
@RestController
@RequestMapping("super")
public class SuperLogin {

    private static final Logger logger = LoggerFactory.getLogger(SuperLogin.class);


    @Operation(summary = "登陆", description = "用户登录")
    @Parameters({
            @Parameter(name = "uname", description = "登录名", example = "super", required = true),
            @Parameter(name = "pwd", description = "密码", example = "super", required = true)
    })
    @ApiResponse(
            responseCode = "200", description = "成功",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResultBean.class))}
    )
    @PostMapping(value = "login")
    @ResponseBody
    public ResultBean login(String uname, String pwd, HttpServletResponse httpServletResponse) {
        ResultBean resultBean = ResultBean.getInstance();
        String message = "";
        boolean isSuccess = false;
        try {
            if (!StringUtils.hasText(uname) || !StringUtils.hasText(pwd)) {
                throw new NilParamException("登录参数为空");
            }
            String superName = "super";
            String superPwd = "super";
            if (!uname.equals(superName) || !pwd.equals(superPwd)) {
                throw new NilParamException("账号错误");
            }
            String uid = "super";
            JWTUser jwtUser = new JWTUser(uid, uname);
            String accessToken = JWTOperator.createAccessToken(jwtUser);
            if (!StringUtils.hasText(accessToken)) {
                throw new BusException("获取 token 异常，登录失败");
            }
            Set<String> roleSet = new HashSet<>();
            roleSet.add(Role.SUPER);
            CurrentUser currentUser = new CurrentUser();
            currentUser.setUid(uid);
            currentUser.setUname(uname);
            currentUser.setRoles(roleSet);
            currentUser.setAccessToken(accessToken);
            currentUser.setExp(jwtUser.getExp());
            // 获取 token 成功，将 token 持久化到缓存表示 token 有效，并设置有效期
            isSuccess = CacheOperator.set(CacheConstants.CURRENT_USER.getCacheKey(accessToken), currentUser,
                    CacheConstants.CURRENT_USER.getTtl(), CacheConstants.CURRENT_USER.getTimeUnit());
            if (!isSuccess) {
                throw new BusException("token 存储异常，登录失败");
            }
            httpServletResponse.addCookie(JWTOperator.getLoginCookie(accessToken));
            resultBean.addData("currentUser", currentUser);
        } catch (BusException e) {
            logger.error("登录异常", e);
            message = e.getMessage();
        } catch (Exception e) {
            logger.error("登录异常", e);
            message = "系统异常";
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }


}
