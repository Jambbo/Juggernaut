package com.example.system.web.controller;

import com.example.system.domain.request.Request;
import com.example.system.service.RequestService;
import com.example.system.web.dto.request.RequestDto;
import com.example.system.web.dto.validation.OnUpdate;
import com.example.system.web.mappers.RequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;
    private final RequestMapper requestMapper;

    @PutMapping
    @PreAuthorize("@customSecurityExpression.canAccessRequest(#dto.id)")
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
    @GetMapping("/getreq")
    public ResponseEntity<List<Request>> getRequests(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {

        List<Request> requests = requestService.getRequests(pageNo, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessRequest(#id)")
    public void deleteById(@PathVariable Long id){
        requestService.delete(id);
    }
}
