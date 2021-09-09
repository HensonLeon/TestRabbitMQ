import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class RoutingCus {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("8.129.236.141");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("direct_logs", BuiltinExchangeType.DIRECT);

        String queueName = channel.queueDeclare().getQueue();

        while(true){
            System.out.println("输入接收的日志级别，用空格隔开:");
            String[] a = new Scanner(System.in).nextLine().split("\\s");
            for (String level:a) {
                channel.queueBind(queueName,"direct_logs",level);
            }
            if("exit".equals(a))
                break;
            System.out.println("等待接收数据");
            DeliverCallback callback = new DeliverCallback(){

                @Override
                public void handle(String consumerTag, Delivery message) throws IOException {
                    String msg = new String(message.getBody(), "UTF-8");
                    String routingKey = message.getEnvelope().getRoutingKey();
                    System.out.println("收到："+routingKey+"----"+msg);
                }
            };


            CancelCallback cancelCallback = new CancelCallback(){

                @Override
                public void handle(String consumerTag) throws IOException {

                }
            };
            channel.basicConsume(queueName,true,callback,cancelCallback);
        }





    }
}
