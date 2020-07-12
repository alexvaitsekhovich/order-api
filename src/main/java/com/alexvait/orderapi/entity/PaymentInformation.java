package com.alexvait.orderapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInformation {

    @NotBlank(message = "Entity says: Payment id is mandatory")
    @Column(name = "payment_id")
    private Integer paymentId;

    @NotBlank(message = "Entity says: Amount is mandatory")
    @Column(name = "amount")
    private Integer amount;

    @Column(name = "discount_amount")
    private Integer discountAmount;
}
