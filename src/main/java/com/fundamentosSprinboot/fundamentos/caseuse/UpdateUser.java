package com.fundamentosSprinboot.fundamentos.caseuse;

import com.fundamentosSprinboot.fundamentos.entity.Users;
import com.fundamentosSprinboot.fundamentos.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UpdateUser {
    private UserService userService;

    public UpdateUser(UserService userService) {
        this.userService = userService;
    }

    public Users update(Users newUser, Long id) {
       return userService.update(newUser, id);
    }
}
