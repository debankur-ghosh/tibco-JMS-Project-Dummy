package com.example.tibco;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

/**
 * ActiveMQConnection - Establishes and manages JMS connection with ActiveMQ broker
 * This class provides methods to connect, disconnect, and manage message sessions
 */
public class ActiveMQConnection {
    
    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    
    private Connection connection;
    private Session session;
    private ConnectionFactory connectionFactory;
    
    /**
     * Initialize the connection factory
     */
    public ActiveMQConnection() {
        this.connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
    }
    
    /**
     * Establish connection to ActiveMQ broker
     * @throws JMSException if connection fails
     */
    public void connect() throws JMSException {
        try {
            connection = connectionFactory.createConnection(USERNAME, PASSWORD);
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            System.out.println("✓ Successfully connected to ActiveMQ broker at " + BROKER_URL);
        } catch (JMSException e) {
            System.err.println("✗ Failed to connect to ActiveMQ broker: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Get the current JMS Session
     * @return Session object
     */
    public Session getSession() {
        return session;
    }
    
    /**
     * Get the current JMS Connection
     * @return Connection object
     */
    public Connection getConnection() {
        return connection;
    }
    
    /**
     * Create a Queue
     * @param queueName Name of the queue to create
     * @return Queue object
     * @throws JMSException if queue creation fails
     */
    public Queue createQueue(String queueName) throws JMSException {
        if (session == null) {
            throw new JMSException("Session not initialized. Call connect() first.");
        }
        return session.createQueue(queueName);
    }
    
    /**
     * Create a Topic
     * @param topicName Name of the topic to create
     * @return Topic object
     * @throws JMSException if topic creation fails
     */
    public Topic createTopic(String topicName) throws JMSException {
        if (session == null) {
            throw new JMSException("Session not initialized. Call connect() first.");
        }
        return session.createTopic(topicName);
    }
    
    /**
     * Create a Message Producer
     * @param destination Queue or Topic destination
     * @return MessageProducer object
     * @throws JMSException if producer creation fails
     */
    public MessageProducer createProducer(Destination destination) throws JMSException {
        if (session == null) {
            throw new JMSException("Session not initialized. Call connect() first.");
        }
        return session.createProducer(destination);
    }
    
    /**
     * Create a Message Consumer
     * @param destination Queue or Topic destination
     * @return MessageConsumer object
     * @throws JMSException if consumer creation fails
     */
    public MessageConsumer createConsumer(Destination destination) throws JMSException {
        if (session == null) {
            throw new JMSException("Session not initialized. Call connect() first.");
        }
        return session.createConsumer(destination);
    }
    
    /**
     * Disconnect from ActiveMQ broker
     * @throws JMSException if disconnection fails
     */
    public void disconnect() throws JMSException {
        try {
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
            System.out.println("✓ Successfully disconnected from ActiveMQ broker");
        } catch (JMSException e) {
            System.err.println("✗ Error during disconnection: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Check if connection is active
     * @return true if connected, false otherwise
     */
    public boolean isConnected() {
        return connection != null;
    }
}