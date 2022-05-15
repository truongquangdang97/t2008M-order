package com.t2008m.orderdemo.repository;

import com.t2008m.orderdemo.entity.Order;
import com.t2008m.orderdemo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
}
