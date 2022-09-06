package netty.demo.protocolDemo.custom.message;


public class PingMessage extends Message {

    @Override
    public int getMessageType() {
        return PingMessage;
    }
}
