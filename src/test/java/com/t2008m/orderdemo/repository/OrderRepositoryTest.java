package com.t2008m.orderdemo.repository;

import com.t2008m.orderdemo.T2008mOrderDemoApplication;
import com.t2008m.orderdemo.config.H2JpaConfig;
import com.t2008m.orderdemo.entity.*;
import com.t2008m.orderdemo.entity.dto.CartItemDTO;
import com.t2008m.orderdemo.entity.dto.ShoppingCartDTO;
import com.t2008m.orderdemo.entity.enums.ProductSimpleStatus;
import com.t2008m.orderdemo.entity.enums.ProductStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {T2008mOrderDemoApplication.class, H2JpaConfig.class})
//@ActiveProfiles("test")
public class OrderRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    private String productId01 = "4a801070-5dc6-490b-97de-3ada1a110746";
    private String productId02 = "57a02f77-cc3c-4abd-a1c0-7792d5c07296";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Before // setup()
    public void before() throws Exception {
        Product product1 = new Product();
        product1.setId(productId01);
        product1.setName("Product 01");
        product1.setDescription("Helo");
        product1.setPrice(new BigDecimal(50));
        product1.setSlug("product-01");
        product1.setProductStatus(ProductStatus.ACTIVE);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setId(productId02);
        product2.setName("Product 02");
        product2.setDescription("Product 02");
        product2.setPrice(new BigDecimal(70));
        product2.setSlug("product-02");
        product2.setProductStatus(ProductStatus.ACTIVE);
        productRepository.save(product2);
    }


    @Test
    public void saveOrderSimple() {
        Order order = new Order();
        String orderId = UUID.randomUUID().toString();
        order.setId(orderId);//Tạo id cho order
        order.setUserId(1);
        order.setTotalPrice(new BigDecimal(0));

        Set<OrderDetail> orderDetailSet = new HashSet<>();
        // tạo product
        Product product01 = productRepository.findById(productId01).get();// tìm ra product
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(new OrderDetailId(orderId, product01.getId()));
        orderDetail.setOrder(order);
        orderDetail.setProduct(product01);
        orderDetail.setQuantity(3);//  số  lượng
        orderDetail.setUnitPrice(product01.getPrice()); /// giá
        order.setTotalPrice(product01.getPrice().multiply(new BigDecimal(orderDetail.getQuantity())));
        orderDetailSet.add(orderDetail);

        order.setOrderDetails(orderDetailSet);
        orderRepository.save(order);

        // tạo product
//        Product product02 = productRepository.findById(productId02).get();
//        OrderDetail orderDetail2 = new OrderDetail();
//        orderDetail2.setId(new OrderDetailId(orderId, product02.getId()));
//        orderDetail2.setOrder(order);
//        orderDetail2.setProduct(product02);
//        orderDetail2.setQuantity(5);
//        orderDetail2.setUnitPrice(product02.getPrice());
//        order.setTotalPrice(order.getTotalPrice().add(product02.getPrice().multiply(new BigDecimal(orderDetail2.getQuantity()))));
//        orderDetailSet.add(orderDetail2);



//        Order savedOrder = orderRepository.findAll().get(0);
//        System.out.println(savedOrder.getId());
//        System.out.println(savedOrder.getTotalPrice());
//        System.out.println(savedOrder.getOrderDetails().size());
//        for (OrderDetail od :
//                savedOrder.getOrderDetails()) {
//            System.out.println(od.getQuantity());
//            System.out.println(od.getUnitPrice());
//            System.out.println(od.getProduct().getName());
//        }
    }

    @Test
    public void realSaveShoppingCart() {
        // ai là người đặt order.
        String userAccessToken = "";
        String userId = "A001";
        // thông tin giỏ hàng.
        CartItemDTO cartItemDTO1 = CartItemDTO.builder()
                .productId(productId01)
                .quantity(5)
                .build();
        CartItemDTO cartItemDTO2 = CartItemDTO.builder()
                .productId(productId02)
                .quantity(3)
                .build();
        ShoppingCartDTO shoppingCartDTO = ShoppingCartDTO.builder()
                .userId(userId)
                .shipPhone("09123")
                .shipAddress("Hanoi")
                .shipNote("Ko cay")
                .shipName("Quang")
                .build();

        HashSet<CartItemDTO> setItemDTO = new HashSet<>();
        setItemDTO.add(cartItemDTO1);
        setItemDTO.add(cartItemDTO2);
        shoppingCartDTO.setItems(setItemDTO);///Gán giá trị vào trong ShoppingCartDTO.setItems
        // chuyển dto sang ShoppingCart và CartItem.
        // xử lý ở controller
        boolean hasException = false;
        ShoppingCart shoppingCart = ShoppingCart.builder() ///Bước này là gán từ DTO qua thật
                .id(UUID.randomUUID().toString())
                .userId(userId)
                .shipName(shoppingCartDTO.getShipName())
                .shipAddress(shoppingCartDTO.getShipAddress())
                .shipNote(shoppingCartDTO.getShipNote())
                .shipPhone(shoppingCartDTO.getShipPhone())
                .build();


        Set<CartItem> setCartItem = new HashSet<>();
        for (CartItemDTO cartItemDTO // tìm ra từng cái cartItemDTO
                : shoppingCartDTO.getItems()) { // chỗ này gán ở bên trên
            Optional<Product> optionalProduct = productRepository.findById(cartItemDTO.getProductId());// tìm ra từng cái product.
//            System.out.println(cartItemDTO+"cdcdcdcdcdcdc");
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
        System.out.println(shoppingCart);
//        shoppingCartRepository.save(shoppingCart);
//        System.out.println(shoppingCartRepository.findAll().size());
    }
}