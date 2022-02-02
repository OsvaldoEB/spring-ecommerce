package com.ecommerce.controller;

import com.ecommerce.model.Producto;
import com.ecommerce.service.IProductoService;
import com.ecommerce.service.IUsuarioService;
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

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("")
    public String getHome(Model model){
        List<Producto> productos = productoService.allProductActive();
        model.addAttribute("productos", productos);
        return "administrador/home";

    }

    @GetMapping("/usuarios")
    public String  usuarios(Model model){
        model.addAttribute("usuarios", usuarioService.allUserActive());
        return "administrador/usuarios";
    }


}
