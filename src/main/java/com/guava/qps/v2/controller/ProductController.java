package com.guava.qps.v2.controller;

import com.guava.qps.v2.entity.Product;
import com.guava.qps.v2.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("/create")
    public String create(Product product) {
        productMapper.insert(product);
        return "ok";
    }

    @RequestMapping("/cache")
    public String cache(@RequestParam("id") String id, @RequestParam("sku") String sku) {
        Product product = productMapper.findById(Integer.valueOf(id));
        if (product == null) return "fail cache,no product!";
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(id,sku);
        return "success cache, product id :" + id;
    }
}
