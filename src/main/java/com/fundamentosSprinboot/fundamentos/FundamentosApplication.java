package com.fundamentosSprinboot.fundamentos;

import com.fundamentosSprinboot.fundamentos.bean.MyBean;
import com.fundamentosSprinboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentosSprinboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentosSprinboot.fundamentos.bean.MyBeanWithPropertiesImplement;
import com.fundamentosSprinboot.fundamentos.component.ComponentDependency;
import com.fundamentosSprinboot.fundamentos.entity.Users;
import com.fundamentosSprinboot.fundamentos.properties.UserProperties;
import com.fundamentosSprinboot.fundamentos.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner{

	Log LOGGER = LogFactory.getLog(FundamentosApplication.class);

	//inyectado dependencias
	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;

	private MyBeanWithProperties myBeanWithProperties;
	private UserProperties userProperties;

	private UserRepository userRepository;
	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency,MyBean myBean,MyBeanWithDependency myBeanWithDependency,MyBeanWithProperties myBeanWithProperties,UserProperties userProperties,UserRepository userRepository) {
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userProperties = userProperties;
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		//ejemplosClasesAnteriores();
		saveUsersInDataBase();
		getInformationJpqlFromUser();
	}
	private void getInformationJpqlFromUser(){
		/*LOGGER.info("Usuario con el metodo findByYserEmail"+
				userRepository.findByUserEmail("daniela@domain.com")
						.orElseThrow(()->new RuntimeException("No se encontro el usuario")));
		userRepository.findAndSort("use", Sort.by("id").descending())
				.stream().forEach(user -> LOGGER.info("Usuario con metodo sort " +user));

		userRepository.findByName("John").stream().forEach(user -> LOGGER.info("UsuarioName"+user));
		LOGGER.info("findbyEmailAndName: "+userRepository.findByEmailAndName("carlos@domain.com","user3").orElseThrow(()-> new RuntimeException("Usuario no encontrado")));
		//LIKE
		userRepository.findByNameLike("%user%").
				stream()
				.forEach(user -> LOGGER.info("Usuario findByNameLike: "+user));
		//OR
		userRepository.findByNameOrEmail("Daniela",null).stream()
				.forEach(user -> LOGGER.info("Usuario findByNameOrEmail"+user));*/
		//userRepository.findByBirthdateBetween(LocalDate
		//		.of(2021,12,1),LocalDate.of(2021,12,30))
		//		.stream().forEach(user-> LOGGER.info("finByBirthDateBetween"+user));

		//userRepository.findByNameContainingOrderByIdDesc("user")
		//		.stream().forEach(user -> LOGGER.info("usuario encontrrado con like y ordenado "+user));

		LOGGER.info("Usuario apartir del named parameter "+userRepository.getAllByBirthDateAndEmail( LocalDate.of(2021, 12, 8),"marco@domain.com")
				.orElseThrow(()-> new RuntimeException("No se encontro el usuario a partir del namedParameter")));
	}
	void saveUsersInDataBase(){
		Users user1 = new Users("John", "john@domain.com",LocalDate.of(2021,12,23));
		Users user2 = new Users ("user2", "marco@domain.com", LocalDate.of(2021, 12, 8));
		Users user3 = new Users("Daniela", "daniela@domain.com", LocalDate.of(2021, 9, 8));
		Users user4 = new Users("user4", "marisol@domain.com", LocalDate.of(2021, 6, 18));
		Users user5 = new Users("Karen", "karen@domain.com", LocalDate.of(2021, 1, 1));
		Users user6 = new Users ("user3", "carlos@domain.com", LocalDate.of(2021, 7, 7));
		Users user7 = new Users("Enrique", "enrique@domain.com", LocalDate.of(2021, 11, 12));
		Users user8 = new Users("Luis", "luis@domain.com", LocalDate.of(2021, 2, 27));
		Users user9 = new Users("Paola", "paola@domain.com", LocalDate.of(2021, 4, 10));

		List<Users> list = Arrays.asList(user1,user2,user3,user4,user5,user6,user7,user8,user9);
		list.stream().forEach(userRepository::save);
	}

	private void ejemplosClasesAnteriores(){
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		System.out.println(myBeanWithProperties.function());
		System.out.println(userProperties.getEmail()+" "+userProperties.getPassword());
		try {
			int value= 10/0;
			LOGGER.debug("Mi valor :"+value);
		}catch (Exception e) {
			LOGGER.error("Esto es un error al dividir por cero"+e.getMessage());
		}

	}
}
