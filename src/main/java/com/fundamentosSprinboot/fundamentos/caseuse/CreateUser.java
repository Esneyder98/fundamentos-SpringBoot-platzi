package com.fundamentosSprinboot.fundamentos.caseuse;

import com.fundamentosSprinboot.fundamentos.entity.Users;
import com.fundamentosSprinboot.fundamentos.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class CreateUser {
    private UserService userService;

    public CreateUser(UserService userService) {
        this.userService = userService;
    }

    public Users save(Users newUser) {
       return userService.save(newUser);
    }
}
