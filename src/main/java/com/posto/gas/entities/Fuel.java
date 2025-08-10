package com.posto.gas.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
@Entity
@Table(name = "fuel")
public class Fuel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @JsonIgnore
    @OneToMany(mappedBy = "fuel")
    private Set<FuelDispenser> fuelDispensers = new HashSet<>();

    public Fuel(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    @JsonProperty("fuelDispenserIds")
    public Set<Long> getFuelDispenserIds() {
        if (fuelDispensers == null) return Set.of();
        return fuelDispensers.stream()
                .map(FuelDispenser::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Fuel fuel = (Fuel) o;
        return Objects.equals(id, fuel.id) && Objects.equals(name, fuel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
