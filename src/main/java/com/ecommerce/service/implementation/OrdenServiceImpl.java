package com.ecommerce.service.implementation;

import com.ecommerce.model.Orden;
import com.ecommerce.repository.IOrdenRepository;
import com.ecommerce.service.IOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdenServiceImpl implements IOrdenService {

    @Autowired
    private IOrdenRepository ordenRepository;

    @Override
    public Orden save(Orden orden) {
        return ordenRepository.save(orden);
    }
}
