package com.fundamentosSprinboot.fundamentos.configuration;

import com.fundamentosSprinboot.fundamentos.caseuse.GetUser;
import com.fundamentosSprinboot.fundamentos.caseuse.GetUserImplement;
import com.fundamentosSprinboot.fundamentos.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaseUseConfiguration {
    @Bean
    GetUser getUser(UserService userService){
        return new GetUserImplement(userService);
    }
}
