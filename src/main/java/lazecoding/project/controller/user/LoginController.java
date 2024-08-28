package lazecoding.project.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lazecoding.project.common.exception.BusException;
import lazecoding.project.common.model.user.CurrentUser;
import lazecoding.project.common.model.user.LoginParam;
import lazecoding.project.common.model.user.LoginVo;
import lazecoding.project.common.mvc.ResultBean;
import lazecoding.project.common.util.security.JWTOperator;
import lazecoding.project.service.user.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 登录
 *
 * @author lazecoding
 */
@Tag(name = "登录", description = "用户登入登出")
@RestController
@RequestMapping("sys")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    /**
     * 用户登录 http://localhost:9977/sys/login?uname=root&pwd=pwd
     */
    @Operation(summary = "登陆", description = "用户登录")
    @ApiResponse(
            responseCode = "200", description = "成功",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResultBean.class))}
    )
    @PostMapping(value = "login")
    @ResponseBody
    public ResultBean login(@ParameterObject LoginParam loginParam, HttpServletResponse httpServletResponse) {
        ResultBean resultBean = ResultBean.getInstance();
        String message = "";
        boolean isSuccess = false;
        try {
            LoginVo loginVo = loginService.login(loginParam);
            if (loginVo != null) {
                isSuccess = true;
                httpServletResponse.addCookie(JWTOperator.getLoginCookie(loginVo.getAccessToken()));
            }
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

    /**
     * 用户登出 http://localhost:9977/sys/logout
     */
    @Operation(summary = "登出", description = "用户登出")
    @PostMapping(value = "logout")
    @ResponseBody
    public ResultBean logout(HttpServletResponse httpServletResponse) {
        ResultBean resultBean = ResultBean.getInstance();
        String message = "";
        boolean isSuccess = false;
        try {
            isSuccess = loginService.logout();
            if (isSuccess) {
                httpServletResponse.addCookie(JWTOperator.getLogoutCookie());
            }
        } catch (BusException e) {
            logger.error("登出异常", e);
            message = e.getMessage();
        } catch (Exception e) {
            logger.error("登出异常", e);
            message = "系统异常";
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }

    /**
     * 获取当前用户 http://localhost:9977/sys/current
     */
    @Operation(summary = "获取当前用户", description = "获取当前用户")
    @GetMapping(value = "current")
    @ResponseBody
    public ResultBean current() {
        ResultBean resultBean = ResultBean.getInstance();
        String message = "";
        boolean isSuccess = false;
        try {
            CurrentUser currentUser = loginService.currentUser();
            resultBean.addData("currentUser", currentUser);
            isSuccess = true;
        } catch (BusException e) {
            logger.error("获取当前用户异常", e);
            message = e.getMessage();
        } catch (Exception e) {
            logger.error("获取当前用户异常", e);
            message = "系统异常";
        }
        resultBean.setSuccess(isSuccess);
        resultBean.setMessage(message);
        return resultBean;
    }


}
