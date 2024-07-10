package com.mediamarkt.order.controller;

import com.mediamarkt.order.dto.OrderCreationRequestDto;
import com.mediamarkt.order.dto.OrderCreationResponseDto;
import com.mediamarkt.order.dto.PaymentConfirmationResponseDto;
import com.mediamarkt.order.entity.Order;
import com.mediamarkt.order.entity.OrderEvent;
import com.mediamarkt.order.entity.OrderState;
import com.mediamarkt.order.mapper.OrderDtoMapper;
import com.mediamarkt.order.service.OrderService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/orders")
@AllArgsConstructor
@Slf4j
public class OrderController {

  private final OrderService orderService;
  private final OrderDtoMapper orderDtoMapper;

  @PostMapping
  public ResponseEntity<OrderCreationResponseDto> placeOrder(
      @Valid @RequestBody OrderCreationRequestDto creationRequestDto) {

    Order newOrder = orderService.createNewOrder(creationRequestDto);
    OrderCreationResponseDto responseDto = orderDtoMapper.toOrderCreationResponseDto(newOrder);
    log.info("New order created: {}", newOrder);

    return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
  }

  @PostMapping("/{orderId}/confirm-payment")
  public ResponseEntity<PaymentConfirmationResponseDto> confirmOrderPayment(@PathVariable Integer orderId) {

    StateMachine<OrderState, OrderEvent> stateMachine = orderService.confirmOrderPayment(orderId);

    log.info("State after payment confirmation: {}", stateMachine.getState().getId().name());
    Order orderPaid = orderService.findById(orderId);
    return new ResponseEntity<>(orderDtoMapper.toPaymentConfirmationResponseDto(orderPaid), HttpStatus.OK);
  }

}
