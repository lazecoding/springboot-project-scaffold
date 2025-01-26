package lazecoding.project.common.util.security.advice;

import lazecoding.project.common.mvc.ResultBean;
import lazecoding.project.common.util.security.exception.NotLoginedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * 未登录异常处理
 *
 * @author lazecoding
 */
@Component
@ControllerAdvice
public class NotLoginedAdvice {

    @ExceptionHandler(NotLoginedException.class)
    public ResponseEntity<ResultBean> handleLoginException(Exception e) {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        resultBean.setMessage(e.getMessage());
        return new ResponseEntity<>(resultBean, HttpStatus.UNAUTHORIZED);
    }
}
