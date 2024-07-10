package com.mediamarkt.order.dto;

import com.mediamarkt.order.entity.OrderState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentConfirmationResponseDto {

  private String orderId;
  private OrderState orderState;
}
