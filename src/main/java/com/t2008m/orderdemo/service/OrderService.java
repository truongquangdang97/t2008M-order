package com.t2008m.orderdemo.service;

import com.t2008m.orderdemo.entity.Order;
import com.t2008m.orderdemo.entity.OrderDetail;
import com.t2008m.orderdemo.entity.OrderDetailId;
import com.t2008m.orderdemo.entity.Product;
import com.t2008m.orderdemo.repository.OrderRepository;
import com.t2008m.orderdemo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;

    public OrderDetail saveOrder(String id){
        Order order = new Order();
        String orderId = UUID.randomUUID().toString();
        order.setId(orderId);//Tạo id cho order
        order.setUserId(1);
        order.setTotalPrice(new BigDecimal(0));

        Set<OrderDetail> orderDetailSet = new HashSet<>();
        // tạo product
        Product product01 = productRepository.findById(id).get();// tìm ra product
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(new OrderDetailId(orderId, product01.getId()));
        orderDetail.setOrder(order);
        orderDetail.setProduct(product01);
        orderDetail.setQuantity(1);
        orderDetail.setUnitPrice(product01.getPrice());
        order.setTotalPrice(product01.getPrice().multiply(new BigDecimal(orderDetail.getQuantity())));
        orderDetailSet.add(orderDetail);
        order.setOrderDetails(orderDetailSet);
        orderRepository.save(order);
        return orderDetail;
    }
}
