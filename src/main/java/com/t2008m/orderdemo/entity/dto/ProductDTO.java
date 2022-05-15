package com.t2008m.orderdemo.entity.dto;

import java.math.BigDecimal;

public class ProductDTO {
    private String id;
    private String name;
    private String slug;
    private String description;
    private String detail;
    private String thumbnails; // nhiều ảnh cách nhau bởi dấu ,
    private BigDecimal price;
    private String createAt;
    private String updateAt;
    private String status;

}
