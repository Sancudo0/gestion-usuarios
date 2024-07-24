package com.gestion.service;

import com.gestion.entity.GestionUsuariosEntity;
import com.gestion.model.GestionUsuariosModel;
import com.gestion.repository.GestionUsuariosRepository;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

@Service
public class GestionUsuariosService {

    @Autowired
    private Environment propiedades;

    @Autowired
    private GestionUsuariosRepository repositorio;

    public ResponseEntity<Object> registrar(GestionUsuariosModel peticion) {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        if (Objects.isNull(peticion)) {

            resultado.put("codigo", "101");
            resultado.put("descripcion", "peticion vacia");

            respuesta.put("resultado",resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getNombres()) || peticion.getNombres().trim().isEmpty()) {

            resultado.put("codigo", "102");
            resultado.put("descripcion", "Ingresa el nombre");

            respuesta.put("resultado",resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }


        if (Objects.isNull(peticion.getA_paterno()) || peticion.getA_paterno().trim().isEmpty()) {

            resultado.put("codigo", "103");
            resultado.put("descripcion", "Ingresa tu apellido paterno");

            respuesta.put("resultado",resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }

        if (Objects.isNull(peticion.getA_materno()) || peticion.getA_materno().trim().isEmpty()) {

            resultado.put("codigo", "104");
            resultado.put("descripcion", "Ingresa tu apellido materno");

            respuesta.put("resultado",resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }

        if (Objects.isNull(peticion.getCorreo()) || peticion.getCorreo().trim().isEmpty()) {

            resultado.put("codigo", "105");
            resultado.put("descripcion", "Ingresa tu correo electronico");

            respuesta.put("resultado",resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);

        }

        if (Objects.isNull(peticion.getC_u()) || peticion.getC_u().trim().isEmpty()) {

            resultado.put("codigo", "106");
            resultado.put("descripcion", "Ingresa tu contrasena correcta");

            respuesta.put("resultado",resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);

        }
    Integer identificador;

        try {

            identificador = repositorio.consultar_identificador();

        } catch (Exception e) {

            resultado.put("codigo", "-54");
            resultado.put("descripcion", "No fue posible consultar el identificador.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        if (Objects.isNull(identificador)) {


            identificador = 1;

        } else {

            identificador++;

        }


        try {

        repositorio.registrar_usuarios(identificador,
                peticion.getNombres().trim(),
                peticion.getA_paterno().trim(),
                peticion.getA_materno().trim(),
                peticion.getCorreo().trim(),
                peticion.getC_u().trim());

    } catch (Exception e) {

        resultado.put("codigo", "-55");
        resultado.put("descripcion", "No fue posible registrar al usuario.");

        respuesta.put("resultado", resultado);
        return new ResponseEntity(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

    }

        resultado.put("codigo", "100");
        resultado.put("descripcion", "Usuario registrado.");

        respuesta.put("resultado", resultado);
        respuesta.put("identificador", identificador);
        return new ResponseEntity(respuesta, HttpStatus.OK);

}

    public ResponseEntity<Object> eliminar(Integer identificador) {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        try {

            Integer eliminar = repositorio.eliminar_usuario(identificador, Integer.valueOf(propiedades.getProperty("valor.estatus.inactivo")));

            if (eliminar != 1) {

                resultado.put("codigo", "101");
                resultado.put("descripcion", "Eliminacion fallida.");

                respuesta.put("resultado", resultado);
                return new ResponseEntity<>(respuesta, HttpStatus.CONFLICT);

            }

            resultado.put("codigo", "100");
            resultado.put("descripcion", "Usuario eliminado.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);

        } catch (Exception e) {

            resultado.put("codigo", "-56");
            resultado.put("descripcion", "No fue posible eliminar la informacion del usuario.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    public ResponseEntity<Object> usuarios() {

        HashMap<String, String> resultado = new LinkedHashMap<>();
        HashMap<String, Object> respuesta = new LinkedHashMap<>();

        try {

            List<GestionUsuariosEntity> usuarios = repositorio.consultar_usuarios(Integer.valueOf(propiedades.getProperty("valor.estatus.activo")));

            if (usuarios.isEmpty()) {

                resultado.put("codigo", "101");
                resultado.put("descripcion", "No se encontraron usuarios activos.");

                respuesta.put("resultado", resultado);
                return new ResponseEntity<>(respuesta, HttpStatus.OK);

            }

            resultado.put("codigo", "100");
            resultado.put("descripcion", "Peticion realizada con exito.");

            respuesta.put("resultado", resultado);
            respuesta.put("usuarios", usuarios);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);

        } catch (Exception e) {

            resultado.put("codigo", "-57");
            resultado.put("descripcion", "No fue posible consultar la informacion de los usuarios.");

            respuesta.put("resultado", resultado);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
