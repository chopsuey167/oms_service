package com.mediamarkt.order.config;

import com.mediamarkt.order.entity.Order;
import com.mediamarkt.order.entity.OrderEvent;
import com.mediamarkt.order.entity.OrderState;
import java.util.EnumSet;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Slf4j
@EnableStateMachineFactory
@Configuration
public class StateMachineConfig extends StateMachineConfigurerAdapter<OrderState, OrderEvent> {

  @Override
  public void configure(StateMachineStateConfigurer<OrderState, OrderEvent> states) throws Exception {
    states
        .withStates()
        .initial(OrderState.CREATED)
        .states(EnumSet.allOf(OrderState.class))
        .end(OrderState.CLOSED);
  }

  @Override
  public void configure(StateMachineTransitionConfigurer<OrderState, OrderEvent> transitions) throws Exception {
    transitions
        .withExternal().source(OrderState.CREATED).target(OrderState.PAID).event(OrderEvent.PAY).guard(
            orderStateValidation())
        .and()
        .withExternal().source(OrderState.PAID).target(OrderState.IN_FULFILLMENT).event(OrderEvent.FULFILL)
        .and()
        .withExternal().source(OrderState.IN_FULFILLMENT).target(OrderState.CLOSED).event(OrderEvent.CLOSED);
  }

  @Override
  public void configure(StateMachineConfigurationConfigurer<OrderState, OrderEvent> config) throws Exception {
    StateMachineListenerAdapter<OrderState, OrderEvent> adapter = new StateMachineListenerAdapter<OrderState, OrderEvent>() {
      @Override
      public void stateChanged(State<OrderState, OrderEvent> from, State<OrderState, OrderEvent> to) {
        log.info(String.format("Order state changed(from: %s, to: %s)", from + "", to + ""));
      }
    };
    config.withConfiguration()
        .autoStartup(false)
        .listener(adapter);
  }

  public Guard<OrderState, OrderEvent> orderStateValidation() {
    return context -> {
      Optional<Order> order = Optional.ofNullable((Order) context.getMessageHeader("order"));
      return order.get().getOrderState() == OrderState.CREATED;
    };
  }
}
