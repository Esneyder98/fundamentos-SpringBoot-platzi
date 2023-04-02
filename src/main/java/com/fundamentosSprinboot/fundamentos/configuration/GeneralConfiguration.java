package com.fundamentosSprinboot.fundamentos.configuration;

import com.fundamentosSprinboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentosSprinboot.fundamentos.bean.MyBeanWithPropertiesImplement;
import com.fundamentosSprinboot.fundamentos.properties.UserProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(UserProperties.class)
public class GeneralConfiguration {
    @Value("${value.name}")
    private String name;

    @Value("${value.apellido}")
    private String apellido;

    @Value("${value.random}")
    private String randon;

    @Bean
    public MyBeanWithProperties function(){
        return new MyBeanWithPropertiesImplement(name,apellido);

    }
}
