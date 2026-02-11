package com.paulopsms.psfood.order.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    private Integer quantity;

    private String description;

    @ManyToOne(optional = false)
    private Order order;

    public OrderItem(Integer quantity, String description, Order order) {
        this.quantity = quantity;
        this.description = description;
        this.order = order;
    }
}
