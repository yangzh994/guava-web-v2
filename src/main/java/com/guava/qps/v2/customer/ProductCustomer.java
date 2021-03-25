package com.guava.qps.v2.customer;


import com.guava.qps.v2.dealy.Message;
import com.guava.qps.v2.entity.Order;
import com.guava.qps.v2.mapper.OrderMapper;
import com.guava.qps.v2.runnables.DelayRunnable;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

public class ProductCustomer extends DelayRunnable {


    private OrderMapper orderMapper;

    private StringRedisTemplate redisTemplate;

    public ProductCustomer(DelayQueue delayQueue) {
        super(delayQueue);
    }

    public OrderMapper getOrderMapper() {
        return orderMapper;
    }

    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public StringRedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Delayed delayed = delayQueue.take();
                Message message = (Message) delayed;
                if (message != null) {
                    Order order = orderMapper.findById(message.getTask());
                    //0 就是未支付 ，1就是支付
                    if ("0".equals(order.getStatus())) {
                        System.out.println(order.getId());
                        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
                        RedisConnection connection = connectionFactory.getConnection();
                        connection.incr(redisTemplate.getStringSerializer().serialize(String.valueOf(order.getProductId())));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
