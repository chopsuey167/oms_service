package com.mediamarkt.order.service.impl;

import com.mediamarkt.order.dto.OrderCreationRequestDto;
import com.mediamarkt.order.entity.Order;
import com.mediamarkt.order.entity.OrderEvent;
import com.mediamarkt.order.entity.OrderState;
import com.mediamarkt.order.exception.OrderNotFoundException;
import com.mediamarkt.order.mapper.OrderDtoMapper;
import com.mediamarkt.order.repository.OrderRepository;
import com.mediamarkt.order.service.OrderService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final OrderDtoMapper orderDtoMapper;
  private final StateMachineFactory<OrderState, OrderEvent> factory;

  @Override
  public Order createNewOrder(OrderCreationRequestDto orderRequestDto) {

    Optional<Order> existingOrder = orderRepository.findByOperationKey(orderRequestDto.getOperationKey());

    if (existingOrder.isPresent()) {
      return existingOrder.get();
    }

    Order newOrder = orderDtoMapper.toOrder(orderRequestDto);
    newOrder.setOrderState(OrderState.CREATED);

    return orderRepository.save(newOrder);
  }

  @Override
  public StateMachine<OrderState, OrderEvent> confirmOrderPayment(Integer orderId) {

    Order order = this.findById(orderId);

    StateMachine<OrderState, OrderEvent> sm = this.build(order);

    Message<OrderEvent> paymentMessage = MessageBuilder.withPayload(OrderEvent.PAY)
        .setHeader("order", order)
        .build();

    sm.sendEvent(paymentMessage);

    return sm;
  }

  @Override
  public void updateOrderInfulfillment(Integer orderId) {
    Order order = this.findById(orderId);
    order.setOrderState(OrderState.IN_FULFILLMENT);
    orderRepository.save(order);
  }

  @Override
  public Order closeOrder(Integer orderId, String fulfillmentResult) {
    Order order = this.findById(orderId);
    order.setOrderState(OrderState.CLOSED);
    order.setFulfillmentResult(fulfillmentResult);
    return orderRepository.save(order);
  }

  @Override
  public Order findById(Integer orderId) {
    return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
  }

  @Override
  public void sendToFulfillOrder(Integer orderId) {

    Order order = this.findById(orderId);

    StateMachine<OrderState, OrderEvent> sm = this.build(order);

    Message<OrderEvent> paymentMessage = MessageBuilder.withPayload(OrderEvent.FULFILL)
        .setHeader("order", order)
        .build();

    sm.sendEvent(paymentMessage);
  }

  private StateMachine<OrderState, OrderEvent> build(Order order) {
    String orderIdKey = Long.toString(order.getId());

    StateMachine<OrderState, OrderEvent> sm = this.factory.getStateMachine(orderIdKey);
    sm.stop();
    sm.getStateMachineAccessor()
        .doWithAllRegions(sma -> {

          sma.addStateMachineInterceptor(new StateMachineInterceptorAdapter<OrderState, OrderEvent>() {

            @Override
            public void preStateChange(
                State<OrderState, OrderEvent> state, Message<OrderEvent> message,
                Transition<OrderState, OrderEvent> transition, StateMachine<OrderState, OrderEvent> stateMachine) {

              Optional.ofNullable(message).flatMap(
                      msg -> Optional.ofNullable((Order) msg.getHeaders().getOrDefault("order", -1)))
                  .ifPresent(order -> {
                    order.setOrderState(state.getId());
                    orderRepository.save(order);
                  });

            }
          });
          sma.resetStateMachine(new DefaultStateMachineContext<>(order.getOrderState(), null, null, null));
        });
    sm.start();
    return sm;
  }

}
