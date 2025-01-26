package lazecoding.project.common.util.security.exception;

/**
 * 未登录
 *
 * @author lazecoding
 */
public class NotLoginedException extends RuntimeException {
    public NotLoginedException(String msg) {
        super(msg);
    }

    public NotLoginedException() {
        super("未登录");
    }
}
