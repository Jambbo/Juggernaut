package com.example.system.web.controller;

import com.example.system.domain.user.User;
import com.example.system.service.EmailService;
import com.example.system.service.UserService;
import com.example.system.service.impl.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emailConfirmation")
@RequiredArgsConstructor
public class EmailController {

    private final UserService userService;

    @GetMapping("/{confirmCode}")
    public User confirm(@PathVariable("confirmCode") String authConfirmCode) {
        User user = userService.getByAuthConfirmCode(authConfirmCode);
        if (user.getAuthConfirmCode().equals(authConfirmCode)) {
            user.setConfirm(true);
            userService.update(user);
        } else return null;
        return user;
    }



}
