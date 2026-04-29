package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Usuario;
import com.BIM1.ProyectoDesarrolloColectivo.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioViewController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("usuarios", service.getAllUsuarios());
        return "usuarios";
    }

    // EL BindingResult result almacena los errores de validación
    // RedirectAttributes redirect permite enviar datos durante una operación redirect: de esta forma los datos se mantienen en la nueva solicitud
    @PostMapping("/guardar")
    public String guardar(@Valid Usuario usuario, BindingResult result, Model model, RedirectAttributes redirect) {

        if (result.hasErrors()) {
            model.addAttribute("usuarios", service.getAllUsuarios()); // Recargamos la lista para evitar tener problemas con la vista
            model.addAttribute("errores", result.getFieldErrors().stream().map(e -> e.getDefaultMessage()).toList()); // Transforma los errores en mensajes simples

            return "usuarios";
        }

        // Si el Id es nulo es porque es un nuevo Usuario, si no, si el Id tiene un valor es porque lo está editando
        boolean nuevaCuenta = (usuario.getId_usuario() == null);

        service.saveUsuario(usuario);

        if (nuevaCuenta) {
            // Con el redirect.addFlashAttribute pasamos datos de un controlador a un controlador a otro a través de una redirección
            redirect.addFlashAttribute("success", "Tu cuenta se ha agregado correctamente " + usuario.getNombre_completo());
        } else {
            redirect.addFlashAttribute("success", usuario.getNombre_completo() + " se ha actualizado correctamente");
        }

        return "redirect:/usuarios";
    }


    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("usuario", service.getUsuariosById(id));
        model.addAttribute("usuarios", service.getAllUsuarios());
        return "usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirect) {

        Usuario usuario = service.getUsuariosById(id);

        service.deleteUsuario(id);
        redirect.addFlashAttribute("success", usuario.getNombre_completo() +" se ha eliminado correctamente");
        return "redirect:/usuarios";
    }

}
