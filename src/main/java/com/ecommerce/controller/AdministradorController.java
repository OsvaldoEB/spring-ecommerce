package com.ecommerce.controller;

import com.ecommerce.model.Orden;
import com.ecommerce.model.Producto;
import com.ecommerce.service.IOrdenService;
import com.ecommerce.service.IProductoService;
import com.ecommerce.service.IUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("administrador")
public class AdministradorController {

    @Autowired
    private IProductoService productoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IOrdenService ordenService;

    private Logger log  = LoggerFactory.getLogger(AdministradorController.class);

    @GetMapping("")
    public String getHome(Model model){
        List<Producto> productos = productoService.allProductActive();
        model.addAttribute("productos", productos);
        return "administrador/home";

    }

    @GetMapping("/usuarios")
    public String  user(Model model){
        model.addAttribute("usuarios", usuarioService.allUserActive());
        return "administrador/usuarios";
    }

    @GetMapping("/ordens")
    public String ordens(Model model){
        //Añadimos a model loa atributos de la orden así como el metodo findAll para mostrar todas las ordenes
        model.addAttribute("ordenes", ordenService.findAll());
        return "administrador/ordenes";
    }

    @GetMapping("/detailorden/{id}")
    public String detailOrden(Model model, @PathVariable Long id){
        log.info("Id de la orden: {}",id);
        //Se declara un objeto de tipo Orden, como el objeto devuelve un Optional vamos a tener que agregarle .get() al final de la sentencia
        Orden orden = ordenService.findById(id).get();
        model.addAttribute("detalles", orden.getDetalle());
        return "administrador/detalleorden";
    }


}
