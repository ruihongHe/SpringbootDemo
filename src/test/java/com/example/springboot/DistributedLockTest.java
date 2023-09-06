package com.example.springboot;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 分布式锁测试类
 *
 * @author heruihong
 * @createTime 2023-09-06 10:29:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DistributedLockTest {
    @Autowired
    private RedisLockRegistry redisLockRegistry;
    @Test
    public void contextLoads() throws IllegalAccessException, InstantiationException {

        Lock lock = redisLockRegistry.obtain("redis");
        try{
            //尝试在指定时间内加锁，如果已经有其他锁锁住，获取当前线程不能加锁，则返回false，加锁失败；加锁成功则返回true
            if(lock.tryLock(3, TimeUnit.SECONDS)){
                log.info("lock is ready");
                TimeUnit.SECONDS.sleep(5);
            }
        } catch (InterruptedException e) {
            log.error("obtain lock error",e);
        } finally {
            lock.unlock();
        }

    }
}
