package com.t2008m.orderdemo.service;

import com.t2008m.orderdemo.entity.Category;
import com.t2008m.orderdemo.entity.Product;
import com.t2008m.orderdemo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Page<Product> findAll(int page, int limit){
        return productRepository.findAll(PageRequest.of(page-1,limit));
    }

    public Page<Product> findByName(String name , int page, int limit){
        return productRepository.findByFirstName(name,PageRequest.of(page-1,limit));
    }

//    public Page<Product> findByCate(String name , int page  , int limit){
//        return productRepository.findByCategory(name,PageRequest.of(page-1,limit));
//    }
}
