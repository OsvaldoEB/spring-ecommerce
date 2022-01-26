package com.ecommerce.repository;

import com.ecommerce.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    //Query para obtener los usuarios activos
    @Query("select p from Producto p  where p.estatus = 1")
    public List<Producto> allProductActive();

    @Transactional
    @Modifying
    @Query("update Producto set estatus = 0 where id = :id")
    public int eliminar(@Param("id") long id);


}
