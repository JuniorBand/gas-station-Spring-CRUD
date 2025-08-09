package com.posto.gas.services;

import com.posto.gas.entities.Fuel;
import com.posto.gas.entities.FuelDispenser;
import com.posto.gas.entities.dtos.FuelDispenserDTO;
import com.posto.gas.repositories.FuelDispenserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuelDispenserService {

    @Autowired
    private FuelService fuelService;

    @Autowired
    private FuelDispenserRepository fuelDispenserRepository;

    public List<FuelDispenser> findAll() {
        return fuelDispenserRepository.findAll();
    }

    public FuelDispenser findById(Long id) {
        Optional<FuelDispenser> fuelDispenser = fuelDispenserRepository.findById(id);
        return fuelDispenser.orElseThrow(() -> new RuntimeException());
    }

    public List<FuelDispenser> insertAll(List<FuelDispenser> list) {
        List<FuelDispenser> savedFuelsDispensers = fuelDispenserRepository.saveAll(list);
        return savedFuelsDispensers;
    }

    public FuelDispenser insert(FuelDispenserDTO fuelDispenserDTO) {
        FuelDispenser newEntity = new FuelDispenser();
        if (fuelDispenserDTO.getName() != null) newEntity.setName(fuelDispenserDTO.getName());
        if (fuelDispenserDTO.getFuel() != null) {
            Fuel fuel = fuelService.findById(fuelDispenserDTO.getFuel());
            newEntity.setFuel(fuel);
        }
        return fuelDispenserRepository.save(newEntity);
    }

    public FuelDispenser update(Long id, FuelDispenserDTO updateFuelDispenserDTO) {
        try {
            return updatePartial(id, updateFuelDispenserDTO);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long id) {
        try {
            if (!fuelDispenserRepository.existsById(id)) {
                throw new RuntimeException();
            }
            fuelDispenserRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(e.getMessage());
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
                .orElseThrow(() -> new EntityNotFoundException("FuelDispenser not found with id: " + id));
        return fuelDispenserRepository.save(newEntity);
    }


}
