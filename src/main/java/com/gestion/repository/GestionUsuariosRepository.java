package com.gestion.repository;

import com.gestion.entity.GestionUsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional
public interface GestionUsuariosRepository  extends JpaRepository<GestionUsuariosEntity, Integer> {

    @Query(value = "SELECT MAX(IDENTIFICADOR_INT) FROM TAB_GESTION_USUARIOS", nativeQuery = true)
    Integer consultar_identificador();

    @Modifying
    @Query(value = "INSERT INTO TAB_GESTION_USUARIOS(IDENTIFICADOR_INT, NOMBRES_VAR, A_PATERNO_VAR, A_MATERNO_VAR, CORREO_VAR, CU_VAR) VALUES(:identificador, :nombres, :a_paterno, :a_materno, :correo, :cu)", nativeQuery = true)
    void registrar_usuarios(@Param("identificador") Integer identificador,
                           @Param("nombres") String nombres,
                           @Param("a_paterno") String a_paterno,
                           @Param("a_materno") String a_materno,
                           @Param("correo") String correo,
                           @Param("cu") String c_u);

    @Modifying
    @Query(value = "UPDATE TAB_GESTION_USUARIOS SET ESTATUS_INT = :estatus WHERE IDENTIFICADOR_INT = :identificador", nativeQuery = true)
    Integer eliminar_usuario(@Param("identificador") Integer identificador, @Param("estatus") Integer estatus);

    @Query(value = "SELECT IDENTIFICADOR_INT, NOMBRES_VAR, A_PATERNO_VAR, A_MATERNO_VAR, CORREO_VAR, CU_VAR FROM TAB_GESTION_USUARIOS WHERE ESTATUS_INT = :estatus", nativeQuery = true)
    List<GestionUsuariosEntity> consultar_usuarios(@Param("estatus") Integer estatus);
}