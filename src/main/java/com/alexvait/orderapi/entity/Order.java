package com.alexvait.orderapi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {

    // AUTO - the strategy is provided by the DB
    // @GeneratedValue(strategy = GenerationType.AUTO)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Entity says: Order number is mandatory")
    @Column(name = "number")
    private String number;

    @NotNull(message = "Entity says: Order status is mandatory")
    @Column(name = "status_id")
    private int statusId = OrderStatus.CREATED.getId();

    @NotNull(message = "Entity says: Creation date is mandatory")
    @Column(name = "created_on", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime createdOn;

    @NotNull(message = "Entity says: Payment information is mandatory")
    private PaymentInformation paymentInformation;

    @NotNull(message = "Entity says: Array of order parts is mandatory")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderPart> orderParts = new ArrayList<>();

    @NotNull(message = "Entity says: Address is mandatory")
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.LAZY)
    private Address address;

    public Order(String number, PaymentInformation paymentInformation) {
        this.number = number;
        this.paymentInformation = paymentInformation;
    }

    public void addPart(OrderPart orderPart) {
        orderPart.setOrder(this);
        orderParts.add(orderPart);
    }

    public void setOrderParts(List<OrderPart> orderParts) {
        orderParts.forEach(this::addPart);
    }

    public void setAddress(Address address) {
        this.address = address;
        address.setOrder(this);
    }

    public void setStatusId(final int statusId) {
        this.statusId = statusId;
    }

    @PrePersist
    protected void onCreate() {
        createdOn = OffsetDateTime.now();
    }
}
