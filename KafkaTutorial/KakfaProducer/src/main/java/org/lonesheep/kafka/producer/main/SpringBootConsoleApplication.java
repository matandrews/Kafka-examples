package org.lonesheep.kafka.producer.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lonesheep.kafka.producer.service.SimpleProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The command line runner which launches the application.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"org.lonesheep.kafka.producer"})
public class SpringBootConsoleApplication implements CommandLineRunner {

    /**
     * The class level logger.
     */
    private static final Logger LOG = LogManager.getLogger(SpringBootConsoleApplication.class);

    /**
     * The kafka producer example.  This is an autowired Spring component.
     */
    @Autowired
    private SimpleProducer simpleProducer;

    /**
     * The entry point into the component.
     * @param args any command line arguments (there are none).
     */
    public static void main(final String[] args) {
        SpringApplication.run(SpringBootConsoleApplication.class);
    }

    /**
     * The command line runner starter which invokes the Kafka producer.
     *
     * @param args any command line arguments (there are none).
     */
    @Override
    public final void run(final String... args) {
        LOG.info("Invoking the producer.");
        simpleProducer.start();
    }
}
