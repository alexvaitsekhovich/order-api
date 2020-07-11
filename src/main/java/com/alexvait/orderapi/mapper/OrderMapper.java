package com.alexvait.orderapi.mapper;

import com.alexvait.orderapi.dto.AddressDto;
import com.alexvait.orderapi.dto.OrderDto;
import com.alexvait.orderapi.dto.OrderPartDto;
import com.alexvait.orderapi.entity.Address;
import com.alexvait.orderapi.entity.Order;
import com.alexvait.orderapi.entity.OrderPart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    AddressDto addressToAddressDto(Address address);

    Address addressDtoToAddress(AddressDto addressDto);

/*
    PaymentInformationDto paymentInformationToPaymentInformationDto(PaymentInformation paymentInformation);
    PaymentInformation paymentInformationDtoToPaymentInformation(PaymentInformationDto paymentInformationDto);
*/

    @Mapping(target = "paymentId", source = "paymentInformation.paymentId")
    @Mapping(target = "amount", source = "paymentInformation.amount")
    @Mapping(target = "discountAmount", source = "paymentInformation.discountAmount")
    OrderDto orderToOrderDto(Order order);

    @Mapping(source = "paymentId", target = "paymentInformation.paymentId")
    @Mapping(source = "amount", target = "paymentInformation.amount")
    @Mapping(source = "discountAmount", target = "paymentInformation.discountAmount")
    Order orderDtoToOrder(OrderDto orderDto);

    OrderPartDto orderPartToOrderPartDto(OrderPart orderPart);

    OrderPart orderPartDtoToOrderPart(OrderPartDto orderPartDto);
}
