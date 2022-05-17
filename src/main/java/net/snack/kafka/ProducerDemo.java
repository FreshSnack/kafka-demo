package net.snack.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
public class ProducerDemo {

    public static final String brokerList = "localhost:9092";

    public static final String topic = "topic-demo";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties prop = new Properties();
        prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);

        KafkaProducer<String, String> producer = new KafkaProducer<>(prop);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, "hello kafka");
        Future<RecordMetadata> send = producer.send(record);
        RecordMetadata recordMetadata = send.get();
        System.out.println(recordMetadata);
    }

}
