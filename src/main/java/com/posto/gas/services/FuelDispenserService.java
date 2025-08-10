package com.posto.gas.services;

import com.posto.gas.entities.Fuel;
import com.posto.gas.entities.FuelDispenser;
import com.posto.gas.entities.Order;
import com.posto.gas.entities.dtos.FuelDispenserDTO;
import com.posto.gas.repositories.FuelDispenserRepository;
import com.posto.gas.services.exceptions.DataBaseException;
import com.posto.gas.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FuelDispenserService {

    @Autowired
    private FuelService fuelService;

    @Autowired
    private FuelDispenserRepository fuelDispenserRepository;

    public List<FuelDispenser> findAll() {
        return fuelDispenserRepository.findAll();
    }

    public List<FuelDispenser> findAllById(Set<Long> ids) {
        return fuelDispenserRepository.findAllById(ids);
    }

    public FuelDispenser findById(Long id) {
        Optional<FuelDispenser> fuelDispenser = fuelDispenserRepository.findById(id);
        return fuelDispenser.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<FuelDispenser> insertAll(List<FuelDispenserDTO> dtoList) {
        try{
            List<FuelDispenser> newEntities = new ArrayList<>();
            for (FuelDispenserDTO fuelDispenserDTO : dtoList) {
                FuelDispenser newEntity = new FuelDispenser();
                if (fuelDispenserDTO.getName() != null) newEntity.setName(fuelDispenserDTO.getName());
                if (fuelDispenserDTO.getFuel() != null) {
                    Fuel fuel = fuelService.findById(fuelDispenserDTO.getFuel());
                    newEntity.setFuel(fuel);
                }
                newEntities.add(newEntity);
            }
            List<FuelDispenser> savedFuelDispensers = fuelDispenserRepository.saveAll(newEntities);
            return savedFuelDispensers;
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    public FuelDispenser insert(FuelDispenserDTO fuelDispenserDTO) {
        try {
            FuelDispenser newEntity = new FuelDispenser();
            if (fuelDispenserDTO.getName() != null) newEntity.setName(fuelDispenserDTO.getName());
            if (fuelDispenserDTO.getFuel() != null) {
                Fuel fuel = fuelService.findById(fuelDispenserDTO.getFuel());
                newEntity.setFuel(fuel);
            }
            return fuelDispenserRepository.save(newEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    public FuelDispenser update(Long id, FuelDispenserDTO updateFuelDispenserDTO) {
        try {
            return updatePartial(id, updateFuelDispenserDTO);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private FuelDispenser updatePartial(Long id, FuelDispenserDTO updateFuelDispenserDTO) {
        Optional<FuelDispenser> entity = fuelDispenserRepository.findById(id);
        if (updateFuelDispenserDTO.getFuel() != null) {
            Fuel newFuel = fuelService.findById(updateFuelDispenserDTO.getFuel());
            entity.get().setFuel(newFuel);
        }
        if (updateFuelDispenserDTO.getName() != null) {
            entity.get().setName(updateFuelDispenserDTO.getName());
        }
        FuelDispenser newEntity = entity
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return fuelDispenserRepository.save(newEntity);
    }

    public void delete(Long id) {
        try {
            if (!fuelDispenserRepository.existsById(id)) {
                throw new ResourceNotFoundException(id);
            }
            fuelDispenserRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

}
