package com.guava.qps.v2.controller;


import com.guava.app.annotations.RequestRateLimiter;
import com.guava.qps.v2.dealy.DelayQueueConfig;
import com.guava.qps.v2.dealy.Message;
import com.guava.qps.v2.entity.Order;
import com.guava.qps.v2.mapper.OrderMapper;
import com.guava.qps.v2.mapper.ProductMapper;
import com.guava.qps.v2.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderMapper orderMapper;

    @RequestMapping("/create")
    @RequestRateLimiter(QPS = 200)
    public String create(@RequestParam("id") String id) {
        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
        RedisConnection connection = connectionFactory.getConnection();
        RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
        byte[] serialize = stringSerializer.serialize(String.valueOf(id));
        if (redisTemplate.hasKey(id)) {
            Long count = connection.decr(serialize);
            if (count <= 0) {
                redisTemplate.delete(id);
            }
            if (count >= 0) {
                Order order = new Order();
                order.setStatus("0");
                order.setProductId(Integer.valueOf(id));
                orderMapper.create(order);
                Producer.send(DelayQueueConfig.PRODUCT_QUEUE, new Message(order.getId(), 20));
                return "success create order";
            }
        }
        return "fail create order";
    }

    @RequestMapping("/pay/{id}")
    public String pay(@PathVariable("id") Integer id) {
        orderMapper.pay(id);
        Producer.send(DelayQueueConfig.ORDER_QUEUE, new Message(id, 0));
        return "pay success";
    }

    @RequestMapping("/info/{id}")
    public Order info(@PathVariable("id") Integer id) {
        return orderMapper.findById(id);
    }


}
