package com.ecommerce.repository;

import com.ecommerce.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //Metodo que nos permita obtener el registro
    public Optional<Usuario> findByEmail(String email);
}
