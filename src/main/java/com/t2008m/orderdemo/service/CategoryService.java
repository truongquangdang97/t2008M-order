package com.t2008m.orderdemo.service;

import com.t2008m.orderdemo.entity.Category;
import com.t2008m.orderdemo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    public List<Category> getAll(){
        return categoryRepository.findAll();
    }
}
