package com.posto.gas.entities.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.posto.gas.entities.FuelDispenser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderUpdateDTO {

    private Instant moment;
    private BigDecimal totalValue;
    private Long volume;
    private FuelDispenser fuelDispenser;

}