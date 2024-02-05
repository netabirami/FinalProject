package com.zalando.ECommerceManagement.shoppingCartManagement.controller.cartView;

import com.zalando.ECommerceManagement.productManagement.model.Product;
import com.zalando.ECommerceManagement.productManagement.service.ProductService;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.CartItem;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.dto.CartViewDto;
import com.zalando.ECommerceManagement.shoppingCartManagement.model.dto.ProductDto;
import com.zalando.ECommerceManagement.shoppingCartManagement.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@RequestMapping("/cartView")
public class CartViewController {
    @Autowired
    public CartItemService cartItemService;
    @Autowired
    public ProductService productService;

    @GetMapping("/{id}")
    public CartViewDto getAllCartItem(@PathVariable Integer id) {
        List<CartItem> cartItems = cartItemService.getCartItemsById(id);
        List<Integer> productIds = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            productIds.add(cartItem.getProductId());
        }
        List<Product> products = productService.getProductsByIds(productIds);
        List<ProductDto> productDtos = new ArrayList<>();
        Double totalPrice = 0.0;

        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setPrice(product.getPrice());
            productDto.setDescription(product.getDescription());
            productDtos.add(productDto);
            totalPrice += product.getPrice();
        }

        CartViewDto cartViewDto = new CartViewDto();
        cartViewDto.setCartId(id);
        cartViewDto.setItems(productDtos);
        cartViewDto.setTotalPrice(totalPrice);

        return cartViewDto;
    }
}
