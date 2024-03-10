package com.example.system.web.security.expression;

import com.example.system.domain.request.Status;
import com.example.system.domain.user.Role;
import com.example.system.service.RequestService;
import com.example.system.service.UserService;
import com.example.system.web.security.JwtEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;


//@Component("cse")
@Service("customSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression{

    private final UserService userService;

    private final RequestService requestService;

    public boolean canAccessUser(Long id){

        JwtEntity user = getPrincipal();
        Long userId = user.getId();

        return userId.equals(id) || hasAnyRole(Role.ADMIN);
    }



    public boolean hasAnyRole( Role... roles){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for(Role role: roles){
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
            if(authentication.getAuthorities().contains(authority)){
                return true;
            }
        }
        return false;
    }

    public boolean canAccessRequest(Long requestId){
        JwtEntity user = getPrincipal();
        Long userId = user.getId();
        return userService.isRequestOwner(userId, requestId) && hasAnyRole(Role.USER);
    }
    public boolean canAccessUserList() {
        JwtEntity user = getPrincipal();
        return hasAnyRole(Role.ADMIN) || hasAnyRole(Role.OPERATOR);
    }

    public boolean canEditRequest(Long requestId){
        JwtEntity user = getPrincipal();
        return (userService.isRequestOwner(user.getId(), requestId))&&userService.isRequestDraft(user.getId(),requestId);
    }


    public JwtEntity getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (JwtEntity) authentication.getPrincipal();
    }


}
