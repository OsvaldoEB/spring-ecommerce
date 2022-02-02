package com.ecommerce.controller;

import com.ecommerce.model.Usuario;
import com.ecommerce.service.IUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private IUsuarioService usuarioService;

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
        return "usuario/compras";
    }
}
