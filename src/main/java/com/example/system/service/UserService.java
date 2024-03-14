package com.example.system.service;

import com.example.system.domain.user.Role;
import com.example.system.domain.user.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    User getByUsername(String username);
    List<User> getAllUsers();

    User getById(Long userId);

    User update(User user);
    void assignRoles(Long userId, Set<Role> roles);

    User create(User user);

    boolean isRequestOwner(Long userId, Long requestId);

    void delete(Long id);

    User getByAuthConfirmCode(String authConfirmCode);
    boolean isRequestDraft(Long id, Long requestId);
}
