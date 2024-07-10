package com.mediamarkt.order.mapper;

import com.mediamarkt.order.dto.OrderCreationRequestDto;
import com.mediamarkt.order.dto.OrderCreationResponseDto;
import com.mediamarkt.order.dto.OrderForFulfillmentRequestDto;
import com.mediamarkt.order.dto.PaymentConfirmationResponseDto;
import com.mediamarkt.order.entity.Order;
import com.mediamarkt.order.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface OrderDtoMapper {

  @Mapping(target = "orderItem", source = "items")
  @Mapping(target = "customer.id", source = "customerId")
  Order toOrder(OrderCreationRequestDto orderCreationRequestDto);

  default Product map(Integer productId) {
    return Product.builder().id(productId).build();
  }

  default Integer map(Product product) {
    return product.getId();
  }

  @Mapping(target = "items", source = "orderItem")
  @Mapping(target = "customerId", source = "customer.id")
  OrderCreationResponseDto toOrderCreationResponseDto(Order order);

  @Mapping(target = "orderId", source = "id")
  PaymentConfirmationResponseDto toPaymentConfirmationResponseDto(Order order);

  @Mapping(target = "orderId", source = "id")
  @Mapping(target = "customerId", source = "customer.id")
  @Mapping(target = "items", source = "orderItem")
  OrderForFulfillmentRequestDto toOrderForFulfillmentRequestDto(Order order);

}
