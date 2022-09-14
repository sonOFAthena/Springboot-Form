package com.artion.springboot.form.app.services;

import com.artion.springboot.form.app.models.domain.Rol;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolServiceImpl implements RolService{

    private List<Rol> roles;

    public RolServiceImpl() {
        this.roles = new ArrayList<>();
        this.roles.add(new Rol(1, "Administrador", "ROLE_ADMIN"));
        this.roles.add(new Rol(2, "Usuario", "ROLE_USER"));
        this.roles.add(new Rol(3, "Moderador", "ROLE_MODERATOR"));
    }

    @Override
    public List<Rol> listar() {
        return roles;
    }

    @Override
    public Rol obtenerPorId(Integer id) {
        Rol resultado = null;

        for (Rol rol: this.roles){
            if (id == rol.getId()){
                resultado = rol;
                break;
            }
        }

        return resultado;
    }
}
