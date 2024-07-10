package com.mediamarkt.order.repository;

import com.mediamarkt.order.entity.Order;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

  Optional<Order> findByOperationKey(String operationKey);
}
