package com.dzkjdx.mall.service.impl;

import com.dzkjdx.mall.dao.ProductMapper;
import com.dzkjdx.mall.enums.ProductEnum;
import com.dzkjdx.mall.enums.ResponseEnum;
import com.dzkjdx.mall.form.CartAddForm;
import com.dzkjdx.mall.form.CartUpdateForm;
import com.dzkjdx.mall.pojo.Cart;
import com.dzkjdx.mall.pojo.Product;
import com.dzkjdx.mall.service.ICartService;
import com.dzkjdx.mall.vo.CartProductVo;
import com.dzkjdx.mall.vo.CartVo;
import com.dzkjdx.mall.vo.ResponseVo;
import com.github.pagehelper.util.StringUtil;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CartService implements ICartService {
    private final static String CART_REDIS_KEY_TEMPLATE = "cart_%d";
    private Gson gson = new Gson();
    Integer quantity = 1;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public ResponseVo<CartVo> add(Integer uid, CartAddForm cartAddForm) {
        Product product = productMapper.selectByPrimaryKey(cartAddForm.getProductId());
        //判断商品是否存在
        if(product == null){
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }
        //判断商品是否在售
        if(!product.getStatus().equals(ProductEnum.ON_SAIL.getStatus())){
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SAIL_OR_DELETE);
        }
        //判断商品库存是否充足
        if(product.getStock() <= 0){
            return ResponseVo.error(ResponseEnum.STOCK_NOT_ENOUGH);
        }
        //写入redis
        //key:cart_uid
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = (String.format(CART_REDIS_KEY_TEMPLATE,uid));
        //取出值进行判断
        String value = opsForHash.get(redisKey, String.valueOf(product.getId()));
        Cart cart = new Cart();
        if(StringUtil.isEmpty(value)){
            //redis中没有该商品
            cart = new Cart(product.getId(), quantity , cartAddForm.getSelected());
        }else {
            //有商品，数量累加
            cart = gson.fromJson(value, Cart.class);
            cart.setQuantity(cart.getQuantity() + quantity);
        }
        opsForHash.put(redisKey, String.valueOf(product.getId()), gson.toJson(cart));

        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> list(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = (String.format(CART_REDIS_KEY_TEMPLATE,uid));
        Map<String, String> entries = opsForHash.entries(redisKey);

        boolean selectAll = true;
        Integer productTotalQuantity = 0;
        BigDecimal productTotalPrice = BigDecimal.valueOf(0);

        CartVo cartVo = new CartVo();
        List<CartProductVo> cartProductVoList = new ArrayList<>();

        //循环购物车中商品列表
        for(Map.Entry<String, String> entry : entries.entrySet()){
            Integer productId = Integer.valueOf(entry.getKey());
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);
            productTotalQuantity += cart.getQuantity();

            //TODO 需要优化，使用mysql的in
            Product product = productMapper.selectByPrimaryKey(productId);

            if(product != null){
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(productId);
                cartProductVo.setQuantity(cart.getQuantity());
                cartProductVo.setProductName(product.getName());
                cartProductVo.setProductSubtitle(product.getSubtitle());
                cartProductVo.setProductMainImage(product.getMainImage());
                cartProductVo.setProductPrice(product.getPrice());
                cartProductVo.setProductStatus(product.getStatus());
                cartProductVo.setProductTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));
                cartProductVo.setProductStock(product.getStock());
                cartProductVo.setProductSelected(cart.getProductSelected());

                //计算购物车中的总价(只计算选中)
                if(cart.getProductSelected()){
                    productTotalPrice = productTotalPrice.add(cartProductVo.getProductTotalPrice());
                }
                cartProductVoList.add(cartProductVo);

                if(!cart.getProductSelected()){
                    selectAll = false;
                }
            }
        }
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setSelectAll(selectAll);
        cartVo.setCartTotalPrice(productTotalPrice);
        cartVo.setCartTotalQuantity(productTotalQuantity);
        return ResponseVo.success(cartVo);
    }

    @Override
    public ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = (String.format(CART_REDIS_KEY_TEMPLATE,uid));
        //取出值进行判断
        String value = opsForHash.get(redisKey, String.valueOf(productId));
        if(StringUtil.isEmpty(value)){
            //redis中没有该商品，报错
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXSIST);
        }

        //有商品，修改内容
        Cart cart = gson.fromJson(value, Cart.class);
        if(cartUpdateForm.getQuantity() != null
                && cartUpdateForm.getQuantity() >= 0){
            cart.setQuantity(cartUpdateForm.getQuantity());
        }
        if(cartUpdateForm.getSelected() != null){
            cart.setProductSelected(cartUpdateForm.getSelected());
        }

        opsForHash.put(redisKey, String.valueOf(productId), gson.toJson(cart));
        return list(uid);
    }

    public ResponseVo<CartVo> delete(Integer uid, Integer productId){
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = (String.format(CART_REDIS_KEY_TEMPLATE,uid));
        //取出值进行判断
        String value = opsForHash.get(redisKey, String.valueOf(productId));
        if(StringUtil.isEmpty(value)){
            //redis中没有该商品，报错
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXSIST);
        }
        opsForHash.delete(redisKey, String.valueOf(productId));
        return list(uid);
    }
}
