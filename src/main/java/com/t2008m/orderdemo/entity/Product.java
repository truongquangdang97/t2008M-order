package com.t2008m.orderdemo.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.t2008m.orderdemo.entity.base.BaseEntity;
import com.t2008m.orderdemo.entity.enums.ProductSimpleStatus;
import com.t2008m.orderdemo.entity.enums.ProductStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String name;
    private String slug;
    private String description;
    private String detail;
    private String thumbnails;
    private BigDecimal price;
//    @Enumerated(EnumType.ORDINAL)
//    private ProductSimpleStatus status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
//    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
//    private Set<OrderDetail> orderDetails;

    @Basic
    private int status;
    @Transient
    private ProductStatus productStatus;

    @PostLoad // khi load
    void fillTransient() {
        this.productStatus = ProductStatus.of(status); // chuyển dữ liệu dạng số ở trong database về kiểu enum
    }
    @PrePersist // trước khi lưu
    void fillPersistent() {// đưa giá trị vào trường status
        this.status = this.productStatus.getValue();
    }
}
