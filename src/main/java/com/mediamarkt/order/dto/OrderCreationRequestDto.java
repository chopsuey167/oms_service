package com.mediamarkt.order.dto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreationRequestDto {

  @NotBlank(message = "Operation key cannot be empty or null")
  private String operationKey;
  @NotNull(message = "Customer id cannot be empty or null")
  private Integer customerId;
  @NotNull(message = "Items cannot be null")
  private @Valid List<OrderItemDto> items;

}
