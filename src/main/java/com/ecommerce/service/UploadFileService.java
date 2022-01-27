package com.ecommerce.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadFileService {

    //Aquí se agrega la URL donde se van a guardar las imagenes
    private String folder= "image//";

    public String saveImage(MultipartFile file) throws IOException {
        //IF que nos dice si la variable file se ecnuentra vacia
        if (file.isEmpty()){
            //pasar imagen a bytes
            byte [] bytes = file.getBytes();
            //Asignamos en que path queremos que se guarde
            Path path = Paths.get(folder+file.getOriginalFilename());
            Files.write(path, bytes);
            //Regresamos el nombre que tiene la imagen
            return file.getOriginalFilename();
        }
        return "default.jpg";
    }

    //Metodo para eliminar una imagen cuando eliminemos un producto
    public void deleteImagen(String nombre){
        String ruta = "images//";
        File file = new File(ruta+nombre);
        file.delete();
    }
}
