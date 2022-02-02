package com.ecommerce.service;

import com.ecommerce.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    public List<Usuario> allUserActive();
    public Optional<Usuario> findById(long id);
    public Usuario save(Usuario usuario);
    public Optional<Usuario> findByEmail(String email);
}
