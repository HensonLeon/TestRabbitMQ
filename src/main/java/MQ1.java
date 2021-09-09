import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MQ1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("8.129.236.141");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        User user = new User();
        user.setId(123456);
        user.setName("张三2222");
        user.setAge(7890);
        String userString = JSON.toJSONString(user);

        channel.queueDeclare("MQ10",false,false,false,null);
        channel.basicPublish("","MQ10",null,userString.getBytes());
        System.out.println("消息已发送22222");
        channel.close();

    }
}
