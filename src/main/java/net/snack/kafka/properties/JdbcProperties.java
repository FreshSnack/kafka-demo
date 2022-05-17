package net.snack.kafka.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@EnableConfigurationProperties
@Configuration
@ConfigurationProperties(prefix = "jdbc")
public class JdbcProperties {

    private String driverClassName;

    private String url;

    private String username;

    /**
     * 密码
     */
    private String password;
}
