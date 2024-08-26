package lazecoding.project.common.util.security.exception;

/**
 * 无权限
 *
 * @author lazecoding
 */
public class NoPermissionsException extends RuntimeException {
    public NoPermissionsException(String msg) {
        super(msg);
    }

    public NoPermissionsException() {
        super("无权限");
    }
}
