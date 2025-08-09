package com.posto.gas.services;

import com.posto.gas.entities.FuelDispenser;
import com.posto.gas.entities.Order;
import com.posto.gas.entities.dtos.OrderDTO;
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
    private FuelDispenserService fuelDispenserService;

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
//        List<Order> entity = orderRepository.findAll();
//        if (dtolist.getMoment() != null) entity.setMoment(dtolist.getMoment());
//        if (dtolist.getVolume() != null) entity.setVolume(dtolist.getVolume());
//        if (dtolist.getTotalValue() != null) entity.setTotalValue(dtolist.getTotalValue());
//        if (dtolist.getFuelDispenser() != null) {
//            FuelDispenser newFuelDispenser = fuelDispenserService.findById(dtolist.getFuelDispenser());
//            entity.get().setFuelDispenser(newFuelDispenser);
//        }
//        Order newEntity = entity
//                .orElseThrow(() -> new EntityNotFoundException("FuelDispenser not found with id: " + id));
//        return orderRepository.save(newEntity);
        List<Order> savedOrders = orderRepository.saveAll(list);
        return savedOrders;
    }

    public Order insert(OrderDTO dto) {
        Order newEntity = new Order();
        if (dto.getMoment() != null) newEntity.setMoment(dto.getMoment());
        if (dto.getVolume() != null) newEntity.setVolume(dto.getVolume());
        if (dto.getTotalValue() != null) newEntity.setTotalValue(dto.getTotalValue());
        if (dto.getFuelDispenser() != null) {
            FuelDispenser fuelDispenser = fuelDispenserService.findById(dto.getFuelDispenser());
            newEntity.setFuelDispenser(fuelDispenser);
        }
       return orderRepository.save(newEntity);
    }

    public Order update(Long id, OrderDTO dto) {
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


}
