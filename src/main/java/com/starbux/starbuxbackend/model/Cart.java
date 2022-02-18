package com.starbux.starbuxbackend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="CART")
public class Cart {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = true, updatable = false)
    @JsonManagedReference
    private User user;


}
