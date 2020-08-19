package com.alexvait.orderapi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(name = "number")
    private String number;

    @NotNull(message = "Entity says: Customer id is mandatory")
    @Column(name = "customer_id")
    private Long customerId;

    @NotNull(message = "Entity says: Retailer id is mandatory")
    @Column(name = "retailer_id")
    private Long retailerId;

    @NotNull(message = "Entity says: Order status is mandatory")
    @Column(name = "status_id")
    private int statusId = OrderStatus.CREATED.getId();

    @Column(name = "created_on", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    @CreationTimestamp
    private Timestamp createdOn;

    @NotNull(message = "Entity says: Payment information is mandatory")
    private PaymentInformation paymentInformation;

    @NotNull(message = "Entity says: Array of order parts is mandatory")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderPart> orderParts = new ArrayList<>();

    @NotNull(message = "Entity says: Address is mandatory")
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.LAZY)
    private Address address;

    public Order(String number, Long customerId, Long retailerId, PaymentInformation paymentInformation) {
        this.number = number;
        this.customerId = customerId;
        this.retailerId = retailerId;
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
        String date = new SimpleDateFormat("yyMMdd").format(new Date());
        number = date + RandomStringUtils.random(8, true, true);
    }
}
