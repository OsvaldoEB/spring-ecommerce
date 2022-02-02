package com.ecommerce.service;

import com.ecommerce.model.Orden;
import com.ecommerce.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IOrdenService {

    public List<Orden> findAll();
    public Optional<Orden> findById(Long id);
    public Orden save (Orden orden);
    public String generarNumeroOrden();
    public List<Orden> findByUsuario(Usuario usuario);
}
