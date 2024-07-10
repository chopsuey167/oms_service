package com.mediamarkt.order.dto;

import com.mediamarkt.order.entity.OrderState;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreationResponseDto {

  private Integer id;
  private Integer customerId;
  private OrderState orderState;
  private List<OrderItemDto> items;

}
