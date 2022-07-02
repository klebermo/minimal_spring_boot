package org.kleber.app.model.usuario;

import org.kleber.app.model.Dao;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDao extends Dao<Usuario> {
    UsuarioDao() {
        super(Usuario.class);
    }
}
