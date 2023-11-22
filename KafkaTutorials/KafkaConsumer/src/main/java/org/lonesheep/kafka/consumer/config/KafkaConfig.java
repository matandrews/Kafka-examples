package org.lonesheep.kafka.consumer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Class to hold the kafka configuration read from application.yaml and populated using Spring Boot and Lombok.
 */
@Configuration
@ConfigurationProperties(prefix = "kafka")
@EnableAutoConfiguration
@Getter
@Setter
public class KafkaConfig {

    /**
     * The kafka bootstrap server location(s).  If multiple, specify a comma separated list.
     */
    private String bootstrapServers;

    /**
     * The source (subscribed) topic identifier.
     */
    private String sourceTopicName;

    /**
     * The consumer group identifier.
     */
    private String consumerGroupIdentifier;

    /**
     * The consumer polling interval
     */
    private long pollInterval;
}
