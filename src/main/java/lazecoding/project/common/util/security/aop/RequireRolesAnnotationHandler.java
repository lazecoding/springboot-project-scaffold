package lazecoding.project.common.util.security.aop;

import lazecoding.project.common.util.security.JWTOperator;
import lazecoding.project.common.util.security.annotation.RequireRoles;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * TODO RequireRoles 注解处理类
 *
 * @author lazecoding
 */
@Aspect
@Component
public class RequireRolesAnnotationHandler {


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
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        String accessToken = JWTOperator.getAccessToken(request);
        System.out.println("accessToken: " + accessToken);
        Class<?> signatureClass = joinPoint.getSignature().getDeclaringType();
        System.out.println("class name:" + signatureClass.getName());
        RequireRoles requireRoles = (RequireRoles) signatureClass.getAnnotation(RequireRoles.class);
        if (requireRoles != null) {
            System.out.println(Arrays.toString(requireRoles.value()));
        }
    }


    @Before("methodHandler()")
    public void methodBeforeAdvice(JoinPoint joinPoint) {
        System.out.println("Before method methodHandler.");
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        String accessToken = JWTOperator.getAccessToken(request);
        System.out.println("accessToken: " + accessToken);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        System.out.println("method name:" + method.getName());
        RequireRoles requireRoles = (RequireRoles) method.getAnnotation(RequireRoles.class);
        if (requireRoles != null) {
            System.out.println(Arrays.toString(requireRoles.value()));
        }
    }


}
