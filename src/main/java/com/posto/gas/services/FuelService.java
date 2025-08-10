package com.posto.gas.services;

import com.posto.gas.entities.Fuel;
import com.posto.gas.entities.FuelDispenser;
import com.posto.gas.entities.dtos.FuelDTO;
import com.posto.gas.repositories.FuelRepository;
import com.posto.gas.services.exceptions.DataBaseException;
import com.posto.gas.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FuelService {

    @Autowired
    private FuelRepository fuelRepository;

    public List<Fuel> findAll() {
        return fuelRepository.findAll();
    }

    public Fuel findById(Long id) {
        Optional<Fuel> fuel = fuelRepository.findById(id);
        return fuel.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<Fuel> insertAll(List<FuelDTO> dtoList) {
        try {
            List<Fuel> newEntities = new ArrayList<>();
            for (FuelDTO fuelDTO : dtoList) {
                Fuel newEntity = new Fuel();
                if (fuelDTO.getName() != null) newEntity.setName(fuelDTO.getName());
                if (fuelDTO.getPrice() != null) newEntity.setPrice(fuelDTO.getPrice());
                newEntities.add(newEntity);
            }
            List<Fuel> savedFuels = fuelRepository.saveAll(newEntities);
            return savedFuels;
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }


    public Fuel insert(FuelDTO dto) {
        try {
            Fuel newEntity = new Fuel();
            if (dto.getName() != null) newEntity.setName(dto.getName());
            if (dto.getPrice() != null) newEntity.setPrice(dto.getPrice());
            return fuelRepository.save(newEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    public Fuel update(Long id, FuelDTO dto) {
        try {
            return updatePartial(id, dto);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }
    private Fuel updatePartial(Long id, FuelDTO dto) {
        Fuel entity = fuelRepository.getReferenceById(id);
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getPrice() != null) entity.setPrice(dto.getPrice());
        return fuelRepository.save(entity);
    }


    public void delete(Long id) {
        try {
            if (!fuelRepository.existsById(id)) {
                throw new ResourceNotFoundException(id);
            }
            fuelRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }


}
