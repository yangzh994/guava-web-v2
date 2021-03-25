package com.guava.qps.v2.mapper;

import com.guava.qps.v2.entity.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProductMapper {

    @Insert("insert into product values(#{id},#{name},#{sku},#{price})")
    public int insert(Product product);

    @Select("select * from product where id=#{id}")
    public Product findById(Integer id);

    @Select("update product set sku=sku-1 where id=#{id}")
    public Product decrSku(Integer id);
}

