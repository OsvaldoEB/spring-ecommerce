package com.ecommerce.controller;

import com.ecommerce.service.IProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private IProductoService productoService;

    //Metodo para visualizar el home del usuario normal
    @GetMapping("")
    public String home(Model model){
        model.addAttribute("productos", productoService.allProductActive());
        return "usuario/home";
    }

    //Metodo para visualizar productohome
    @GetMapping("productohome/{id}")
    public String productoHome(@PathVariable Long id){
        //log que ayuda a saber que id esta enviando a la vista productohome
        log.info("Id producto enviado como parametro {}", id);
        return "usuario/productohome";
    }
}
