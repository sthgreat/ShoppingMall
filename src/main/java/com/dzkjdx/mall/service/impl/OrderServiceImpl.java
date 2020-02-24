package com.dzkjdx.mall.service.impl;

import com.dzkjdx.mall.dao.ProductMapper;
import com.dzkjdx.mall.dao.ShippingMapper;
import com.dzkjdx.mall.enums.ResponseEnum;
import com.dzkjdx.mall.pojo.Cart;
import com.dzkjdx.mall.pojo.Product;
import com.dzkjdx.mall.pojo.Shipping;
import com.dzkjdx.mall.service.IOrderService;
import com.dzkjdx.mall.vo.CartVo;
import com.dzkjdx.mall.vo.OrderVo;
import com.dzkjdx.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import com.dzkjdx.mall.service.impl.CartService;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.*;

@Component
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseVo<OrderVo> create(Integer uid, Integer shippingId) {
        //校验收货地址
        Shipping shipping = shippingMapper.selectByUidAndShippingId(uid, shippingId);
        if(shipping == null){
            return ResponseVo.error(ResponseEnum.SHIPPING_NOT_EXIST);
        }

        //获取购物车，校验购物车（是否有商品，库存是否充足）
        List<Cart> cartList = cartService.listForCart(uid);
        for(Cart cart : cartList){
            if(cart.getProductSelected().equals(false)){
                cartList.remove(cart);
            }
        }
        if(CollectionUtils.isEmpty(cartList)){
            return ResponseVo.error(ResponseEnum.SELECT_LIST_IS_EMPTY);
        }
        //获取cartlist中所有id拿到数据库中查询
        Set<Integer> ProductIdSet = new HashSet<>();
        for(Cart cart : cartList){
            ProductIdSet.add(cart.getProductId());
        }
        List<Product> productList = productMapper.selectByProductIdSet(ProductIdSet);
        Map<Integer, Product> map = new HashMap<>();
        for(Product product : productList){
            map.put(product.getId(), product);
        }

        for(Cart cart : cartList){
            //根据product查数据库
            Product product = map.get(cart.getProductId());
            //是否有商品
            if(product == null){
                return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST,
                        "商品不存在， productId= "+cart.getProductId());
            }
            //是否有库存
            if(cart.getQuantity() > product.getStock()){
                return ResponseVo.error(ResponseEnum.STOCK_NOT_ENOUGH,
                        "商品库存不正确：" + product.getName());
            }
        }


        //计算总价格，只计算被选中的商品

        //生成订单入库：order orderitem，事务控制

        //减库存

        //更新购物车

        //返回前端
        return null;
    }
}
