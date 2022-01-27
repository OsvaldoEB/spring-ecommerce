package com.ecommerce.controller;

import com.ecommerce.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private IProductoService productoService;

    @GetMapping("")
    public String home(Model model){
        model.addAttribute("productos", productoService.allProductActive());
        return "usuario/home";
    }
}
