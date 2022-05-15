package com.t2008m.orderdemo.controller;

import com.t2008m.orderdemo.entity.Product;
import com.t2008m.orderdemo.entity.enums.ProductSimpleStatus;
import com.t2008m.orderdemo.entity.enums.ProductStatus;
import com.t2008m.orderdemo.repository.ProductRepository;
import com.t2008m.orderdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/products")
public class ProductApi {

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Product>> findAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit){
        return ResponseEntity.ok(productService.findAll(page, limit));
    }
    @RequestMapping(method = RequestMethod.GET,path = "byName")
    public ResponseEntity<Page<Product>> findByName(@RequestParam(defaultValue = "",name = "name")String name,@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "2") int limit){
        return ResponseEntity.ok(productService.findByName(name,page, limit));
    }
//    @RequestMapping(method = RequestMethod.GET,path = "byCategory")
//    public ResponseEntity<Page<Product>> findByCategory(@RequestParam(defaultValue = "",name = "name")String name,@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "2") int limit){
//        return ResponseEntity.ok(productService.findByName(name,page, limit));
//    }
//    public ResponseEntity<?> save() {
//        Product product = new Product();
//        product.setName("Product 01");
//        product.setDescription("Helo");
//        product.setPrice(new BigDecimal(20));
//        product.setSlug("hello-product");
////        product.setStatus(ProductSimpleStatus.ACTIVE);
//        productRepository.save(product);
//        return new ResponseEntity<>("Okie", HttpStatus.OK);
//    }
}
