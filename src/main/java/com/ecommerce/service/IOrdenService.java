package com.ecommerce.service;

import com.ecommerce.model.Orden;

import java.util.List;

public interface IOrdenService {

    public List<Orden> findAll();
    public Orden save (Orden orden);
}
