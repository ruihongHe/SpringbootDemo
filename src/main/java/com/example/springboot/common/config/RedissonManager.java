package com.example.springboot.common.config;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;*/

@Configuration
@Log4j2
public class RedissonManager {

    @Value("${redisson.address}")
    private String addressUrl;

    /*单机模式自动装配*/
    @Bean
    public RedissonClient getRedisson() throws Exception{
        RedissonClient redisson = null;
        Config config = new Config();
        config.useSingleServer()
                .setAddress(addressUrl);
        redisson = Redisson.create(config);
       log.info(JSONObject.toJSONString(redisson.getConfig()));
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
