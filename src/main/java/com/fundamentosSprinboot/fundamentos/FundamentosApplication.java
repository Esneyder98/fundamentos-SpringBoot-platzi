package com.fundamentosSprinboot.fundamentos;

import com.fundamentosSprinboot.fundamentos.bean.MyBean;
import com.fundamentosSprinboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentosSprinboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentosSprinboot.fundamentos.bean.MyBeanWithPropertiesImplement;
import com.fundamentosSprinboot.fundamentos.component.ComponentDependency;
import com.fundamentosSprinboot.fundamentos.entity.Users;
import com.fundamentosSprinboot.fundamentos.properties.UserProperties;
import com.fundamentosSprinboot.fundamentos.repository.UserRepository;
import com.fundamentosSprinboot.fundamentos.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
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

	private UserService userService;
	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency,MyBean myBean,MyBeanWithDependency myBeanWithDependency,MyBeanWithProperties myBeanWithProperties,UserProperties userProperties,UserRepository userRepository,UserService userService) {
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userProperties = userProperties;
		this.userRepository = userRepository;
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		//ejemplosClasesAnteriores();
		saveUsersInDataBase();
		getInformationJpqlFromUser();
		saveWithErrorTrasactional();
		//
		//ExcelArchivo excelArchivo = new ExcelArchivo();
		//excelArchivo.crearArchivo();

		userService.crearApartirArray();
	}

	public void saveWithErrorTrasactional(){
		Users test1 = new Users("TestTransactional1", "TestTransactional1@domain.com", LocalDate.now());
		Users test2 = new Users("TestTransactional2", "TestTransactional2@domain.com", LocalDate.now());
		Users test3 = new Users("TestTransactional3", "TestTransactional3@domain.com", LocalDate.now());
		Users test4 = new Users("TestTransactional4", "TestTransactional4@domain.com", LocalDate.now());

		List<Users> users = Arrays.asList(test1, test2, test3, test4);
		try{
			userService.saveTrasactional(users);
		}catch(Exception e){
			LOGGER.error("Esta es una excepcion dentro del metodo transational" + e);
		}


		userService.GetAllUsers().stream()
				.forEach(user ->
						LOGGER.info("Usuario dentro del metodo transactional= "+user));
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

	private void crearApartirArray(){
		ArrayList<Users> users = new ArrayList<>();
		users = (ArrayList<Users>) userService.GetAllUsers();

		// crea el archivo
		Workbook libro = new XSSFWorkbook();
		final String nombreArchivo = "Users.xlsx";
		// crea una hoja
		Sheet hoja = libro.createSheet("Hoja 1");

		String[] encabezados = {"Name", "Email", "Birdate"};
		int indiceFila = 0;
		// Escribo los encabesados en la primera fila
		Row fila = hoja.createRow(indiceFila);
		for (int i = 0; i < encabezados.length; i++) {
			String encabezado = encabezados[i];
			Cell celda = fila.createCell(i);
			celda.setCellValue(encabezado);
		}

		indiceFila++;
		for (int i = 0; i < users.size(); i++) {
			fila = hoja.createRow(indiceFila);
			// obtengo el usuario en cada posicion
			Users user = users.get(i);
			// boy agrendo cada atributo en su respectiva fila
			fila.createCell(0).setCellValue(user.getName());
			fila.createCell(1).setCellValue(user.getEmail());
			fila.createCell(2).setCellValue(user.getBirthDate());
			indiceFila++;
		}

		// Guardamos
		File directorioActual = new File(".");
		String ubicacion = directorioActual.getAbsolutePath();
		String ubicacionArchivoSalida = ubicacion.substring(0, ubicacion.length() - 1) + nombreArchivo;

		try {
			FileOutputStream outputStream;
			//FileOutputStream out = new FileOutputStream(new File("C:\\Users/Usuario/Downloads/"));
			//String ubicacionArchivoSalida =out+nombreArchivo;
			outputStream = new FileOutputStream(ubicacionArchivoSalida);
			libro.write(outputStream);
			libro.close();
			LOGGER.info("Libro de personas guardado correctamente");
		} catch (FileNotFoundException ex) {
			System.out.println("Error de filenotfound");
			LOGGER.error("Error de filenotfound");
		} catch (IOException ex) {
			System.out.println("Error de IOException");
			LOGGER.error("Error de IOException");
		}

	}
}
