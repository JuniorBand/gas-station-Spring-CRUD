package com.posto.gas.services;

import com.posto.gas.entities.Fuel;
import com.posto.gas.entities.FuelDispenser;
import com.posto.gas.entities.Order;
import com.posto.gas.entities.dtos.OrderDTO;
import com.posto.gas.repositories.OrderRepository;
import com.posto.gas.services.exceptions.DateException;
import com.posto.gas.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private FuelService fuelService;

    @Autowired
    private FuelDispenserService fuelDispenserService;

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<Order> insertAll(List<OrderDTO> dtoList) {
        try {
            List<Order> newEntities = new ArrayList<>();
            for (OrderDTO orderDTO : dtoList) {
                Order newEntity = new Order();
                if (orderDTO.getMoment() != null) newEntity.setMoment(orderDTO.getMoment());
                if (orderDTO.getVolume() != null) newEntity.setVolume(orderDTO.getVolume());
                if (orderDTO.getTotalValue() != null) newEntity.setTotalValue(orderDTO.getTotalValue());
                if (orderDTO.getFuelDispenser() != null) {
                    FuelDispenser FuelDispenser = fuelDispenserService.findById(orderDTO.getFuelDispenser());
                    newEntity.setFuelDispenser(FuelDispenser);
                }
                newEntities.add(newEntity);
            }
            List<Order> savedOrders = orderRepository.saveAll(newEntities);
            return savedOrders;
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Order insert(OrderDTO dto) {
        try {
            Order newEntity = new Order();
            if (dto.getMoment() != null) newEntity.setMoment(dto.getMoment());
            if (dto.getVolume() != null) newEntity.setVolume(dto.getVolume());
            if (dto.getTotalValue() != null) newEntity.setTotalValue(dto.getTotalValue());
            if (dto.getFuelDispenser() != null) {
                FuelDispenser fuelDispenser = fuelDispenserService.findById(dto.getFuelDispenser());
                newEntity.setFuelDispenser(fuelDispenser);
            }
            return orderRepository.save(newEntity);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Order update(Long id, OrderDTO dto) {
        try {
            return updatePartial(id, dto);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private Order updatePartial(Long id, OrderDTO dto) {
        Optional<Order> entity = orderRepository.findById(id);
        if (dto.getMoment() != null) entity.get().setMoment(dto.getMoment());
        if (dto.getVolume() != null) entity.get().setVolume(dto.getVolume());
        if (dto.getTotalValue() != null) entity.get().setTotalValue(dto.getTotalValue());
        if (dto.getFuelDispenser() != null) {
            FuelDispenser newFuelDispenser = fuelDispenserService.findById(dto.getFuelDispenser());
            entity.get().setFuelDispenser(newFuelDispenser);
        }
        Order newEntity = entity
                .orElseThrow(() -> new EntityNotFoundException("FuelDispenser not found with id: " + id));
        return orderRepository.save(newEntity);
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

    public void deleteAllByFuel(Long fuelId) {
        Fuel fuel = fuelService.findById(fuelId);
        for(FuelDispenser fuelDispenser : fuelDispenserService.findAll()) {
            if (fuelDispenser.getFuel().equals(fuel)) {
                deleteAllByFuelDispenser(fuelDispenser);
                fuelDispenserService.delete(fuelDispenser.getId());
            }
        }
    }

    public void deleteAllByFuelDispenser(FuelDispenser fuelDispenser) {
        for (Order order : orderRepository.findAll()) {
            if (order.getFuelDispenser().equals(fuelDispenser)) {
                delete(order.getId());
            }
        }
    }


}
