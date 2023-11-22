package org.lonesheep.kafka.producer.service;

import org.lonesheep.kafka.producer.config.KafkaConfig;

import java.util.Properties;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * A simple producer class writing a String key/value pair to the Kafka topic every 10 seconds.
 */
@Component
public class SimpleProducer {

    /**
     * Create the class level logger.
     */
    private static final Logger LOG = LogManager.getLogger(SimpleProducer.class);

    /**
     * The Kafka properties passed into the Kafka Consumer object when it is initialised.
     */
    private final Properties kafkaProperties = new Properties();

    /**
     * The name of the kafka topic to output messages to.
     */
    private final String kafkaSinkTopicName;

    /**
     * Constructor.
     *
     * @param kafkaConfig the Kafka Configuration
     */
    public SimpleProducer(final KafkaConfig kafkaConfig) {

        // Assign localhost id
        kafkaProperties.put("bootstrap.servers", kafkaConfig.getBootstrapServers());

        // Set acknowledgements for producer requests.
        kafkaProperties.put("acks", "all");

        // If the request fails, the producer can automatically retry,
        kafkaProperties.put("retries", 0);

        // Specify buffer size in config.
        kafkaProperties.put("batch.size", 16384);

        // Reduce the no of requests less than 0   .
        kafkaProperties.put("linger.ms", 1);

        // The buffer.memory controls the total amount of memory available to the producer for buffering.
        kafkaProperties.put("buffer.memory", 33554432);

        // Simple Strings are used for the key and value pairs.
        kafkaProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.kafkaSinkTopicName = kafkaConfig.getSinkTopicName();
    }

    /**
     * Start the producer and write a simple key/value message to the topic.
     */
    public void start() {

        Producer<String, String> producer = new KafkaProducer<>(this.kafkaProperties);

        for (int i = 0; i < 1000; i++) {
            producer.send(new ProducerRecord<>(
                    this.kafkaSinkTopicName,
                    Integer.toString(i),
                    Integer.toString(i)));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // bury this sleep exception
            }

            LOG.debug("Sending message key='{}', value='{}'.", i, i);
        }

        producer.close();
    }
}
