package com.posto.gas.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Data
@Entity
@Table(name = "fuel_dispenser")
public class FuelDispenser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @JsonIgnoreProperties({"fuelDispenserIds"})
    @ManyToOne
    @JoinColumn(name = "fuel")
    private Fuel fuel;

    @JsonIgnore
    @OneToMany(mappedBy = "fuelDispenser")
    private Set<Order> orders = new HashSet<>();

    public FuelDispenser(String name, Fuel fuel) {
        this.name = name;
        this.fuel = fuel;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FuelDispenser that = (FuelDispenser) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(fuel, that.fuel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, fuel);
    }
}
