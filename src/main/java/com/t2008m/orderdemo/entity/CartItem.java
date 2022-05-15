package com.t2008m.orderdemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart_items")
public class CartItem {
    @EmbeddedId
    private CartItemId id;
//    private  String shoppingCartId;
//    private String productId;
    private  String productName;
    private String productImage;
    private int quantity;
    private BigDecimal unitPrice;


    @ManyToOne
    @MapsId("shoppingCartId")
    @JoinColumn(name="shopping_cart_id")
    private  ShoppingCart shoppingCart;


    @JsonBackReference
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name="product_id")
    private  Product product;

}
