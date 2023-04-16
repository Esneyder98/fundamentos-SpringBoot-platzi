package com.fundamentosSprinboot.fundamentos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fundamentosSprinboot.fundamentos.entity.Users;
import com.fundamentosSprinboot.fundamentos.service.UserService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ArchivoExcelDinamico {
     private UserService userService;

    public ArchivoExcelDinamico(UserService userService) {
        this.userService = userService;
    }


//Cell (para celdas)
    // Row (para filas)
    // Sheet (hojas)
    public void crearApartirArray(){
        ArrayList<Users> users = new ArrayList<>();
        users = (ArrayList<Users>) userService.GetAllUsers();

       // personas.add(new Persona("Luis", "https://parzibyte.me", 50));
      //  personas.add(new Persona("Rasmus Lerdorf", "https://toys.lerdorf.com/", 53));
      //  personas.add(new Persona("Brian Kernighan", "https://www.cs.princeton.edu/~bwk/", 80));
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
        //File directorioActual = new File(".");
        //String ubicacion = directorioActual.getAbsolutePath();
        //String ubicacionArchivoSalida = ubicacion.substring(0, ubicacion.length() - 1) + nombreArchivo;

        try {
            FileOutputStream outputStream;
            FileOutputStream out = new FileOutputStream(new File("C:\\Users/Usuario/Downloads/"));
            String ubicacionArchivoSalida =out+nombreArchivo;
            outputStream = new FileOutputStream(ubicacionArchivoSalida);
            libro.write(outputStream);
            libro.close();
            System.out.println("Libro de personas guardado correctamente");
        } catch (FileNotFoundException ex) {
            System.out.println("Error de filenotfound");
        } catch (IOException ex) {
            System.out.println("Error de IOException");
        }

    }
}
