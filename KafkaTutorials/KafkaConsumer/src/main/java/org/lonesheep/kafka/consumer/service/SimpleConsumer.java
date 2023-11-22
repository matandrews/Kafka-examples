package org.lonesheep.kafka.consumer.service;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lonesheep.kafka.consumer.config.KafkaConfig;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Component
public class SimpleConsumer {

    /**
     * Create the class level logger.
     */
    private static final Logger LOG = LogManager.getLogger(SimpleConsumer.class);

    /**
     * The Kafka properties passed into the Kafka Consumer object when it is initialised.
     */
    private final Properties kafkaProperties = new Properties();

    /**
     * The name of the kafka topic to read messages from.
     */
    private final String kafkaSourceTopicName;

    /**
     * The kakfa topic polling interval.
     */
    private final long pollingInterval;

    /**
     * Constructor.
     *
     * @param kafkaConfig the Kafka Configuration
     */
    public SimpleConsumer(final KafkaConfig kafkaConfig) {

        // Assign localhost id
        this.kafkaProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());

        // Set the consumer group identifier.
        this.kafkaProperties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfig.getConsumerGroupIdentifier());

        // Simple Strings are used for the key and value pairs.
        this.kafkaProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        this.kafkaProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        this.kafkaProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        this.kafkaSourceTopicName = kafkaConfig.getSourceTopicName();
        this.pollingInterval = kafkaConfig.getPollInterval();
    }

    /**
     * Start the producer and write a simple key/value message to the topic.
     */
    public void start() {

        Consumer<String, String> consumer = new KafkaConsumer<>(this.kafkaProperties);

        try (consumer) {
            consumer.subscribe(Collections.singletonList(this.kafkaSourceTopicName));

            // Loop indefinitely.
            while (true) {

                // Poll the kafka topic for some records.  The poll will return immedietely if records are found,
                // otherwise it will wait for the "pollingInterval" before returning.
                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(this.pollingInterval));

                LOG.info("Number of messages returned='{}'", consumerRecords.count());

                consumerRecords.forEach(record ->
                        LOG.debug("Reading message key='{}', value='{}', partition='{}', offset='{}'",
                        record.key(),
                        record.value(),
                        record.partition(),
                        record.offset()));

                // commits the offset of record to broker.
                consumer.commitAsync();
            }
        }


    }
}
