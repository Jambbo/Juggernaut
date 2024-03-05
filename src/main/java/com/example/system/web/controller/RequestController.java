package com.example.system.web.controller;

import com.example.system.domain.request.Request;
import com.example.system.service.RequestService;
import com.example.system.web.dto.request.RequestDto;
import com.example.system.web.dto.validation.OnUpdate;
import com.example.system.web.mappers.RequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;
    private final RequestMapper requestMapper;

    @PutMapping
    @PreAuthorize("@customSecurityExpression.canAccessUser(#dto.id)")
    public RequestDto update(@Validated(OnUpdate.class)
                             @RequestBody RequestDto dto){
        Request request = requestMapper.toEntity(dto);
        Request updatedRequest = requestService.update(request);
        return requestMapper.toDto(updatedRequest);
    }
    @GetMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessRequest(#id)")
    public RequestDto getById(@PathVariable Long id){
        Request request = requestService.getById(id);
        return requestMapper.toDto(request);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessRequest(#id)")
    public void deleteById(@PathVariable Long id){
        requestService.delete(id);
    }
}
