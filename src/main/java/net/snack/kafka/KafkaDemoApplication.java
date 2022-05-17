package net.snack.kafka;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import net.snack.kafka.properties.JdbcProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@EnableApolloConfig
@SpringBootApplication
public class KafkaDemoApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(KafkaDemoApplication.class, args);
    }

    @Value("${timeout}")
    private Integer timeout;

    @Value("${encode}")
    private String encode;

    @ApolloConfig
    private Config config;

    @ApolloConfig(value = "build")
    private Config buildConfig;

//    @ApolloConfig(value = "TEST1.common-db")
//    private Config dbConfig;

    @Autowired
    private JdbcProperties jdbcProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        System.out.println(timeout);

        System.out.println(encode);

        System.out.println(config);

//        System.out.println(buildConfig);

//        System.out.println(dbConfig);

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            System.out.println(timeout);
            System.out.println(jdbcProperties);
        }, 0, 5, TimeUnit.SECONDS);

//        // 启动生产者，发送两条消息
//        new Thread(new OriginalProducer()).start();
//
//        // A、B都在消费者组A下，故消息要么被A消费，要么被B消费
//        // 启动消费者A
//        new Thread(new OriginalConsumer(OriginalConsumer.GROUP_ID_A, OriginalConsumer.CLIENT_ID_A)).start();
//        // 启动消费者B
//        new Thread(new OriginalConsumer(OriginalConsumer.GROUP_ID_A, OriginalConsumer.CLIENT_ID_B)).start();
//
//        // 启动消费者C，在消费者组B下，可以消费到两条消息
//        new Thread(new OriginalConsumer(OriginalConsumer.GROUP_ID_B, OriginalConsumer.CLIENT_ID_C)).start();
    }
}
