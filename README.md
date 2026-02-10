# TIBCO JMS Project Dummy

This project demonstrates the setup for TIBCO integration with ActiveMQ using JMS (Java Message Service).

## Project Overview
- **Framework**: TIBCO Enterprise Administrator
- **JMS Broker**: Apache ActiveMQ
- **Language**: Java
- **Build Tool**: Maven

## Project Structure
```
tibco-JMS-Project-Dummy/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/tibco/
│       │       ├── ActiveMQConnection.java
│       │       ├── MessageSender.java
│       │       └── MessageReceiver.java
│       └── resources/
│           └── activemq.properties
├── config/
│   ├── jms-connection.xml
│   └── tibco-config.properties
├── pom.xml
└── README.md
```

## Prerequisites
- Java 8 or higher
- Apache ActiveMQ 5.15+
- TIBCO Enterprise Administrator
- Maven 3.6+

## Configuration

### ActiveMQ Connection Details
- **Broker URL**: tcp://localhost:61616
- **Username**: admin
- **Password**: admin

### JMS Connection Properties
- **Connection Factory**: org.apache.activemq.ActiveMQConnectionFactory
- **Queue Name**: TIBCO.QUEUE.DEFAULT
- **Topic Name**: TIBCO.TOPIC.DEFAULT

## Getting Started

1. **Start ActiveMQ**:
   ```bash
   activemq start
   ```

2. **Build the project**:
   ```bash
   mvn clean install
   ```

3. **Run Message Sender**:
   ```bash
   mvn exec:java -Dexec.mainClass="com.example.tibco.MessageSender"
   ```

4. **Run Message Receiver**:
   ```bash
   mvn exec:java -Dexec.mainClass="com.example.tibco.MessageReceiver"
   ```

## Files Description

### ActiveMQConnection.java
Core JMS connection class that establishes connection with ActiveMQ broker.

### MessageSender.java
Example class to send messages to ActiveMQ queue.

### MessageReceiver.java
Example class to receive messages from ActiveMQ queue.

### activemq.properties
Configuration properties for ActiveMQ connection parameters.

## License
MIT License