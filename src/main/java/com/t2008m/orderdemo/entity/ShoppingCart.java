package com.t2008m.orderdemo.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "shopping_carts")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class ShoppingCart {
    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String userId;
    private BigDecimal totalPrice;
    private String shipName;
    private String shipAddress;
    private String shipPhone;
    private String shipNote;

    @OneToMany(mappedBy = "shoppingCart",cascade = CascadeType.ALL)
    private Set<CartItem> items;

    public void addTotalPrice(CartItem cartItem){

        if(this.totalPrice==null){
            this.totalPrice = new BigDecimal(0);
        }

        BigDecimal quantityInBigDecimal = new BigDecimal(cartItem.getQuantity());
        this.totalPrice = this.totalPrice.add(cartItem.getUnitPrice().multiply(quantityInBigDecimal));
    }


}
