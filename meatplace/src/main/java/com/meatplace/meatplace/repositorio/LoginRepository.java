package com.meatplace.meatplace.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.meatplace.meatplace.entidades.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Integer>{
    @Procedure(name = "validarLogin")
    Login validarLogin(@Param("nombre") String nombre, @Param("contrasena") String contrasena);
}
