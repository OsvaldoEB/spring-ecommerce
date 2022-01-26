package com.ecommerce.service;

import com.ecommerce.model.Producto;

import java.util.List;
import java.util.Optional;

//Aquí se definen todos los metodos CRUD
public interface IProductoService {
    //Nuevo producto
    public Producto save(Producto producto);

    //Para buscar producto
    public Optional<Producto> get(long id);

    //Editar producto
    public void update(Producto producto);

    //Eliminar producto, se utiliza baja de manera logica
    public long delete(long id);

    //Mostrar listado de productos
    public List<Producto> allProductActive();


}
