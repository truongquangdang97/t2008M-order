package com.t2008m.orderdemo.controller;


import com.t2008m.orderdemo.entity.Order;
import com.t2008m.orderdemo.repository.OrderRepository;
import com.t2008m.orderdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/order")
public class OrderApi {
    @Autowired
    OrderService orderService;

    @RequestMapping(method = RequestMethod.POST)
    public void saveOrder(String id){
        orderService.saveOrder(id);
    }


}
