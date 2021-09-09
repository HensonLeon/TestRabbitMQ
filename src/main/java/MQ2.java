import com.rabbitmq.client.*;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MQ2 {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("8.129.236.141");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("MQ1",false,false,
                false,null);
        System.out.println("等待接收数据ssss");

        DeliverCallback callback = new DeliverCallback(){

            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                String msg = new String(message.getBody(),"UTF-8");

                System.out.println("收到："+msg);
                channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
            }
        };
        //消费者取消时的回调对象
        CancelCallback cancel = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {
            }
        };
        channel.basicConsume("MQ1",false,callback,cancel);
    }
}
