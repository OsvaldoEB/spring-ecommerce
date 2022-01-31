package com.ecommerce.service;

import com.ecommerce.model.Usuario;

import java.util.Optional;

public interface IUsuarioService {
    public Optional<Usuario> findById(long id);
}
