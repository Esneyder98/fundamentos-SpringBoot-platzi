package com.fundamentosSprinboot.fundamentos.service;

import com.fundamentosSprinboot.fundamentos.FundamentosApplication;
import com.fundamentosSprinboot.fundamentos.entity.Users;
import com.fundamentosSprinboot.fundamentos.repository.UserRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final Log LOG = LogFactory.getLog(UserService.class);
    Log LOGGER = LogFactory.getLog(FundamentosApplication.class);
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional
    public void saveTrasactional(List<Users> users){
       // imprimir la info que va llegando peek
        users.stream().peek(user -> LOG.info("Usuario insertado: "+ user))
                .forEach(user -> userRepository.save(user));
    }
    public List<Users> GetAllUsers(){
       return userRepository.findAll();
    }

    public Users save(Users newUser) {
        return userRepository.save(newUser);
    }

    public void delete(Long id) {
        userRepository.delete(new Users(id));
    }

    public Users update(Users newUser, Long id) {
        return userRepository.findById(id)
                .map(
                        user -> {
                            user.setEmail(newUser.getEmail());
                            user.setBirthDate(newUser.getBirthDate());
                            user.setName(newUser.getName());
                            return userRepository.save(user);
                        }
                ).orElseThrow(()->new RuntimeException("No se encontro usuario a modificar"));
    }

    public Workbook crearApartirArray() {
        ArrayList<Users> users = new ArrayList<>();
        users = (ArrayList<Users>) this.GetAllUsers();

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
        return libro;
    }
}
