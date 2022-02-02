package com.ecommerce.controller;

import com.ecommerce.model.Orden;
import com.ecommerce.model.Usuario;
import com.ecommerce.service.IOrdenService;
import com.ecommerce.service.IUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IOrdenService ordenService;

    //Metodo para mostrar la vista de registro de usuario
    @GetMapping("/registro")
    public String create(){
        return "usuario/registro";
    }

    //Metodo para almacenar (guardar) usuario
    @PostMapping("/save")
    public String save(Usuario usuario){
        logger.info("Usuario registro {}", usuario);

        //Aqui le asignamos por defecto que el usuario es de tipo USER
        usuario.setTipoUsuario("USER");
        usuarioService.save(usuario);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "usuario/login";
    }

    @PostMapping("/acceder")
    public String acceder(Usuario usuario, HttpSession session){
        logger.info("Accesos: {}", usuario);
        //Obtenemos un usuario que contenga un email
        Optional<Usuario> user = usuarioService.findByEmail(usuario.getEmail());
        //logger.info("Usuario de db: {}", user.get());

        //Validacion momentanea, ya que se cambiara por spring security
        if(user.isPresent()){
            //Se guarda el id del usuario para la sesion
            session.setAttribute("idUsuario", user.get().getId());
            if(user.get().getTipoUsuario().equals("ADMIN")){
                return "redirect:/administrador";
            } else {
                return "redirect:/";
            }
        } else {
            logger.info("Usuario no existe");
        }
        return "redirect:/";
    }

    //Metodo para obtener compras
    @GetMapping("/shopping")
    public String getShop(Model model, HttpSession session){
        model.addAttribute("sesion", session.getAttribute("idUsuario"));
        Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idUsuario").toString())).get();
        List<Orden> ordenes = ordenService.findByUsuario(usuario);

        model.addAttribute("ordenes", ordenes);
        return "usuario/compras";
    }

    //Metodo para obtener los detalles de la compra
    @GetMapping("/detail/{id}")
    public  String detailShop(@PathVariable Long id, HttpSession session, Model model){
        logger.info("Id de la orden: {}", id);
        Optional<Orden> orden = ordenService.findById(id);

        model.addAttribute("detalles", orden.get().getDetalle());
        //sesion
        model.addAttribute("sesion", session.getAttribute("idUsuario"));
        return "usuario/detallecompra";
    }


}
