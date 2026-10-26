package com.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
