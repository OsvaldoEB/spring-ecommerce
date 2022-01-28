package com.ecommerce.controller;

import com.ecommerce.model.DetalleOrden;
import com.ecommerce.model.Orden;
import com.ecommerce.model.Producto;
import com.ecommerce.service.IProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private IProductoService productoService;

    //Variable para almacenar los detalles de la orden
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

    //Crear objeto Orden y almacena los datos de la orden
    Orden orden = new Orden();

    //Metodo para visualizar el home del usuario normal
    @GetMapping("")
    public String home(Model model){
        model.addAttribute("productos", productoService.allProductActive());
        return "usuario/home";
    }

    //Metodo para visualizar productohome
    @GetMapping("productohome/{id}")
    public String productoHome(@PathVariable Long id,Model model){
        //log que ayuda a saber que id esta enviando a la vista productohome
        log.info("Id producto enviado como parametro {}", id);
        //Declaramos un bojteto tipo producto
        Producto producto = new Producto();
        // Nos atre un objeto Optional
        Optional<Producto> productoOptional = productoService.get(id);
        //Pasar producto para obtener los datos del producto
        producto = productoOptional.get();

        model.addAttribute("producto", producto);
        return "usuario/productohome";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Long id, @RequestParam Integer cantidad, Model model){
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal = 0;

        //Obtenemos el producto que se va a agregar al carrito
        Optional<Producto> optionalProducto = productoService.get(id);
        log.info("Producto añadido: {}", optionalProducto.get());
        log.info("Cantidad: {}", cantidad);
        producto = optionalProducto.get();

        //Obtenemos los datos para la vista carrito
        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio() * cantidad);
        detalleOrden.setProducto(producto);

        //Añadimos cada producto (detalleOrden) a la lista
        detalles.add(detalleOrden);

        //Sumar el total de todos los productos que se encuentren en la lista
        sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        return "usuario/carrito";
    }

}
