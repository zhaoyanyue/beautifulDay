package com.ptp;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author ZYY
 * @date 2019/10/22
 */
//点对点的消息消费者
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
                "tcp://192.168.1.138:61616"
        );
        try {
            connection=connectionFactory.createConnection();
            connection.start();
            session=connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
            destination=session.createQueue("FirstQueue");
            consumer=session.createConsumer(destination);
            while(true){
                TextMessage message=(TextMessage)consumer.receive(100);
                if(null!=message){
                    System.out.println("收到消息====="+message.getText());
                }else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if(null!=connection){
                    connection.close();
                }
            } catch (Throwable ignore) {
            }
        }
    }
}
