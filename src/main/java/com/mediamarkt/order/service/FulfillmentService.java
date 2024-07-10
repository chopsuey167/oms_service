package com.mediamarkt.order.service;

import com.mediamarkt.order.dto.OrderForFulfillmentRequestDto;

public interface FulfillmentService {

  void fulfillOrder(OrderForFulfillmentRequestDto orderForFulfillmentRequestDto);
}
