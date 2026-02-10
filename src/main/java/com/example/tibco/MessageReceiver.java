Debankur
package com.example.tibco;

import javax.jms.*;

public class MessageReceiver {
    
    private ActiveMQConnection jmsConnection;
    private MessageConsumer consumer;
    private Queue queue;
    
    public MessageReceiver(String queueName) throws JMSException {
        jmsConnection = new ActiveMQConnection();
        jmsConnection.connect();
        queue = jmsConnection.createQueue(queueName);
        consumer = jmsConnection.createConsumer(queue);
    }
    
    public Message receiveMessage(long timeout) throws JMSException {
        try {
            Message message = consumer.receive(timeout);
            if (message != null) {
                System.out.println("✓ Message received successfully");
                return message;
            } else {
                System.out.println("⚠ No message received within timeout period");
                return null;
            }
        } catch (JMSException e) {
            System.err.println("✗ Failed to receive message: " + e.getMessage());
            throw e;
        }
    }
    
    public String receiveTextMessage(long timeout) throws JMSException {
        Message message = receiveMessage(timeout);
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            return textMessage.getText();
        }
        return null;
    }
    
    public void receiveMultipleMessages(int count, long timeout) throws JMSException {
        System.out.println("Listening for " + count + " messages...");
        for (int i = 0; i < count; i++) {
            Message message = receiveMessage(timeout);
            if (message != null) {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println("Message " + (i + 1) + ": " + textMessage.getText());
                    String sender = textMessage.getStringProperty("SenderApp");
                    long timestamp = textMessage.getLongProperty("Timestamp");
                    System.out.println("  - Sender: " + sender);
                    System.out.println("  - Timestamp: " + timestamp);
                }
            } else {
                System.out.println("⚠ No more messages available");
                break;
            }
        }
    }
    
    public void close() throws JMSException {
        try {
            if (consumer != null) {
                consumer.close();
            }
            if (jmsConnection != null) {
                jmsConnection.disconnect();
            }
            System.out.println("✓ MessageReceiver closed successfully");
        } catch (JMSException e) {
            System.err.println("✗ Error closing MessageReceiver: " + e.getMessage());
            throw e;
        }
    }
    
    public static void main(String[] args) {
        MessageReceiver receiver = null;
        try {
            receiver = new MessageReceiver("TIBCO.QUEUE.DEFAULT");
            receiver.receiveMultipleMessages(5, 10000);
        } catch (JMSException e) {
            System.err.println("✗ Error in MessageReceiver: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (receiver != null) {
                try {
                    receiver.close();
                } catch (JMSException e) {
                    System.err.println("✗ Error closing receiver: " + e.getMessage());
                }
            }
        }
    }
}
