package org.kleber.app.model.autorizacao;

import org.kleber.app.model.Dao;
import org.springframework.stereotype.Repository;

@Repository
public class AutorizacaoDao extends Dao<Autorizacao> {
    AutorizacaoDao() {
        super(Autorizacao.class);
    }
}
