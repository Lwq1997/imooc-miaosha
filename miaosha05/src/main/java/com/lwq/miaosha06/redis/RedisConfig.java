package com.lwq.miaosha06.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: Lwq
 * @Date: 2019/3/18 12:59
 * @Version 1.0
 * @Describe
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {

    private String host;
    private int port;
    private int timeout;
    private int poolMaxTotal;
    private int poolMaxldle;
    private int poolMaxWait;
}

