package com.mediamarkt.order.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

  @NotNull(message = "Product id cannot be empty or null")
  private Integer productId;
  @NotNull(message = "Quantity cannot be empty or null")
  @Min(value = 1, message = "Quantity should be greater than 0")
  private Integer quantity;
}
