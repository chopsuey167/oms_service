package com.mediamarkt.order.exception;

public class OrderNotFoundException extends RuntimeException {

  public OrderNotFoundException(Integer orderId) {
    super("Order " + orderId + " not found");
  }
}
