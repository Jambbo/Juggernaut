package com.example.system.service;

import com.example.system.domain.user.User;

public interface UserService {
    User getByUsername(String username);

    User getById(Long userId);

    User update(User user);

    User create(User user);

    boolean isRequestOwner(Long userId, Long requestId);

    void delete(Long id);

    boolean isRequestDraft(Long id, Long requestId);
}
