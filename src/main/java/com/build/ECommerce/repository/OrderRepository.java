package com.build.ECommerce.repository;

import com.build.ECommerce.entity.Order;
import com.build.ECommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserEmail(String userEmail);

    List<Order> user(User user);

}
