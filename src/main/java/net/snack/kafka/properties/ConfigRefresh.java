package net.snack.kafka.properties;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 监听apollo配置, 实时刷新 @ConfigurationProperties 中的配置
 */
@Configuration
@Slf4j
public class ConfigRefresh {

    @Autowired
    private ApplicationContext cxt;

    @ApolloConfigChangeListener(value = {"TEST1.common-db"})
    public void onChange(ConfigChangeEvent event) {
        Map<String, Object> configBeanMap = cxt.getBeansWithAnnotation(ConfigurationProperties.class);
        if (ObjectUtils.isEmpty(configBeanMap)) {
            return;
        }

        Set<String> keys = new HashSet<>();
        configBeanMap.forEach((className, configClass) -> {
            if (StringUtils.isBlank(className) || ObjectUtils.isEmpty(configClass)) {
                return;
            }
            ConfigurationProperties configurationProperties = configClass.getClass().getAnnotation(ConfigurationProperties.class);
            if (configurationProperties == null) {
                return;
            }
            String prefix = configurationProperties.prefix();
            if (StringUtils.isBlank(prefix.trim())) {
                return;
            }

            for (String changeKey : event.changedKeys()) {
                if (changeKey.startsWith(prefix)) {
                    log.info("apollo key = {} refresh", changeKey);
                    keys.add(changeKey);
                }
            }
        });
    }

}

