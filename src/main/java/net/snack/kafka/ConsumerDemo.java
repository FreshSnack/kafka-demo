package net.snack.kafka;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Slf4j
public class ConsumerDemo {

    public static final String brokerList = "localhost:9092";

    public static final String topic = "topic-demo";

    public static final String groupId = "group.demo";

    public static void main(String[] args) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = loggerContext.getLogger("org.apache.kafka");
        logger.setLevel(Level.INFO);

        Properties prop = new Properties();
        prop.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        prop.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(prop);
        consumer.subscribe(Collections.singletonList(topic));

        while (true) {
            log.info("start poll message");
            ConsumerRecords<String, String> record = consumer.poll(Duration.ofSeconds(2));
            log.info("poll message result");
            record.forEach(r -> {
                log.info("k->" + r.key() + ",v->" + r.value());
            });
        }

    }
}
