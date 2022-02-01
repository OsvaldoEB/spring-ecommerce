package com.ecommerce.controller;

import com.ecommerce.model.Producto;
import com.ecommerce.model.Usuario;
import com.ecommerce.service.IProductoService;
import com.ecommerce.service.IUsuarioService;
import com.ecommerce.service.UploadFileService;
import com.ecommerce.service.implementation.UsuarioServiceImpl;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private IProductoService productoService;

    //Inyectamos la clase UploadFileService
    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("")
    public String getShow(Model model){
        model.addAttribute("productos", productoService.allProductActive());
        return "productos/show";
    }

    //Metodo que devuelve la vista para crear un producto
    @GetMapping("/create")
    public String create(){
        return "productos/create";
    }

    //Metodo que devuelve la vista para almacenar un producto
    @PostMapping("/save")
    public String save(Producto producto, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {
        LOGGER.info("Este es el objeto producto {}", producto);

        Usuario u = usuarioService.findById(Integer.parseInt(session.getAttribute("idUsuario").toString() )).get();
        producto.setUsuario(u);
        //imagen
        if(producto.getId()==null){ //validacion cuando se crea un producto
            String nombreImagen = uploadFileService.saveImage(file);
            producto.setImagen(nombreImagen);
        } else{

        }
        productoService.save(producto);
        return "redirect:/productos";
    }

    //Metodo para editar el producto seleccionado
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        //Creamos un objeto tipo producto
        Producto producto = new Producto();
        //Variable Optional para que nos devuelva la busqueda del producto
        Optional<Producto> optionalProducto = productoService.get(id);
        producto = optionalProducto.get();

        LOGGER.info("Producto buscado: {}",producto);
        //Se agrega attributo al model para poder cargar los datos obtenidos del producto
        model.addAttribute("producto", producto);
        return "productos/edit";
    }

    //Metodo para actualizar la informacion del producto
    @PostMapping("/update")
    public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {

        Producto p = new Producto();
        p=productoService.get(producto.getId()).get();

        if(file.isEmpty()){ //Cuando editamos el producto pero no cambiamos la imagen

            producto.setImagen(p.getImagen());
        }else{ //cuando se edita tambien la imagen

            //Eliminar cuando no sea la imagen por defecto
            if (!p.getImagen().equals("default.jpg")){
                uploadFileService.deleteImagen(p.getImagen());
            }

            String nombreImagen = uploadFileService.saveImage(file);
            producto.setImagen(nombreImagen);
        }

        producto.setUsuario(p.getUsuario());
        productoService.update(producto);
        return "redirect:/productos";
    }

    //Metodo para eliminar (forma logica) un producto
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){

        //Codigo para cuando un producto sea dado de baja se elimine la foto en el servidor
        /*Producto p = new Producto();
        p = productoService.get(id).get();

        //Eliminar cuando no sea la imagen por defecto
        if (!p.getImagen().equals("default.jpg")){
            uploadFileService.deleteImagen(p.getImagen());
        }*/

        productoService.eliminar(id);
        return "redirect:/productos";
    }
}
