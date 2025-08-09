package com.posto.gas.services;

import com.posto.gas.entities.FuelDispenser;
import com.posto.gas.entities.dtos.FuelDispenserUpdateDTO;
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
    private FuelDispenserRepository fuelDispenserRepository;

    public List<FuelDispenser> findAll() {
        return fuelDispenserRepository.findAll();
    }

    public FuelDispenser findById(Long id) {
        Optional<FuelDispenser> fuel = fuelDispenserRepository.findById(id);
        return fuel.orElseThrow(() -> new RuntimeException());
    }

    public List<FuelDispenser> insertAll(List<FuelDispenser> list) {
        List<FuelDispenser> savedFuelsDispensers = fuelDispenserRepository.saveAll(list);
        return savedFuelsDispensers;
    }

    public FuelDispenser insert(FuelDispenser fuelDispenser) {
        return fuelDispenserRepository.save(fuelDispenser);
    }

    public FuelDispenser update(Long id, FuelDispenserUpdateDTO dto) {
        try {
            return updatePartial(id, dto);
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

    private FuelDispenser updatePartial(Long id, FuelDispenserUpdateDTO dto) {
        FuelDispenser entity = fuelDispenserRepository.getReferenceById(id);
        if (dto.getFuel() != null) {
            entity.setFuel(dto.getFuel());
        }
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        return fuelDispenserRepository.save(entity);
    }


}
