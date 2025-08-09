package com.posto.gas.entities.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.posto.gas.entities.Fuel;
import com.posto.gas.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FuelDispenserUpdateDTO {

    private String name;
    private Fuel fuel;
    private Set<Order> orders;

}