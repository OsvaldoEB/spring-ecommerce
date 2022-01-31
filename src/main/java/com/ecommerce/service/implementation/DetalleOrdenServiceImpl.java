package com.ecommerce.service.implementation;

import com.ecommerce.model.DetalleOrden;
import com.ecommerce.repository.IDetalleOrdenRepository;
import com.ecommerce.service.IDetalleOrdenService;
import org.springframework.stereotype.Service;

@Service
public class DetalleOrdenServiceImpl implements IDetalleOrdenService  {

    private IDetalleOrdenRepository detalleOrdenRepository;

    @Override
    public DetalleOrden save(DetalleOrden detalleOrden) {
        return detalleOrdenRepository.save(detalleOrden);
    }
}
