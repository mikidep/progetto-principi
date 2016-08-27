package com.depvin.pps.dao;

import com.depvin.pps.dbinterface.DBInterface;
import com.depvin.pps.model.Articolo;
import com.depvin.pps.model.Progetto;
import com.depvin.pps.model.RichiestaArticolo;
import com.depvin.pps.model.Sede;

import java.util.List;

public class RichiestaArticoloDAO {
    public static List<RichiestaArticolo> getArticoliRichiestiPerSede(Sede s) {
        return DBInterface.getInstance().getEntityManager()
                .createQuery("SELECT ra FROM RichiestaArticolo ra WHERE ra.progetto.sede.id = :sId", RichiestaArticolo.class)
                .setParameter("sId", s.getId())
                .getResultList();
    }

    public static List<Articolo> getArticoliRichiestiDisponibiliPerProgetto(Progetto pr) {
        return DBInterface.getInstance().getEntityManager()
                .createQuery("SELECT DISTINCT ra.articolo FROM RichiestaArticolo ra JOIN ra.articolo.inMagazzino am" +
                        " WHERE ra.progetto.id = :pId" +
                        " AND am.disponibilita >= ra.quantita", Articolo.class)
                .setParameter("pId", pr.getId())
                .getResultList();
    }
}
