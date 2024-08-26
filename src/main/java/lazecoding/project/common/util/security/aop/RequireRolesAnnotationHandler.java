package lazecoding.project.common.util.security.aop;

import lazecoding.project.common.model.user.CurrentUser;
import lazecoding.project.common.util.security.JWTOperator;
import lazecoding.project.common.util.security.annotation.RequireRoles;
import lazecoding.project.common.util.security.exception.NoPermissionsException;
import lazecoding.project.service.user.LoginService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO RequireRoles 注解处理类
 *
 * @author lazecoding
 */
@Aspect
@Component
public class RequireRolesAnnotationHandler {

    @Autowired
    private LoginService loginService;


    // 切入点表达式，匹配类上带有 RequireRoles 注解的方法
    @Pointcut("(@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController))" +
            "&& @within(lazecoding.project.common.util.security.annotation.RequireRoles)")
    public void classHandler() {
    }

    // 切入点表达式，匹配方法上带有 RequireRoles 注解的方法
    @Pointcut("(@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController))" +
            "&& @annotation(lazecoding.project.common.util.security.annotation.RequireRoles)")
    public void methodHandler() {
    }


    // 前置通知
    @Before("classHandler()")
    public void classBeforeAdvice(JoinPoint joinPoint) {
        System.out.println("Before class classHandler.");
        Class<?> signatureClass = joinPoint.getSignature().getDeclaringType();
        RequireRoles requireRoles = (RequireRoles) signatureClass.getAnnotation(RequireRoles.class);
        // 设置角色才需要校验用户角色
        verify(requireRoles);
    }


    @Before("methodHandler()")
    public void methodBeforeAdvice(JoinPoint joinPoint) {
        System.out.println("Before method methodHandler.");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        System.out.println("method name:" + method.getName());
        RequireRoles requireRoles = (RequireRoles) method.getAnnotation(RequireRoles.class);
        // 设置角色才需要校验用户角色
        verify(requireRoles);
    }

    /**
     * 鉴权
     */
    private void verify(RequireRoles requireRoles) {
        if (requireRoles != null) {
            System.out.println(Arrays.toString(requireRoles.value()));
            String[] requireRoleArray = requireRoles.value();
            if (requireRoleArray != null && requireRoleArray.length > 0) {
                CurrentUser currentUser = loginService.currentUser();
                if (currentUser == null) {
                    throw new NoPermissionsException();
                }
                Set<String> roles = currentUser.getRoles();
                if (roles == null || roles.isEmpty()) {
                    throw new NoPermissionsException();
                }
                // 判断用户是否拥有设置的权限
                for (String role : roles) {
                    if (role != null && !role.isEmpty()) {
                        for (String requireRole : requireRoleArray) {
                            if (requireRole != null && !requireRole.isEmpty()) {
                                if (role.equals(requireRole)) {
                                    return;
                                }
                            }
                        }
                    }
                }
                // 行至此处，未匹配到角色，则无权限
                throw new NoPermissionsException();
            }
        }
    }

}
