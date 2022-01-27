package com.ecommerce.controller;

import com.ecommerce.model.Producto;
import com.ecommerce.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("administrador")
public class AdministradorController {

    @Autowired
    private IProductoService productoService;

    @GetMapping("")
    public String getHome(Model model){
        List<Producto> productos = productoService.allProductActive();
        model.addAttribute("productos", productos);
        return "administrador/home";

    }


}
