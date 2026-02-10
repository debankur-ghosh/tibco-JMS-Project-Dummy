package com.example.tibco;

import javax.jms.*;

/**
 * MessageSender - Sends messages to ActiveMQ queue
 * This class demonstrates how to produce messages to a JMS queue
 */
public class MessageSender {
    
    private ActiveMQConnection jmsConnection;
    private MessageProducer producer;
    private Queue queue;
    
    /**
     * Initialize the MessageSender with queue name
     * @param queueName Name of the queue to send messages to
     * @throws JMSException if initialization fails
     */
    public MessageSender(String queueName) throws JMSException {
        jmsConnection = new ActiveMQConnection();
        jmsConnection.connect();
        queue = jmsConnection.createQueue(queueName);
        producer = jmsConnection.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
    }
    
    /**
     * Send a text message to the queue
     * @param message The message content to send
     * @throws JMSException if message sending fails
     */
    public void sendTextMessage(String message) throws JMSException {
        try {
            TextMessage textMessage = jmsConnection.getSession().createTextMessage(message);
            textMessage.setStringProperty("SenderApp", "TIBCO-JMS-Dummy");
            textMessage.setLongProperty("Timestamp", System.currentTimeMillis());
            
            producer.send(textMessage);
            System.out.println("✓ Message sent successfully: " + message);
        } catch (JMSException e) {
            System.err.println("✗ Failed to send message: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Send multiple messages to the queue
     * @param messages Array of message contents to send
     * @throws JMSException if message sending fails
     */
    public void sendMultipleMessages(String[] messages) throws JMSException {
        for (String message : messages) {
            sendTextMessage(message);
        }
        System.out.println("✓ All messages sent successfully!");
    }
    
    /**
     * Close the sender and cleanup resources
     * @throws JMSException if closing fails
     */
    public void close() throws JMSException {
        try {
            if (producer != null) {
                producer.close();
            }
            if (jmsConnection != null) {
                jmsConnection.disconnect();
            }
            System.out.println("✓ MessageSender closed successfully");
        } catch (JMSException e) {
            System.err.println("✗ Error closing MessageSender: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Main method to demonstrate message sending
     */
    public static void main(String[] args) {
        MessageSender sender = null;
        try {
            sender = new MessageSender("TIBCO.QUEUE.DEFAULT");
            
            // Send single message
            sender.sendTextMessage("Hello from TIBCO JMS Project!");
            
            // Send multiple messages
            String[] messages = {
                "Message 1: Test Message",
                "Message 2: TIBCO Integration",
                "Message 3: ActiveMQ Queue Demo"
            };
            sender.sendMultipleMessages(messages);
            
        } catch (JMSException e) {
            System.err.println("✗ Error in MessageSender: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (sender != null) {
                try {
                    sender.close();
                } catch (JMSException e) {
                    System.err.println("✗ Error closing sender: " + e.getMessage());
                }
            }
        }
    }
}