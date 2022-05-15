package com.t2008m.orderdemo.repository;

import com.t2008m.orderdemo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, String> {

    public Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%" )
    Page<Product> findByFirstName(@Param("name") String name, Pageable pageable);
//
//    @Query("SELECT p,c.name FROM Product as p JOIN Category as c on   p.id = c.id where c.name like %:name%")
//    Page<Product> findByCategory(@Param("name") String name, Pageable pageable);

}
