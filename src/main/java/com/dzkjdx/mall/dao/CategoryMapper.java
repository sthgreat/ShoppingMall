package com.dzkjdx.mall.dao;

import com.dzkjdx.mall.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface CategoryMapper {
    @Select("select * from mall_category where id = #{id}")
    Category findByName(@Param("id") Integer id);
}
