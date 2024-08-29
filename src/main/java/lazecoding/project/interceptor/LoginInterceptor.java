package lazecoding.project.interceptor;

import lazecoding.project.common.model.user.CurrentUser;
import lazecoding.project.common.util.BeanUtil;
import lazecoding.project.service.user.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录拦截器
 *
 * @author lazecoding
 */
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LoginService loginService = BeanUtil.getBean(LoginService.class);
        CurrentUser currentUser = null;
        try {
            currentUser = loginService.currentUser();
        } catch (Exception e) {
            logger.error("loginService.currentUser Exception", e);
        }
        if (currentUser == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 可以在这里添加请求处理后的逻辑
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求完成后的清理工作
    }
}
