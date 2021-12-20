package top.pofengsystem.core.exception;

/**
 * 消息类型冲突异常
 */
public class MessageBeanTypeConflictException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public MessageBeanTypeConflictException(Throwable cause) {
        super(cause);
    }

    public MessageBeanTypeConflictException(String message) {
        super(message);
    }

    public MessageBeanTypeConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
