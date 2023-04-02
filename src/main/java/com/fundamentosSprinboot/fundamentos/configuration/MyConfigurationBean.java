package com.fundamentosSprinboot.fundamentos.configuration;

import com.fundamentosSprinboot.fundamentos.bean.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfigurationBean {
    @Bean
    public MyBean beanOperation(){
        return new MyBean2Implement();

    }

    @Bean
    public MyOperation myOperation(){
        return new MyOperationImplement();

    }

    @Bean
    public MyBeanWithDependency beanOperationWithDependency(MyOperation myOperation){
        return new MyBeanWithDependencyImplement(myOperation);

    }
}
