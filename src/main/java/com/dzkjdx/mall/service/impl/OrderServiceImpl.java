package com.dzkjdx.mall.service.impl;

import com.dzkjdx.mall.dao.OrderItemMapper;
import com.dzkjdx.mall.dao.OrderMapper;
import com.dzkjdx.mall.dao.ProductMapper;
import com.dzkjdx.mall.dao.ShippingMapper;
import com.dzkjdx.mall.enums.OrderStatusEnum;
import com.dzkjdx.mall.enums.PaymentTypeEnum;
import com.dzkjdx.mall.enums.ProductEnum;
import com.dzkjdx.mall.enums.ResponseEnum;
import com.dzkjdx.mall.pojo.*;
import com.dzkjdx.mall.service.IOrderService;
import com.dzkjdx.mall.vo.OrderItemVo;
import com.dzkjdx.mall.vo.OrderVo;
import com.dzkjdx.mall.vo.ResponseVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    @Transactional
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

        List<OrderItem> orderItemList = new ArrayList<>();
        Long orderNo = generateOrderNo();
        for(Cart cart : cartList){
            //根据product查数据库
            Product product = map.get(cart.getProductId());
            //是否有商品
            if(product == null){
                return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST,
                        "商品不存在， productId= "+cart.getProductId());
            }
            //商品上下架情况
            if(!ProductEnum.ON_SAIL.getStatus().equals(product.getStatus())){
                return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SAIL_OR_DELETE,
                        "商品不是在售状态，"+product.getName());
            }

            //是否有库存
            if(cart.getQuantity() > product.getStock()){
                return ResponseVo.error(ResponseEnum.STOCK_NOT_ENOUGH,
                        "商品库存不正确：" + product.getName());
            }
            OrderItem orderItem = buildOrderItem(uid, orderNo, cart.getQuantity(), product);
            orderItemList.add(orderItem);

            //减库存
            product.setStock(product.getStock() - cart.getQuantity());
            int i = productMapper.updateByPrimaryKeySelective(product);
            if(i<=0){
                return ResponseVo.error(ResponseEnum.ERROR);
            }
        }

        //计算总价格，只计算被选中的商品
        //生成订单入库：order orderitem，事务控制
        Order order = buildOrder(uid, orderNo, shippingId, orderItemList);
        int row = orderMapper.insertSelective(order);
        if(row <= 0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        int row2 = orderItemMapper.batchInsert(orderItemList);
        if(row2<=0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        //更新购物车，redis事务（打包命令）
        for(Cart cart : cartList){
            cartService.delete(uid, cart.getProductId());
        }

        //构造OrderVo对象
        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);

        return ResponseVo.success(orderVo);
    }

    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectByUid(uid);

        Set<Long> orderNoSet = orderList.stream().map(Order::getOrderNo).
                collect(Collectors.toSet());
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoSet(orderNoSet);
        Map<Long, List<OrderItem>> orderItemMap = orderItemList.stream()
                .collect(Collectors.groupingBy(OrderItem::getOrderNo));

        Set<Integer> ShippingIdSet = orderList.stream().map(Order::getShippingId).
                collect(Collectors.toSet());
        List<Shipping> shippingList = shippingMapper.selectByIdSet(ShippingIdSet);
        Map<Integer, Shipping> shippingMap = shippingList.stream()
                .collect(Collectors.toMap(Shipping::getId, shipping -> shipping));

        List<OrderVo> orderVoList = new ArrayList<>();
        for(Order order : orderList){
            OrderVo orderVo = buildOrderVo(order, orderItemMap.get(order.getOrderNo()),
                    shippingMap.get(order.getShippingId()));
            orderVoList.add(orderVo);
        }
        PageInfo pageInfo = new PageInfo<>(orderList);
        pageInfo.setList(orderVoList);

        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<OrderVo> detail(Integer uid, Long orderNo) {
        //校验订单是否属于该用户
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order == null || !order.getUserId().equals(uid)){
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        Set<Long> orderNoSet = new HashSet<>();
        orderNoSet.add(order.getOrderNo());
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoSet(orderNoSet);

        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);
        return ResponseVo.success(orderVo);
    }

    @Override
    public ResponseVo cancel(Integer uid, Long orderNo) {
        //校验订单是否属于该用户
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order == null || !order.getUserId().equals(uid)){
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        //假设业务逻辑简单，订单状态为未付款才可取消
        if(order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())){
            return ResponseVo.error(ResponseEnum.ORDER_STATUS_ERROR);
        }
        order.setStatus(OrderStatusEnum.CANCELED.getCode());
        order.setCloseTime(new Date());
        int row = orderMapper.updateByPrimaryKeySelective(order);
        if(row<=0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        return ResponseVo.success();
    }

    private OrderVo buildOrderVo(Order order, List<OrderItem> orderItemList, Shipping shipping) {
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);

        List<OrderItemVo> OrderItemVoList = orderItemList.stream().map(e -> {
            OrderItemVo orderItemVo = new OrderItemVo();
            BeanUtils.copyProperties(e, orderItemVo);
            return orderItemVo;
        }).collect(Collectors.toList());
        orderVo.setOrderItemVoList(OrderItemVoList);

        if(shipping!=null){
            orderVo.setShippingId(shipping.getId());
            orderVo.setShippingVo(shipping);
        }
        return orderVo;
    }

    private Order buildOrder(Integer uid, Long orderNo,
                             Integer shippingId,
                             List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(uid);
        order.setShippingId(shippingId);
        BigDecimal payment = new BigDecimal(0);
        for(OrderItem orderItem : orderItemList){
            payment = payment.add(orderItem.getTotalPrice());
        }
        order.setPayment(payment);
        order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());
        order.setPostage(0);
        order.setStatus(OrderStatusEnum.NO_PAY.getCode());

        return order;
    }

    private Long generateOrderNo() {
        return System.currentTimeMillis()  + new Random().nextInt(3);
    }

    private OrderItem buildOrderItem(Integer uid, Long orderNo,
                                     Integer quantity, Product product) {
        OrderItem item = new OrderItem();
        item.setUserId(uid);
        item.setOrderNo(orderNo);
        item.setProductId(product.getId());
        item.setProductName(product.getName());
        item.setProductImage(product.getMainImage());
        item.setCurrentUnitPrice(product.getPrice());
        item.setQuantity(quantity);
        item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return item;
    }
}
