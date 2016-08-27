package com.depvin.pps.dao;

import com.depvin.pps.dbinterface.DBInterface;
import com.depvin.pps.model.Articolo;
import com.depvin.pps.model.Progetto;
import com.depvin.pps.model.RichiestaArticolo;
import com.depvin.pps.model.Sede;
import org.junit.Test;

import java.util.List;

public class RichiestaArticoloDAOTest {
    @Test
    public void getArticoliRichiestiPerSedeTest() {
        Sede sede = DBInterface.getInstance().getEntityManager().find(Sede.class, (long)6);
        List<RichiestaArticolo> lr = RichiestaArticoloDAO.getArticoliRichiestiPerSede(sede);

        for (RichiestaArticolo ra: lr) {
            System.out.println(ra.getArticolo().getNome() + " @ " + ra.getProgetto().getNome() + "(" + ra.getQuantita() + ")");
        }
    }

    @Test
    public void getArticoliRichiestiDisponibiliPerProgettoTest() {
        Progetto progetto = DBInterface.getInstance().getEntityManager().find(Progetto.class, (long)1);
        List<Articolo> lr = RichiestaArticoloDAO.getArticoliRichiestiDisponibiliPerProgetto(progetto);

        for (Articolo a: lr) {
            System.out.println(a.getId() + " - " + a.getNome() + ": " + a.getDescrizione());
        }    }
}
