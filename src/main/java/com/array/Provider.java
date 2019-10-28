package com.array;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author ZYY
 * @date 2019/10/22
 */
//队列的消息提供者
public class Provider {

    private static final int SEND_NUMBER = 10;

    public static void main(String[] args) {
        ConnectionFactory connectionFactory;
        Connection connection=null;
        Session session;
        Destination destination;
        MessageProducer messageProducer;
        connectionFactory =new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://192.168.1.138:61616"
        );

        try {
            connection=connectionFactory.createConnection();
            connection.start();
            session=connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
            destination=session.createTopic("FirstTopic");
            messageProducer=session.createProducer(destination );
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            sendMassage(session,messageProducer);
            session.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(connection!=null){
                    connection.close();
                }
            } catch (Throwable e) {
            }
        }
    }
    public  static  void sendMassage(Session session,MessageProducer producer) throws  Exception{
        for (int i = 0; i < SEND_NUMBER; i++) {
            TextMessage message=session.createTextMessage("ActiveMQ 发送的消息"+i);
            System.out.println("发送消息："+"ActiveMq 发送的消息"+ i);
            producer.send(message);
        }
    }
}
