package com.alexvait.orderapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "order_address")
@Data
@EqualsAndHashCode(exclude = {"order"})
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Entity says: City is mandatory")
    @Size(max = 255)
    @Column(name = "city")
    private String city;

    @NotBlank(message = "Entity says: Zip code is mandatory, between 3 and 10 digits")
    @Size(min=3, max = 10, message = "Zip code size must be between 3 and 10")
    @Column(name = "zip")
    private String zip;

    @NotBlank(message = "Entity says: Street is mandatory")
    @Column(name = "street")
    private String street;

    @Column(name = "nr")
    private String nr;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    @ToString.Exclude
    private Order order;

    public Address(String city, String zip, String street, String nr) {
        this.city = city;
        this.zip = zip;
        this.street = street;
        this.nr = nr;
    }
}
