package com.posto.gas.repositories;

import com.posto.gas.entities.Fuel;
import com.posto.gas.entities.FuelDispenser;
import com.posto.gas.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
