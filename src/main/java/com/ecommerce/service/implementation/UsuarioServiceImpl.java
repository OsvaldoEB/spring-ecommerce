package com.ecommerce.service.implementation;

import com.ecommerce.model.Usuario;
import com.ecommerce.repository.UsuarioRepository;
import com.ecommerce.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public Optional<Usuario> findById(long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
