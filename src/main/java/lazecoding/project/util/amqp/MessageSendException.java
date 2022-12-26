package lazecoding.project.util.amqp;

/**
 * 消息发送异常
 *
 * @author lazecoding
 */
public class MessageSendException extends RuntimeException {
    public MessageSendException(String msg) {
        super(msg);
    }
}
