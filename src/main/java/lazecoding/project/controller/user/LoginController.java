package lazecoding.project.controller.user;

import lazecoding.project.common.exception.BusException;
import lazecoding.project.common.model.user.LoginParam;
import lazecoding.project.common.model.user.LoginVo;
import lazecoding.project.common.mvc.ResultBean;
import lazecoding.project.common.util.security.JWTOperator;
import lazecoding.project.service.user.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 登录
 *
 * @author lazecoding
 */
@RestController
@RequestMapping("sys")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    /**
     * 用户登录
     */
    @PostMapping(value = "login")
    @ResponseBody
    public ResultBean login(LoginParam loginParam, HttpServletResponse httpServletResponse) {
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
     * 用户登出
     */
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



}
