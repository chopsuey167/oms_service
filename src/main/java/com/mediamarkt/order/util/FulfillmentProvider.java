package com.mediamarkt.order.util;

import java.util.Random;

public class FulfillmentProvider {

  private static final Random random = new Random();

  public String fulfillmentResult() {
    return random.nextBoolean() ? "Success" : "Failure";
  }
}
