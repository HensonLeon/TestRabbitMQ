import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class RoutingPro {
    public static void main(String[] args) throws IOException, TimeoutException {
        String[] a = {"waring","info","error"};
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("8.129.236.141");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("direct_logs", BuiltinExchangeType.DIRECT);
       while (true){
           System.out.println("输入信息:");
           String msg = new Scanner(System.in).nextLine();
           if("exit".equals(msg))
               break;
           String level = a[new Random().nextInt(a.length)];

           channel.basicPublish("direct_logs",level,null,msg.getBytes());
           System.out.println("消息已发送："+level+"---"+msg);
       }

        channel.close();
    }
}
