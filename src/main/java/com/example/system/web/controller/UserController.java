package com.example.system.web.controller;

import com.example.system.domain.request.Request;
import com.example.system.domain.user.Role;
import com.example.system.domain.user.User;
import com.example.system.service.RequestService;
import com.example.system.service.UserService;
import com.example.system.web.dto.request.RequestDto;
import com.example.system.web.dto.user.UserDto;
import com.example.system.web.dto.validation.OnCreate;
import com.example.system.web.dto.validation.OnUpdate;
import com.example.system.web.mappers.RequestMapper;
import com.example.system.web.mappers.UserMapper;
import com.example.system.web.security.JwtEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {

    private final UserService userService;
    private final RequestService requestService;
    private final UserMapper userMapper;
    private final RequestMapper requestMapper;

    @PutMapping
    @PreAuthorize("@customSecurityExpression.canAccessUser(#dto.id) and @customSecurityExpression.hasAnyRole(T(com.example.system.domain.user.Role).USER)")
    public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto dto) {
        User user = userMapper.toEntity(dto);
        User updatedUser = userService.update(user);
        return userMapper.toDto(updatedUser);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id) and @customSecurityExpression.hasAnyRole(T(com.example.system.domain.user.Role).USER)")
    public UserDto getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @PutMapping("/{userId}/roles")
    @PreAuthorize("@customSecurityExpression.hasAnyRole(T(com.example.system.domain.user.Role).ADMIN)")
    public ResponseEntity<Void> assignRolesToUser(@PathVariable Long userId, @RequestParam Set<Role> roles) {
        userService.assignRoles(userId, roles);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



    @DeleteMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id) and @customSecurityExpression.hasAnyRole(T(com.example.system.domain.user.Role).USER)")
    public void deleteById(@PathVariable Long id) {
        userService.delete(id);
    }

    //    @GetMapping("/{id}/requests")
//    @PostMapping()
//    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
//    public List<RequestDto> getRequestByUserId(@PathVariable Long id){
//        List<Request> requests = requestService.getAllByUserId(id);
//        return requestMapper.toDto(requests);
//    }
    @PostMapping("/{id}/requests")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id) and @customSecurityExpression.hasAnyRole(T(com.example.system.domain.user.Role).USER)")
    public RequestDto createRequest(
            @PathVariable Long id, @Validated(OnCreate.class)
    @RequestBody RequestDto dto
    ) {
        dto.setCreatedAt(LocalDateTime.now());
        Request request = requestMapper.toEntity(dto);

        Request createdRequest = requestService.addRequestToUser(request, id);
        return requestMapper.toDto(createdRequest);
    }

    @PostMapping("/{id}/drafts")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id) and @customSecurityExpression.hasAnyRole(T(com.example.system.domain.user.Role).USER)")
    public RequestDto createDraft(
            @PathVariable Long id, @Validated(OnCreate.class)
    @RequestBody RequestDto dto
    ) {
        dto.setCreatedAt(LocalDateTime.now());
        Request request = requestMapper.toEntity(dto);
        Request createdRequest = requestService.addRequestToUser(request, id);
        return requestMapper.toDto(createdRequest);

    }

    @GetMapping("/all-users")
    @PreAuthorize("@customSecurityExpression.hasAnyRole(T(com.example.system.domain.user.Role).ADMIN)")
    public List<UserDto> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return userMapper.toDto(users);
    }
}
