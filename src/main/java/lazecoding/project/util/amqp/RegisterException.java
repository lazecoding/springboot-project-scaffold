package lazecoding.project.util.amqp;

/**
 * 监听注册异常
 *
 * @author lazecoding
 */
public class RegisterException extends RuntimeException {
    public RegisterException(String msg) {
        super(msg);
    }
}
