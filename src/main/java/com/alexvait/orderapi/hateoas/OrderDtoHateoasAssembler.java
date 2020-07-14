package com.alexvait.orderapi.hateoas;

import com.alexvait.orderapi.controller.OrderController;
import com.alexvait.orderapi.dto.OrderDto;
import com.alexvait.orderapi.entity.OrderStatus;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.alexvait.orderapi.controller.OrderController.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderDtoHateoasAssembler implements RepresentationModelAssembler<OrderDto,
        EntityModel<OrderDto>> {

    @Override
    public EntityModel<OrderDto> toModel(OrderDto orderDto) {

        EntityModel<OrderDto> orderDtoEntityModel = EntityModel.of(
                orderDto,
                linkTo(methodOn(OrderController.class).getOrderById(orderDto.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).getAllOrders()).withRel("orders"));

        Arrays.stream(OrderStatus.values())
                .filter(orderStatus -> orderStatus.getId() != orderDto.getStatusId())
                .forEach(orderStatus ->
                        orderDtoEntityModel.add(
                                linkTo(methodOn(OrderController.class)
                                        .updateStatus(orderDto.getId(), orderStatus.getAction()))
                                        .withRel(orderStatus.getAction())
                        ));

        return orderDtoEntityModel;
    }

    @Override
    public CollectionModel<EntityModel<OrderDto>> toCollectionModel(Iterable<? extends OrderDto> orderDtos) {
        return CollectionModel.of(
                StreamSupport.stream(orderDtos.spliterator(), false)
                        .map(this::toModel)
                        .collect(Collectors.toList()),
                linkTo(methodOn(OrderController.class)
                        .getAllOrders(DEFAULT_PAGE_INT, DEFAULT_SIZE_INT, DEFAULT_DIRECTION, DEFAULT_SORT))
                        .withSelfRel()
        );
    }

    public CollectionModel<EntityModel<OrderDto>> toCollectionModelWithPagination(
            Iterable<? extends OrderDto> orderDtos, int page, int size, String direction, String order) {

        CollectionModel<EntityModel<OrderDto>> collectionModel = this.toCollectionModel(orderDtos);

        if (page > 0) {
            collectionModel.add(
                    linkTo(methodOn(OrderController.class)
                            .getAllOrders(page - 1, size, direction, order))
                            .withRel(IanaLinkRelations.PREVIOUS_VALUE)
            );
        }

        if (StreamSupport.stream(orderDtos.spliterator(), false).count() > 0) {
            collectionModel.add(
                    linkTo(methodOn(OrderController.class)
                            .getAllOrders(page + 1, size, direction, order))
                            .withRel(IanaLinkRelations.NEXT_VALUE)
            );
        }

        return collectionModel;
    }

}
