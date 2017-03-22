package steps;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQSteps {

    public void sendMessageToRabbit(String username, String password, String host, int port, String queue, String message) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost("/");
        factory.setHost(host);
        factory.setPort(port);
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        channel.queueDeclare(queue, false, false, false, null);
        channel.basicPublish("", queue, null, message.getBytes("UTF-8"));
        channel.close();
        conn.close();
    }

    public String recivMessageFromRabbit(String username, String password, String host, int port, String queue) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost("/");
        factory.setHost(host);
        factory.setPort(port);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queue, false, false, false, null);
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queue, true, consumer);
        QueueingConsumer.Delivery delivery;
        delivery = consumer.nextDelivery();
        String message = new String(delivery.getBody());
        channel.close();
        connection.close();
        return message;
    }
}
