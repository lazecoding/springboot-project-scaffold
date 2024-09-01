package lazecoding.project.common.util.security.aop;

import lazecoding.project.common.model.user.CurrentUser;
import lazecoding.project.common.mvc.ResultBean;
import lazecoding.project.common.util.security.annotation.RequireRoles;
import lazecoding.project.common.util.security.constant.Role;
import lazecoding.project.common.util.security.exception.NoPermissionsException;
import lazecoding.project.service.user.LoginService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

/**
 * RequireRoles 注解处理类
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
    // @Before("classHandler()")
    public void classBeforeAdvice(JoinPoint joinPoint) {
        Class<?> signatureClass = joinPoint.getSignature().getDeclaringType();
        RequireRoles requireRoles = (RequireRoles) signatureClass.getAnnotation(RequireRoles.class);
        // 设置角色才需要校验用户角色
        verify(requireRoles);
    }


    // @Before("methodHandler()")
    public void methodBeforeAdvice(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequireRoles requireRoles = (RequireRoles) method.getAnnotation(RequireRoles.class);
        // 设置角色才需要校验用户角色
        verify(requireRoles);
    }

    @Around("classHandler()")
    public Object classAroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Around class classHandler.");
        try {
            Class<?> signatureClass = joinPoint.getSignature().getDeclaringType();
            RequireRoles requireRoles = (RequireRoles) signatureClass.getAnnotation(RequireRoles.class);
            // 校验
            verify(requireRoles);
            // 执行方法
            return joinPoint.proceed();
        } catch (NoPermissionsException e) {
            // 如果没有权限，返回401状态码
            HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
            assert response != null;
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            // 返回一个带有401状态码的响应实体
            return new ResultBean(String.valueOf(HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }
    }

    @Around("methodHandler()")
    public Object methodAroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Around method methodHandler.");
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            RequireRoles requireRoles = (RequireRoles) method.getAnnotation(RequireRoles.class);
            // 设置角色才需要校验用户角色
            verify(requireRoles);
            // 执行方法
            return joinPoint.proceed();
        } catch (NoPermissionsException e) {
            // 如果没有权限，返回401状态码
            HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
            assert response != null;
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            // 返回一个带有401状态码的响应实体
            return new ResultBean(String.valueOf(HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }
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
                // 如果是超级管理员，直接通过
                if (roles.contains(Role.SUPER)) {
                    return;
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
