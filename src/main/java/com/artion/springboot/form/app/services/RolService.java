package com.artion.springboot.form.app.services;

import com.artion.springboot.form.app.models.domain.Rol;

import java.util.List;

public interface RolService {

    public List<Rol> listar();
    public Rol obtenerPorId(Integer id);
}
