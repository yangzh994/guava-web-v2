package com.guava.qps.v2.config;

import com.guava.qps.v2.customer.OrderCustomer;
import com.guava.qps.v2.customer.ProductCustomer;
import com.guava.qps.v2.dealy.DelayQueueConfig;
import com.guava.qps.v2.mapper.OrderMapper;
import com.guava.qps.v2.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class CustomerConfig {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ProductMapper productMapper;


    @Bean
    public ProductCustomer productCustomer(){
        ProductCustomer productCustomer = new ProductCustomer(DelayQueueConfig.PRODUCT_QUEUE);
        productCustomer.setOrderMapper(orderMapper);
        productCustomer.setRedisTemplate(redisTemplate);
        return productCustomer;
    }

    @Bean
    public OrderCustomer orderCustomer(){
        OrderCustomer orderCustomer = new OrderCustomer(DelayQueueConfig.ORDER_QUEUE);
        orderCustomer.setOrderMapper(orderMapper);
        orderCustomer.setProductMapper(productMapper);
        return orderCustomer;
    }

}
