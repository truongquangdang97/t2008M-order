package com.t2008m.orderdemo.controller;

import com.t2008m.orderdemo.entity.Category;
import com.t2008m.orderdemo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/categories")
public class CategoryApi {
    @Autowired
    CategoryService categoryService;
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Category>> getAll(){
        return ResponseEntity.ok(categoryService.getAll());
    }
}
