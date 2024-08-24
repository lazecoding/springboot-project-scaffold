package lazecoding.project.interceptor;

import lazecoding.project.common.util.security.JWTOperator;
import org.springframework.util.StringUtils;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取用户 token
        String accessToken = JWTOperator.getAccessToken(request);
        if (!StringUtils.hasText(accessToken)) {
            // TODO 用户未登录，重定向到登录页面
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        boolean verify = JWTOperator.verify(accessToken);
        if (!verify) {
            // TODO 用户 token 非法，重定向到登录页面
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        // TODO 检查 token 是否被注销

        // TODO 通过 token 获取用户


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
