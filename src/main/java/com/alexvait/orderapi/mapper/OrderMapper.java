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

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    AddressDto addressToAddressDto(Address address);

    Address addressDtoToAddress(AddressDto addressDto);

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

    default OffsetDateTime timestampToOffsetDateTime(Timestamp ts) {
        if (ts != null) {
            return OffsetDateTime.of(ts.toLocalDateTime().getYear(), ts.toLocalDateTime().getMonthValue(),
                    ts.toLocalDateTime().getDayOfMonth(), ts.toLocalDateTime().getHour(), ts.toLocalDateTime().getMinute(),
                    ts.toLocalDateTime().getSecond(), ts.toLocalDateTime().getNano(), ZoneOffset.UTC);
        } else {
            return null;
        }
    }

    default Timestamp offsetDateTimeToTimestamp(OffsetDateTime offsetDateTime) {
        if (offsetDateTime != null) {
            return Timestamp.valueOf(offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
        } else {
            return null;
        }
    }
}
