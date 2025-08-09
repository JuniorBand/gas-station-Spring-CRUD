package com.posto.gas.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.posto.gas.services.FuelDispenserService;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@NoArgsConstructor
@Data
@Entity
@Table(name = "tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    @Column(nullable = false)
    private Instant moment;

    @Column(nullable = false)
    private BigDecimal totalValue;

    @Column(nullable = false)
    private Double volume;

    @ManyToOne
    @JoinColumn(name = "fuel_dispenser")
    private FuelDispenser fuelDispenser;


    public Order(Instant moment, BigDecimal totalValue, Double volume, FuelDispenser fuelDispenser) {
        this.moment = moment;
        this.totalValue = totalValue;
        this.volume = volume;
        this.fuelDispenser = fuelDispenser;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
