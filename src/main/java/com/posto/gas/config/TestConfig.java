package com.posto.gas.config;

import com.posto.gas.entities.Fuel;
import com.posto.gas.entities.FuelDispenser;
import com.posto.gas.entities.Order;
import com.posto.gas.repositories.FuelDispenserRepository;
import com.posto.gas.repositories.FuelRepository;
import com.posto.gas.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FuelDispenserRepository fuelDispenserRepository;

    @Autowired
    private FuelRepository fuelRepository;


    @Override
    public void run(String... args) throws Exception {
        // Instanciando Fuels
        Fuel gasolina = new Fuel("Gasolina", new BigDecimal("5.99"));
        Fuel etanol = new Fuel("Etanol", new BigDecimal("4.59"));
        Fuel diesel = new Fuel("Diesel", new BigDecimal("6.29"));
        fuelRepository.saveAll(Arrays.asList(gasolina, etanol, diesel));

        // Instanciando FuelDispensers
        FuelDispenser bomba1 = new FuelDispenser("Bomba 1", gasolina);
        FuelDispenser bomba2 = new FuelDispenser("Bomba 2", etanol);
        FuelDispenser bomba3 = new FuelDispenser("Bomba 3", diesel);
        fuelDispenserRepository.saveAll(Arrays.asList(bomba1, bomba2, bomba3));

        // Instanciando Orders
        Order pedido1 = new Order(Instant.now(), new BigDecimal("299.50"), 50.00, bomba1);
        Order pedido2 = new Order(Instant.now(), new BigDecimal("137.70"), 30.00, bomba2);
        Order pedido3 = new Order(Instant.now(), new BigDecimal("251.60"), 40.00, bomba3);
        orderRepository.saveAll(Arrays.asList(pedido1, pedido2, pedido3));
    }
}
