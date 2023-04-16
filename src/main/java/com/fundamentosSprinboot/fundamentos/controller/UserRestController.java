package com.fundamentosSprinboot.fundamentos.controller;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fundamentosSprinboot.fundamentos.FundamentosApplication;
import com.fundamentosSprinboot.fundamentos.caseuse.CreateUser;
import com.fundamentosSprinboot.fundamentos.caseuse.DeleteUser;
import com.fundamentosSprinboot.fundamentos.caseuse.GetUser;
import com.fundamentosSprinboot.fundamentos.caseuse.UpdateUser;
import com.fundamentosSprinboot.fundamentos.entity.Users;
import com.fundamentosSprinboot.fundamentos.repository.UserRepository;
import com.fundamentosSprinboot.fundamentos.service.UserService;
import jakarta.persistence.ManyToOne;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserRestController {
    Log LOGGER = LogFactory.getLog(FundamentosApplication.class);
    private GetUser getUser;
    private CreateUser createUser;
    private DeleteUser deleteUser;
    private UpdateUser updateUser;
    private UserRepository userRepository;

    private UserService userService;

    public UserRestController(GetUser getUser,CreateUser createUser,DeleteUser deleteUser, UpdateUser updateUser, UserRepository userRepository,UserService userService) {
        this.getUser = getUser;
        this.createUser = createUser;
        this.deleteUser = deleteUser;
        this.updateUser = updateUser;
        this.userRepository = userRepository;
        this.userService = userService;
    }
    @GetMapping("/")
    List<Users> get(){
        return getUser.getAll();
    }

    private Users user;
    @PostMapping("/")
    ResponseEntity<Users> newUser(@RequestBody Users newUser) {
        return new ResponseEntity<>(createUser.save(newUser), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
        ResponseEntity deleteUser(@PathVariable Long id){
            deleteUser.remove(id);
            return  new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
        ResponseEntity<Users> updateUser(@RequestBody Users newUser, @PathVariable Long id){
        return new ResponseEntity<>(updateUser.update(newUser,id),HttpStatus.OK);

    }

    @GetMapping("/lista")
   List<Users> getAlls(@RequestParam int page, @RequestParam int size){
        return userRepository.findAll(PageRequest.of(page,size)).getContent();

    }
    @GetMapping("/excel")
        ResponseEntity <?> archivoExcel() throws FileNotFoundException {
        Workbook libro = new XSSFWorkbook();
        try {
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error de IOException");
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }


}
