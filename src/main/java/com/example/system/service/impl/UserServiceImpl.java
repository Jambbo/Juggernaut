package com.example.system.service.impl;

import com.example.system.domain.request.Request;
import com.example.system.domain.request.Status;
import com.example.system.domain.user.Role;
import com.example.system.domain.user.User;
import com.example.system.repository.UserRepository;
import com.example.system.service.RequestService;
import com.example.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RequestService requestService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->new IllegalStateException("User not found."));
    }

    @Override
    public User getById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()->new IllegalStateException("User not found"));
    }

    @Override
    @Transactional
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public User create(User user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new IllegalStateException("User already exists.");
        }
       if(!user.getPassword().equals(user.getPasswordConfirmation())){
           throw new IllegalStateException("Password and password confirmation do not match.");
       }
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       Set<Role> roles = Set.of(Role.USER);
       user.setRoles(roles);
       userRepository.save(user);
       return user;
    }


    @Override
    @Transactional(readOnly = true)
    public boolean isRequestOwner(Long userId, Long requestId) {
        return userRepository.isRequestOwner(userId,requestId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean isRequestDraft(Long userId, Long requestId){
        User user = getById(userId);
        Request request = requestService.getById(requestId);
        return request.getStatus().equals(Status.DRAFT) && user.getRequests().contains(request);
    }

}
