package com.starbux.starbuxbackend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Currency;

@Data
@Entity
@Table(name="DRINK")
public class Drink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    private Currency currency;
}
