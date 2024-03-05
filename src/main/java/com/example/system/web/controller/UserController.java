package com.example.system.web.controller;

import com.example.system.domain.request.Request;
import com.example.system.domain.user.User;
import com.example.system.service.RequestService;
import com.example.system.service.UserService;
import com.example.system.web.dto.request.RequestDto;
import com.example.system.web.dto.user.UserDto;
import com.example.system.web.dto.validation.OnCreate;
import com.example.system.web.dto.validation.OnUpdate;
import com.example.system.web.mappers.RequestMapper;
import com.example.system.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final RequestService requestService;
    private final UserMapper userMapper;
    private final RequestMapper requestMapper;

    @PutMapping
    @PreAuthorize("@customSecurityExpression.canAccessUser(#dto.id)")
    public UserDto update(@Validated(OnUpdate.class)@RequestBody UserDto dto){
        User user = userMapper.toEntity(dto);
        User updatedUser = userService.update(user);
        return userMapper.toDto(updatedUser);
    }
    @GetMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public UserDto getById(@PathVariable Long id){
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public void deleteById(@PathVariable Long id){
        userService.delete(id);
    }

    @GetMapping("/{id}/requests")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public List<RequestDto> getRequestByUserId(@PathVariable Long id){
        List<Request> requests = requestService.getAllByUserId(id);
        return requestMapper.toDto(requests);
    }
    @PostMapping("/{id}/requests")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public RequestDto createRequest(
            @PathVariable Long id, @Validated(OnCreate.class)
            @RequestBody RequestDto dto
    ){
        dto.setCreatedAt(LocalDateTime.now());
        Request request = requestMapper.toEntity(dto);
        Request createdRequest = requestService.createRequest(request,id);
        return requestMapper.toDto(createdRequest);
    }
    @PostMapping("/{id}/drafts")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public RequestDto createDraft(
            @PathVariable Long id, @Validated(OnCreate.class)
            @RequestBody RequestDto dto
    ){
        dto.setCreatedAt(LocalDateTime.now());
        Request requestDraft = requestMapper.toEntity(dto);
        Request createdRequestDraft = requestService.createDraftRequest(requestDraft,id);
        return requestMapper.toDto(createdRequestDraft);
    }
}
