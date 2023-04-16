package com.fundamentosSprinboot.fundamentos.caseuse;

import com.fundamentosSprinboot.fundamentos.entity.Users;
import com.fundamentosSprinboot.fundamentos.service.UserService;

import java.util.List;

public class GetUserImplement implements GetUser {
    private UserService userService;

    public GetUserImplement(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<Users> getAll() {
        return userService.GetAllUsers();
    }
}
