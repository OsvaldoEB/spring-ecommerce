package com.ecommerce.controller;

import com.ecommerce.model.DetalleOrden;
import com.ecommerce.model.Orden;
import com.ecommerce.model.Producto;
import com.ecommerce.model.Usuario;
import com.ecommerce.service.IDetalleOrdenService;
import com.ecommerce.service.IOrdenService;
import com.ecommerce.service.IProductoService;
import com.ecommerce.service.IUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private IProductoService productoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IOrdenService ordenService;

    @Autowired
    private IDetalleOrdenService detalleOrdenService;

    //Variable para almacenar los detalles de la orden
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

    //Crear objeto Orden y almacena los datos de la orden
    Orden orden = new Orden();

    //Metodo para visualizar el home del usuario normal
    @GetMapping("")
    public String home(Model model, HttpSession session){

        model.addAttribute("productos", productoService.allProductActive());
        log.info("Session usuario: {}" , session.getAttribute("idUsuario"));
        //session
        model.addAttribute("sesion", session.getAttribute("idUsuario"));
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
    public String addCart(@RequestParam Long id, @RequestParam double cantidad, Model model){
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal = 0;
        int pos = 0;

        //Obtenemos el producto que se va a agregar al carrito
        Optional<Producto> optionalProducto = productoService.get(id);
        log.info("Producto añadido: {}", optionalProducto.get());
        log.info("Cantidad: {}", Integer.valueOf((int) cantidad));
        producto = optionalProducto.get();

        //Obtenemos los datos para la vista carrito
        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio() * cantidad);
        detalleOrden.setProducto(producto);

        /*//Validar que el producto no se añada dos veces
        Long idProducto = producto.getId();
        boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);
        if(!ingresado){
            //Añadimos cada producto (detalleOrden) a la lista

            detalles.add(detalleOrden);
        } *//*else {
            if(detalles.size()>0){
                for (int i = 0; i < detalles.size(); i++){
                    if(detalleOrden.getProducto().getId() == detalles.get(i).getProducto().getId()){
                        pos = i;
                    }
                }
                if(detalleOrden.getProducto().getId() == detalles.get(pos).getProducto().getId()){
                    detalleOrden.setCantidad(cantidad) = detalles.get(pos).getCantidad() + detalleOrden.setCantidad(cantidad) ;
                }
        }*/


        log.info("Detalle orden: {}", detalleOrden);
        //Obtenemos la posicion de la lista detalles para cuando se requiera agregar otro producto del mismo tipo se pueda agregar
        if(detalles.size()>0){
            //For que va recorriendo las posiciones de la lista detalles
            for (int i = 0; i < detalles.size(); i++){
                if(detalleOrden.getProducto().getId() == detalles.get(i).getProducto().getId()){
                    pos = i;
                }
            }
            //IF que nos nos iguala el id del detalle de la orden al id de la lista dealles
            if(detalleOrden.getProducto().getId() == detalles.get(pos).getProducto().getId()){
                //Se sustitulle la cantidad anterior por la nueva cuando se haya agregado un nuevo producto del mismo tipo
                cantidad = detalles.get(pos).getCantidad() + cantidad;
                //Se realiza la suma total de la actualizacion de los prodcutos
                sumaTotal = detalles.get(pos).getTotal()*cantidad;
                detalles.get(pos).setCantidad(cantidad);
                detalles.get(pos).setTotal(sumaTotal);
            } else{
                detalleOrden.setCantidad(cantidad);
                detalleOrden.setPrecio(producto.getPrecio());
                detalleOrden.setNombre(producto.getNombre());
                detalleOrden.setTotal(producto.getPrecio() * cantidad);
                detalleOrden.setProducto(producto);
                detalles.add(detalleOrden);
            }
        }else{

            detalles.add(detalleOrden);
        }
        //Sumar el total de todos los productos que se encuentren en la lista
        sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        return "usuario/carrito";
    }

    //Quitar un producto del carrito
    @GetMapping("/delete/cart/{id}")
    public String deleteProductCart(@PathVariable Long id, Model model){

        //Lista nueva de productos
        List<DetalleOrden> ordenesNuevas = new ArrayList<DetalleOrden>();

        //Va a recorrer una lista de detalle orden
        for(DetalleOrden detalleOrden : detalles){
            if(detalleOrden.getProducto().getId()!=id){
                ordenesNuevas.add(detalleOrden);
            }
        }

        //Poner la nueva lista con los productos restantes
        detalles=ordenesNuevas;

        double sumaTotal = 0;
        //Sumar el total de todos los productos que se encuentren en la lista
        sumaTotal = detalles.stream().mapToDouble(dt->dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);

        return "usuario/carrito";
    }

    @GetMapping("/getCart")
    public String getCart(Model model, HttpSession session){

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        //Sesion
        model.addAttribute("sesion", session.getAttribute("idUsuario"));
        return "/usuario/carrito";
    }

    //Metodo para agregar los datos a  la orden
    @GetMapping("/order")
    public String order(Model model, HttpSession session){

        Usuario usuario = usuarioService.findById( Integer.parseInt(session.getAttribute("idUsuario").toString())).get();

        model.addAttribute("cart", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario", usuario);
        return "/usuario/resumenorden";
    }

    //Metodo para guardar la orden
    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession session ){
        Date fechaCreacion = new Date();
        orden.setFechaCreacion(fechaCreacion);
        orden.setNumero(ordenService.generarNumeroOrden());

        //Usuario que hace referencia a la orden
        Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idUsuario").toString())).get();
        orden.setUsuario(usuario);

        ordenService.save(orden);

        //Guardar detalles
        for (DetalleOrden dt : detalles){
            dt.setOrden(orden);
            detalleOrdenService.save(dt);
        }

        //Limpiar lista y orden valores que tiene nuestro detalle
        orden = new Orden();
        detalles.clear();

        return "redirect:/";
    }

    //Metodo para busqueda de productos
    @PostMapping("/search")
    public String searchProduct(@RequestParam String nombre, Model model){
        log.info("Nombre del producto: {}", nombre);
        List<Producto> productos = productoService.allProductActive().stream().filter( p -> p.getNombre().contains(nombre)).collect(Collectors.toList());
        model.addAttribute("productos", productos);
        return "usuario/home";
    }

}
