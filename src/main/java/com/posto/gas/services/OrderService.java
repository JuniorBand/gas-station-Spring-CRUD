package com.posto.gas.services;

import com.posto.gas.entities.Order;
import com.posto.gas.entities.dtos.OrderUpdateDTO;
import com.posto.gas.repositories.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElseThrow(() -> new RuntimeException());
    }

    public List<Order> insertAll(List<Order> list) {
        List<Order> savedOrders = orderRepository.saveAll(list);
        return savedOrders;
    }

    public Order insert(Order order) {
        return orderRepository.save(order);
    }

    public Order update(Long id, OrderUpdateDTO dto) {
        try {
            return updatePartial(id, dto);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long id) {
        try {
            if (!orderRepository.existsById(id)) {
                throw new RuntimeException();
            }
            orderRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Order updatePartial(Long id, OrderUpdateDTO dto) {
        Order entity = orderRepository.getReferenceById(id);
        if (dto.getMoment() != null) entity.setMoment(dto.getMoment());
        if (dto.getVolume() != null) entity.setVolume(dto.getVolume());
        if (dto.getTotalValue() != null) entity.setTotalValue(dto.getTotalValue());
        if (dto.getFuelDispenser() != null) {
            entity.setFuelDispenser(dto.getFuelDispenser());
        }
        return orderRepository.save(entity);
    }


}
