package com.t2008m.orderdemo.controller;


import com.t2008m.orderdemo.entity.CartItem;
import com.t2008m.orderdemo.entity.CartItemId;
import com.t2008m.orderdemo.entity.Product;
import com.t2008m.orderdemo.entity.ShoppingCart;
import com.t2008m.orderdemo.entity.dto.CartItemDTO;
import com.t2008m.orderdemo.entity.dto.ShoppingCartDTO;
import com.t2008m.orderdemo.repository.OrderRepository;
import com.t2008m.orderdemo.repository.ProductRepository;
import com.t2008m.orderdemo.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/shoppingCart")
public class ShoppingCartApi {

    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;

    @RequestMapping(method = RequestMethod.POST)
    public ShoppingCart save(@RequestBody ShoppingCartDTO shoppingCartDTO){
        boolean hasException = false;
        ShoppingCart shoppingCart = ShoppingCart.builder() ///Bước này là gán từ DTO qua thật
                .id(UUID.randomUUID().toString())
                .userId(shoppingCartDTO.getUserId())
                .shipName(shoppingCartDTO.getShipName())
                .shipAddress(shoppingCartDTO.getShipAddress())
                .shipNote(shoppingCartDTO.getShipNote())
                .shipPhone(shoppingCartDTO.getShipPhone())
                .build();


        Set<CartItem> setCartItem = new HashSet<>();
        for (CartItemDTO cartItemDTO // tìm ra từng cái cartItemDTO
                : shoppingCartDTO.getItems()) { // chỗ này gán ở bên trên
            Optional<Product> optionalProduct = productRepository.findById(cartItemDTO.getProductId());// tìm ra từng cái product.
            if(!optionalProduct.isPresent()){
                hasException = true;
                break;
            }
            Product product = optionalProduct.get();
            CartItem cartItem = CartItem.builder()
                    .id(new CartItemId(shoppingCart.getId(), product.getId()))
                    .product(product)
                    .productName(product.getName())
                    .productImage(product.getThumbnails())
                    .quantity(cartItemDTO.getQuantity())
                    .unitPrice(product.getPrice())
                    .shoppingCart(shoppingCart)
                    .build();

            shoppingCart.addTotalPrice(cartItem); // add tổng giá bigdecimal
            setCartItem.add(cartItem);
        }
        shoppingCart.setItems(setCartItem);
//        System.out.println(shoppingCart);
//        shoppingCartRepository.save(shoppingCart);

        return shoppingCartRepository.save(shoppingCart);
    }
}
