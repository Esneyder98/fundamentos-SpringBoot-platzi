package com.fundamentosSprinboot.fundamentos;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelArchivo {


    public ExcelArchivo() {
    }

    void crearArchivo(){
        //Crear libro de trabajo en blanco
        Workbook workbook = new HSSFWorkbook();
        //Crea hoja nueva
        Sheet sheet = workbook.createSheet("Hoja de datos");
        //crear la información que estará contenida en la hoja de datos.
        Map<String, Object[]> datos = new TreeMap<String, Object[]>();
        datos.put("1", new Object[]{"Identificador", "Nombre", "Apellidos"});
        datos.put("2", new Object[]{1, "María", "Remen"});
        datos.put("3", new Object[]{2, "David", "Allos"});
        datos.put("4", new Object[]{3, "Carlos", "Caritas"});
        datos.put("5", new Object[]{4, "Luisa", "Vitz"});

        //Iterar sobre datos para escribir en la hoja
        Set<String> keyset = datos.keySet();
        int numeroRenglon = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(numeroRenglon++);
            Object[] arregloObjetos = datos.get(key);
            int numeroCelda = 0;
            for (Object obj : arregloObjetos) {
                Cell cell = row.createCell(numeroCelda++);
                if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                } else if (obj instanceof Integer) {
                    cell.setCellValue((Integer) obj);
                }
            }
        }

        try {
            //Se genera el documento
            FileOutputStream out = new FileOutputStream(new File("C:\\Users/Usuario/Downloads/ejemplo.xls"));
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
