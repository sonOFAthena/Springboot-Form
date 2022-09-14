package com.artion.springboot.form.app.editors;

import com.artion.springboot.form.app.services.PaisService;
import com.artion.springboot.form.app.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class RolEditor extends PropertyEditorSupport {

    @Autowired
    private RolService service;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        try{
            Integer id = Integer.parseInt(text);
            this.setValue(service.obtenerPorId(id));
        }catch (NumberFormatException e){
            setValue(null);
        }

    }
}
