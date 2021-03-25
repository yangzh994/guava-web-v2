package com.guava.qps.v2.mapper;

import com.guava.qps.v2.entity.Order;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderMapper {

    @Insert("insert into orders values(#{id},#{productId},#{status})")
    @SelectKey(keyProperty = "id", keyColumn = "id", before = false, resultType = Integer.class, statement = "select last_insert_id()")
    public int create(Order order);

    @Select("select * from orders where id = #{id}")
    public Order findById(Integer id);

    @Update("update orders set status='1' where id=#{id}")
    public int pay(Integer id);
}
