package com.posto.gas.entities;

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

    @Column(nullable = false)
    private Instant moment;

    @Column(nullable = false)
    private BigDecimal totalValue;

    @Column(nullable = false)
    private Long volume;

    @ManyToOne
    @JoinColumn(name = "fuel_dispenser")
    private FuelDispenser fuelDispenser;


    public Order(Instant moment, BigDecimal totalValue, Long volume, FuelDispenser fuelDispenser) {
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
