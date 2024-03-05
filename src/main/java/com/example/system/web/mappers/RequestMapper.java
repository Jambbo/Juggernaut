package com.example.system.web.mappers;

import com.example.system.domain.request.Request;
import com.example.system.web.dto.request.RequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RequestMapper extends Mappable<Request, RequestDto> {

    @Mapping(target = "phoneNumber", source = "dto.phoneNumber")
//    @Mapping(target = "user", source = "dto.user")
    @Override
    Request toEntity(RequestDto dto);

}
