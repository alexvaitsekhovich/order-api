package com.alexvait.orderapi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "order_parts")
@Data
@EqualsAndHashCode(exclude = {"order"})
@NoArgsConstructor
public class OrderPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotNull(message = "Entity says: Order part item id is mandatory")
    @Column(name = "item_id")
    private Long itemId;

    @NotBlank(message = "Entity says: Order part item name is mandatory")
    @Size(min = 2, max = 255)
    @Column(name = "item_name")
    private String itemName;

    @NotNull(message = "Entity says: Order part item count is mandatory")
    @Min(1)
    @Column(name = "count")
    @Positive(message = "Entity says: Count of order part shall be positive")
    private Integer count;

    @NotNull(message = "Entity says: Order part item price is mandatory")
    @Column(name = "price")
    @Positive(message = "Entity says: Price of order part shall be positive")
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    private Order order;

    public OrderPart(Long itemId, String itemName, Integer count, Integer price) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.count = count;
        this.price = price;
    }
}
