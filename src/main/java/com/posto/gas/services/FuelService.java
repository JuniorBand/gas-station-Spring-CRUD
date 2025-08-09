package com.posto.gas.services;

import com.posto.gas.entities.Fuel;
import com.posto.gas.entities.FuelDispenser;
import com.posto.gas.entities.dtos.FuelUpdateDTO;
import com.posto.gas.repositories.FuelDispenserRepository;
import com.posto.gas.repositories.FuelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FuelService {

    @Autowired
    private FuelRepository fuelRepository;

    @Autowired
    private FuelDispenserRepository fuelDispenserRepository;

    public List<Fuel> findAll() {
        return fuelRepository.findAll();
    }

    public Fuel findById(Long id) {
        Optional<Fuel> fuel = fuelRepository.findById(id);
        return fuel.orElseThrow(() -> new RuntimeException());
    }

    public List<Fuel> insertAll(List<Fuel> list) {
        return fuelRepository.saveAll(list);
    }

    public Fuel insert(Fuel fuel) {
        return fuelRepository.save(fuel);
    }

    public Fuel update(Long id, FuelUpdateDTO dto) {
        try {
            return updatePartial(id, dto);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long id) {
        try {
            if (!fuelRepository.existsById(id)) {
                throw new RuntimeException();
            }
            fuelRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Fuel updatePartial(Long id, FuelUpdateDTO dto) {
        Fuel entity = fuelRepository.getReferenceById(id);
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getPrice() != null) entity.setPrice(dto.getPrice());
        if (dto.getFuelDispenserIds() != null) {
            // Busca os dispensers e associa ao Fuel
            Set<FuelDispenser> dispensers = new HashSet<>(fuelDispenserRepository.findAllById(dto.getFuelDispenserIds()));
            for (FuelDispenser dispenser : dispensers) {
                dispenser.setFuel(entity); // Atualiza o lado do dispenser
            }
            entity.setFuelDispensers(dispensers); // Crie este setter na entidade Fuel
        }
        return fuelRepository.save(entity);
    }

}
