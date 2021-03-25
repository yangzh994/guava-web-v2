package com.guava.qps.v2.listener;

import com.guava.qps.v2.customer.OrderCustomer;
import com.guava.qps.v2.customer.ProductCustomer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class CustomerListener implements ApplicationContextAware {

    final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void startCustomer() {
        ProductCustomer productCustomer = applicationContext.getBean(ProductCustomer.class);
        OrderCustomer orderCustomer = applicationContext.getBean(OrderCustomer.class);
        executorService.execute(productCustomer);
        executorService.execute(orderCustomer);
    }
}
