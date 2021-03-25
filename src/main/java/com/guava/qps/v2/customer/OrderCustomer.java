package com.guava.qps.v2.customer;

import com.guava.qps.v2.dealy.Message;
import com.guava.qps.v2.entity.Order;
import com.guava.qps.v2.mapper.OrderMapper;
import com.guava.qps.v2.mapper.ProductMapper;
import com.guava.qps.v2.runnables.DelayRunnable;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

public class OrderCustomer extends DelayRunnable {


    private OrderMapper orderMapper;

    private ProductMapper productMapper;

    public OrderMapper getOrderMapper() {
        return orderMapper;
    }

    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public ProductMapper getProductMapper() {
        return productMapper;
    }

    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public OrderCustomer(DelayQueue delayQueue) {
        super(delayQueue);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Delayed take = delayQueue.take();
                Message message = (Message) take;
                if (message != null) {
                    Order order = orderMapper.findById(message.getTask());
                    productMapper.decrSku(order.getProductId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
