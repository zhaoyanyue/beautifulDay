package com.array;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author ZYY
 * @date 2019/10/22
 */
public class Consumer {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory;
        Connection connection=null;
        Session session;
        Destination destination;
        MessageConsumer consumer;
        connectionFactory=new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://192.168.1.168:61616"
        );

        try {
            connection =connectionFactory.createConnection();
            connection.start();
            session=connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
            destination=session.createTopic("FristTopic");
            consumer=session.createConsumer(destination);
            TextMessage message=(TextMessage)consumer.receive(100);
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        message.acknowledge();
                        System.out.println("收到消息==="+((TextMessage)message).getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection!=null){
                    connection.close();
                }
            } catch (Throwable e) {
            }
        }
    }

}
