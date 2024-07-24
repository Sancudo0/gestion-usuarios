package com.gestion.controller;

import com.gestion.model.GestionUsuariosModel;
import com.gestion.service.GestionUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/middleware")
public class GestionUsuariosController {

    @Autowired
    private GestionUsuariosService servicio;

    @PostMapping("/usuarios")
    public ResponseEntity<Object>registrar(@RequestBody(required = false) GestionUsuariosModel peticion) {

        return servicio.registrar(peticion);
    }
    @DeleteMapping("/usuarios/{identificador}")
    public ResponseEntity<Object> eliminar(@PathVariable Integer identificador) {

        return servicio.eliminar(identificador);

    }

    @GetMapping("/usuarios")
    public ResponseEntity<Object> usuarios() {

        return servicio.usuarios();

    }

}

