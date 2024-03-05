package com.example.system.web.mappers;

import com.example.system.domain.request.Request;
import com.example.system.web.dto.request.RequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestMapper extends Mappable<Request, RequestDto> {
}
