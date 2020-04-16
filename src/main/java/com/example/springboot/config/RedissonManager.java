package com.example.springboot.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RedissonManager {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;

    /*单机模式自动装配*/
    @Bean
    public RedissonClient getRedisson() throws Exception{
        RedissonClient redisson = null;
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://"+host+":"+port);
        redisson = Redisson.create(config);
        System.out.println(redisson.getConfig().toString());
        return redisson;
    }
    /**
     * 哨兵模式自动装配
     * @return
     */
    /*@Bean
    public RedissonClient redissonSentinel() {
        Config config = new Config();
        config.useSentinelServers().addSentinelAddress(redssionProperties.getSentinelAddresses())
                .setMasterName(redssionProperties.getMasterName())
                .setTimeout(redssionProperties.getTimeout())
                .setMasterConnectionPoolSize(redssionProperties.getMasterConnectionPoolSize())
                .setSlaveConnectionPoolSize(redssionProperties.getSlaveConnectionPoolSize());

        return Redisson.create(config);
    }*/

}
