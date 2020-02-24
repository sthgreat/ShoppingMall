package com.dzkjdx.mall.dao;

import com.dzkjdx.mall.MallApplicationTests;
import com.dzkjdx.mall.pojo.Shipping;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

@Slf4j
public class ShippingMapperTest extends MallApplicationTests {
    @Autowired
    private ShippingMapper shippingMapper;

    @Test
    public void selectByUid() {
        List<Shipping> shippings = shippingMapper.selectByUid(4);
        log.info("shippins: {}",shippings.toString());
    }
}