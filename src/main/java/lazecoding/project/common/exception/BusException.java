package lazecoding.project.common.exception;

/**
 * 业务异常
 *
 * @author lazecoding
 */
public class BusException extends RuntimeException {
    public BusException(String msg) {
        super(msg);
    }
}
