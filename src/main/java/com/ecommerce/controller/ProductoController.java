package com.ecommerce.controller;

import com.ecommerce.model.Producto;
import com.ecommerce.model.Usuario;
import com.ecommerce.service.IProductoService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private IProductoService productoService;

    @GetMapping("")
    public String getShow(){
        return "productos/show";
    }

    @GetMapping("/create")
    public String create(){
        return "productos/create";
    }


    @PostMapping("/save")
    public String save(Producto producto){
        LOGGER.info("Este es el objeto producto {}", producto);
        Usuario u = new Usuario((long) 1, "", "", "", "", "", "", "", "", true);
        producto.setUsuario(u);
        productoService.save(producto);
        return "redirect:/productos";
    }
}
