package com.mediamarkt.order.service.impl;

import com.mediamarkt.order.dto.OrderForFulfillmentRequestDto;
import com.mediamarkt.order.service.FulfillmentService;
import com.mediamarkt.order.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class FulfillmentServiceImpl implements FulfillmentService {

  private final OrderService orderService;

  @Override
  public void fulfillOrder(OrderForFulfillmentRequestDto orderForFulfillmentRequestDto) {
    //TODO: implement logic to update order state and persist fulfill result

  }
}
