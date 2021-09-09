import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class WorkMQPro {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("8.129.236.141");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("workMQ",false,false,false,null);
        for (int i=0;i<100000;i++){
            String message = "task.."+i;
            channel.basicPublish("","workMQ",null,message.getBytes());
            System.out.println("消息已发送");
            Thread.sleep(i*2);
        }
        //channel.queueDeclare("MQ10",false,false,false,null);
        //channel.basicPublish("","MQ10",null,"woshinia".getBytes());
        //System.out.println("消息已发送");
        channel.close();
    }
}
