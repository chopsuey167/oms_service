package com.mediamarkt.order.service;

import com.mediamarkt.order.dto.OrderCreationRequestDto;
import com.mediamarkt.order.entity.Order;
import com.mediamarkt.order.entity.OrderEvent;
import com.mediamarkt.order.entity.OrderState;
import org.springframework.statemachine.StateMachine;

public interface OrderService {

  Order createNewOrder(OrderCreationRequestDto orderRequestDto);

  StateMachine<OrderState, OrderEvent> confirmOrderPayment(Integer orderId);

  void updateOrderInfulfillment(Integer orderId);

  Order closeOrder(Integer orderId, String fulfillmentResult);

  Order findById(Integer orderId);

  void sendToFulfillOrder(Integer orderId);
}
