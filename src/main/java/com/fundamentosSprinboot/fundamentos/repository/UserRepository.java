package com.fundamentosSprinboot.fundamentos.repository;

import com.fundamentosSprinboot.fundamentos.dto.UserDto;
import com.fundamentosSprinboot.fundamentos.entity.Users;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long>, PagingAndSortingRepository<Users,Long> {
    @Query("Select u from Users u where u.email=?1")
    Optional<Users> findByUserEmail(String email);

    @Query("SELECT  u from Users u where u.name like ?1%")
    List<Users> findAndSort(String name, Sort sort);

    List<Users> findByName(String name);
//optional para el manejo de los null
    Optional<Users> findByEmailAndName(String email,String name);

    //like
    List<Users> findByNameLike(String name);

    List<Users> findByNameOrEmail(String name, String email);

    List<Users> findByBirthDateBetween(LocalDate start, LocalDate end);

    List<Users> findByNameLikeOrderByIdDesc(String name);
    //hace lo mismo que el like %H% pero sin necesidad de pasarle el parametro con lo %G%
    List<Users> findByNameContainingOrderByIdDesc(String name);

    @Query("SELECT new com.fundamentosSprinboot.fundamentos.dto.UserDto(u.id, u.name, u.birthDate)" +
            " FROM Users u " +
            " WHERE u.birthDate=:parametroFecha " +
            " AND u.email=:parametroEmail")
    Optional<UserDto> getAllByBirthDateAndEmail(@Param("parametroFecha") LocalDate date,
                                                @Param("parametroEmail") String email);
}
