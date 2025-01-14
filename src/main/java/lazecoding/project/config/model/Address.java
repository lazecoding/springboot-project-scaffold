package lazecoding.project.config.model;

/**
 * IP + Port
 *
 * @author lazecoding
 */
public class Address {

    /**
     * IP
     */
    private String ip;

    /**
     * Port
     */
    private int port;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Address{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
