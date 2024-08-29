package lazecoding.project.common.util.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * 属性校验
 *
 * @author lazecoding
 */
public class ValidatorUtil {


    private static final Logger logger = LoggerFactory.getLogger(ValidatorUtil.class);

    /**
     * 校验器
     */
    public static Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * 检验是否通过
     */
    public static <T> boolean validate(T t) {
        if (t == null) {
            return false;
        }
        boolean isPass = true;
        Set<ConstraintViolation<T>> violations = validator.validate(t);
        if (!violations.isEmpty()) {
            logger.error("validate fail，violations:[{}]", violations);
            isPass = false;
        }
        return isPass;
    }

    /**
     * 检验是否通过并获取返回值
     */
    public static <T> Set<ConstraintViolation<T>> validateAndGet(T t) {
        if (t == null) {
            throw new RuntimeException("校验对象不可为空");
        }
        return validator.validate(t);
    }

    private ValidatorUtil() {
    }
}
