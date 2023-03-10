package com.bms.springbootrabbitmq.core.dto;

import com.bms.springbootrabbitmq.core.model.BaseEntity;

import java.util.List;

public interface DtoConverter<D extends Dto, E extends BaseEntity> {
    D convert(E D);

     default List<D> convert(List<E> D) {
        return D.stream().map(this::convert).toList();
    }
}
