package com.artion.springboot.form.app.controllers;

import com.artion.springboot.form.app.editors.NombreMayusculaEditor;
import com.artion.springboot.form.app.editors.PaisPropertyEditor;
import com.artion.springboot.form.app.editors.RolEditor;
import com.artion.springboot.form.app.models.domain.Pais;
import com.artion.springboot.form.app.models.domain.Rol;
import com.artion.springboot.form.app.models.domain.Usuario;
import com.artion.springboot.form.app.services.PaisService;
import com.artion.springboot.form.app.services.RolService;
import com.artion.springboot.form.app.validation.UsuarioValidador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes("usuario")
public class FormController {

    @Autowired
    private UsuarioValidador validador;

    @Autowired
    private PaisService paisService;

    @Autowired
    private PaisPropertyEditor paisEditor;

    @Autowired
    private RolService rolService;

    @Autowired
    private RolEditor rolEditor;

    /**
     * Registramos un validador del form
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(validador);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, "birthDate" ,new CustomDateEditor(dateFormat, true));

        //Registrar la clase NombreMayusculaEditor
        binder.registerCustomEditor(String.class, "name", new NombreMayusculaEditor());
        binder.registerCustomEditor(String.class, "surname", new NombreMayusculaEditor());

        //Registrar el servicio Pais
        binder.registerCustomEditor(Pais.class, "country", paisEditor);

        //Registrar el servicio Rol
        binder.registerCustomEditor(Rol.class, "roles", rolEditor);
    }

    @ModelAttribute("genero")
    public List<String> genero(){
        return Arrays.asList("Hombre", "Mujer");
    }

    @ModelAttribute("listaRoles")
    public List<Rol> listaRoles(){
        return this.rolService.listar();
    }

    @ModelAttribute("listaPaises")
    public List<Pais> listaPaises(){
        return paisService.listar();
    }

    @ModelAttribute("listaRolesString")
    public List<String> listaRolesString(){
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");
        roles.add("ROLE_MODERATOR");

        return roles;
    }

    @ModelAttribute("listaRolesMap")
    public Map<String, String> listaRolesMap(){
        Map<String, String> roles = new HashMap<>();

        roles.put("ROLE_ADMIN", "Administrador");
        roles.put("ROLE_USER", "Usuario");
        roles.put("ROLE_MODERATOR", "Moderador");

        return roles;
    }

    @ModelAttribute("paises")
    public List<String> paises(){
        return Arrays.asList("España", "Mexico", "Chile", "Argentina", "Perú", "Colombia", "Venezuela");
    }

    @ModelAttribute("paisesMap")
    public Map<String, String> paisesMap(){
        Map<String, String> paises = new HashMap<>();

        paises.put("ES", "España");
        paises.put("MX", "Mexico");
        paises.put("CL", "Chile");
        paises.put("AR", "Argentina");
        paises.put("PE", "Perú");
        paises.put("CO", "Colombia");
        paises.put("VE", "Venezuela");

        return paises;
    }

    @GetMapping("/form")
    public String form(Model model){
        Usuario usuario = new Usuario();
        usuario.setName("John");
        usuario.setSurname("Doe");
        usuario.setId("51.544.515-K");
        usuario.setEnable(true);
        usuario.setSecretValue("valor secreto");
        usuario.setCountry(new Pais(6,"CO", "Colombia"));
        usuario.setRoles(Arrays.asList( new Rol(2, "Usuario", "ROLE_USER")));

        model.addAttribute("titulo", "Formulario Usuarios");
        model.addAttribute("usuario", usuario);

        return "form";
    }

    //Obtiene los datos del formulario
    //Si se desea pasar el objeto usuario con otro nombre a la vista se puede usar @ModelAttribute("nombreObjeto") despues del @Valid
    @PostMapping("/form")
    public String procesar(@Valid Usuario usuario, BindingResult result,  Model model){
//        validador.validate(usuario, result);

        if (result.hasErrors()){
            model.addAttribute("titulo", "Resultado form");
            return "form";
        }
        return "redirect:/ver";
    }

    @GetMapping("/ver")
    public String ver(@SessionAttribute(name="usuario", required = false) Usuario usuario, Model model, SessionStatus status){

        if (usuario == null)
            return "redirect:/form";

        model.addAttribute("titulo", "Resultado form");

        //Limpia el objeto
        status.setComplete();
        return "resultado";
    }
}
