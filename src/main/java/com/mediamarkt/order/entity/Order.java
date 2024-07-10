package com.mediamarkt.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "orders")
public class Order {

  @Id
  @SequenceGenerator(
      name = "order_id_sequence",
      sequenceName = "order_id_sequence"
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "order_id_sequence"
  )
  private Integer id;
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JoinColumn(name = "customer_id", referencedColumnName = "id")
  @ToString.Exclude
  @JsonIgnore
  private Customer customer;
  private OrderState orderState;
  private String operationKey;
  private String fulfillmentResult;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "order_id", referencedColumnName = "id")
  @ToString.Exclude
  @JsonIgnore
  private List<OrderItem> orderItem;

}
