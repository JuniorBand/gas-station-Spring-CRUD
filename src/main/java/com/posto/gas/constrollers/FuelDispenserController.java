package com.posto.gas.constrollers;

import com.posto.gas.entities.FuelDispenser;
import com.posto.gas.services.FuelDispenserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/fuel_dispensers")
public class FuelDispenserController {

    @Autowired
    private FuelDispenserService fuelDispenserService;

    @GetMapping
    public ResponseEntity<List<FuelDispenser>> findAll(){
        List<FuelDispenser> fuelDispensers = fuelDispenserService.findAll();
        return ResponseEntity.ok().body(fuelDispensers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuelDispenser> findById(@PathVariable Long id){
        FuelDispenser fuelDispenser = fuelDispenserService.findById(id);
        return ResponseEntity.ok().body(fuelDispenser);
    }

}
