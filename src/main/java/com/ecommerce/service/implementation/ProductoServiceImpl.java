package com.ecommerce.service.implementation;

import com.ecommerce.model.Producto;
import com.ecommerce.repository.ProductoRepository;
import com.ecommerce.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements IProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> get(long id) {
        return productoRepository.findById(id);
    }

    @Override
    public void update(Producto producto) {
        productoRepository.save(producto);
    }

    @Override
    public int eliminar(long id) {
        return productoRepository.eliminar(id);
    }


    @Override
    public List<Producto> allProductActive() {
        return productoRepository.allProductActive();
    }
}
