package com.fundamentosSprinboot.fundamentos;

import com.fundamentosSprinboot.fundamentos.bean.MyBean;
import com.fundamentosSprinboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentosSprinboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentosSprinboot.fundamentos.bean.MyBeanWithPropertiesImplement;
import com.fundamentosSprinboot.fundamentos.component.ComponentDependency;
import com.fundamentosSprinboot.fundamentos.properties.UserProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner{
	//inyectado dependencias
	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;

	private MyBeanWithProperties myBeanWithProperties;

	private UserProperties userProperties;
	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency,MyBean myBean,MyBeanWithDependency myBeanWithDependency,MyBeanWithProperties myBeanWithProperties,UserProperties userProperties) {
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userProperties = userProperties;
	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		System.out.println(myBeanWithProperties.function());
		System.out.println(userProperties.getEmail()+" "+userProperties.getPassword());
	}
}
