package org.lonesheep.kafka.consumer.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lonesheep.kafka.consumer.service.SimpleConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The command line runner which launches the application.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"org.lonesheep.kafka.consumer"})
public class SimpleConsumerRunner implements CommandLineRunner {

    /**
     * The class level logger.
     */
    private static final Logger LOG = LogManager.getLogger(SimpleConsumerRunner.class);

    /**
     * The kafka consumer example.  This is an autowired Spring component.
     */
    @Autowired
    private SimpleConsumer simpleConsumer;

    /**
     * The entry point into the component.
     * @param args any command line arguments (there are none).
     */
    public static void main(final String[] args) {
        SpringApplication.run(SimpleConsumerRunner.class);
    }

    /**
     * The command line runner starter which invokes the Kafka producer.
     *
     * @param args any command line arguments (there are none).
     */
    @Override
    public final void run(final String... args) {
        LOG.info("Invoking the consumer.");
        this.simpleConsumer.start();
    }
}
