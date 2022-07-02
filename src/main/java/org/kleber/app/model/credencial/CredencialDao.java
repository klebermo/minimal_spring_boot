package org.kleber.app.model.credencial;

import org.kleber.app.model.Dao;
import org.springframework.stereotype.Repository;

@Repository
public class CredencialDao extends Dao<Credencial> {
    CredencialDao() {
        super(Credencial.class);
    }
}
