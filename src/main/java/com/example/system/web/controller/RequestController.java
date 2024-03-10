package com.example.system.web.controller;

import com.example.system.domain.request.Request;
import com.example.system.domain.request.Status;
import com.example.system.domain.user.User;
import com.example.system.service.UserService;
import org.springframework.http.HttpHeaders;
import com.example.system.service.RequestService;
import com.example.system.web.dto.request.RequestDto;
import com.example.system.web.dto.validation.OnUpdate;
import com.example.system.web.mappers.RequestMapper;
import com.example.system.web.security.JwtEntity;
import com.example.system.web.security.JwtTokenProvider;
import com.example.system.web.security.expression.CustomSecurityExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/requests")
@RequiredArgsConstructor
public class RequestController {
    private final UserService userService;
    private final RequestService requestService;
    private final RequestMapper requestMapper;
    private final CustomSecurityExpression customSecurityExpression;
    private final JwtTokenProvider jwtTokenProvider;
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
        Request request = requestService.getRequestById(id);
        return requestMapper.toDto(request);
    }
    @GetMapping("/operator/{id}")
    @PreAuthorize("@customSecurityExpression.hasAnyRole(T(com.example.system.domain.user.Role).OPERATOR)")
    public RequestDto getByIdOp(@PathVariable Long id){
        Request request = requestService.getRequestById(id);
        return requestMapper.toDto(request);
    }

    @GetMapping("/all")
    @PreAuthorize("@customSecurityExpression.hasAnyRole(T(com.example.system.domain.user.Role).OPERATOR)")
    public ResponseEntity<Page<RequestDto>> getAllRequests(@RequestParam(defaultValue = "createdAt") String sortBy,
                                                        @RequestParam(defaultValue = "asc") String order,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "5") int size) {
        Page<RequestDto> requests = requestService.findAllRequestsSortedByDate(sortBy, order, page, size);
        return ResponseEntity.ok(requests);
    }
    @GetMapping("/user/all")
    @PreAuthorize("@customSecurityExpression.hasAnyRole(T(com.example.system.domain.user.Role).USER)")
    public ResponseEntity<Page<RequestDto>> getAllRequestsForUser(
                                                                  @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorizationHeader,
                                                                  @RequestParam(defaultValue = "createdAt") String sortBy,
                                                                  @RequestParam(defaultValue = "asc") String order,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "5") int size)
    {
        String token = authorizationHeader.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.getByUsername(username);


        Page<RequestDto> userRequests = requestService.findRequestsCreatedByUser(user.getId(), sortBy, order, page, size);
        return ResponseEntity.ok(userRequests);
    }
    @GetMapping("/search")
    @PreAuthorize("@customSecurityExpression.hasAnyRole(T(com.example.system.domain.user.Role).OPERATOR)")
    public ResponseEntity<Page<RequestDto>> getRequestsByCreatedByAndStatus(
            @RequestParam String createdBy,
            @RequestParam (defaultValue = "SENT")Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<RequestDto> requests = requestService.findByCreatedByContainingIgnoreCaseAndStatus(createdBy,status, page, size);
        return ResponseEntity.ok(requests);
    }

    @PutMapping("/request/{id}/accept")
    @PreAuthorize("@customSecurityExpression.hasAnyRole(T(com.example.system.domain.user.Role).OPERATOR)")
    public void acceptRequest(@PathVariable Long id) {
        requestService.acceptRequest(id);
    }
    @PutMapping("/request/{id}/reject")
    @PreAuthorize("@customSecurityExpression.hasAnyRole(T(com.example.system.domain.user.Role).OPERATOR)")
    public void rejectRequest(@PathVariable Long id) {
        requestService.rejectRequest(id);
    }
    @PostMapping("/send-to-operator")
    @PreAuthorize("@customSecurityExpression.hasAnyRole(T(com.example.system.domain.user.Role).USER) and !@customSecurityExpression.hasAnyRole(T(com.example.system.domain.user.Role).OPERATOR)")
    public RequestDto sendRequestToOperator(@RequestBody RequestDto requestDto) {
        JwtEntity currentUser = customSecurityExpression.getPrincipal();
        Long id = currentUser.getId();
        Request request = requestMapper.toEntity(requestDto);
        Request sentRequest = requestService.sendRequestToOperator(request,id);
        return requestMapper.toDto(sentRequest);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessRequest(#id)")
    public void deleteById(@PathVariable Long id){
        requestService.delete(id);
    }
}
