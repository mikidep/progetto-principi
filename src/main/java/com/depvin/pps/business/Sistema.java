package com.depvin.pps.business;

import com.depvin.pps.dao.*;
import com.depvin.pps.dbinterface.DBInterface;
import com.depvin.pps.model.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Sistema {

    private static Sistema ourInstance;

    public static synchronized Sistema getInstance() {
        if (ourInstance == null)
            ourInstance = new Sistema();
        return ourInstance;
    }

    void aggiungiProgetto(String nome, Sede sede, float budget, CapoProgetto capoProgetto) {
        Progetto progetto = new Progetto(nome, budget, sede);
        capoProgetto.getProgetti().add(progetto);
        DBInterface.getInstance().save();
    }

    void aggiungiArticoloMagazzino(ArticoloMagazzino articoloMagazzino, Magazzino magazzino) {
        magazzino.getArticoliMagazzino().add(articoloMagazzino);
        DBInterface.getInstance().save();
    }

    void aggiungiCategoriaArticolo(ArticoloMagazzino articoloMagazzino, String nomeCategoria) {
        Categoria categoria = new Categoria(nomeCategoria);
        articoloMagazzino.getArticolo().getProdotto().getCategorie().add(categoria);
        DBInterface.getInstance().save();
    }

    void rimuoviArticoloOrdine(Ordine ordine, ArticoloOrdine articoloOrdine) {
        ordine.getArticoliOrdine().remove(articoloOrdine);
        articoloOrdine.setMagazzino(null);
        DBInterface.getInstance().save();
    }

    void rimuoviOrdine(Ordine ordine, Progetto progetto) {
       /* Dipendente d = ordine.getDipendente();
        d.getOrdini().remove(ordine);*/
        progetto.getOrdini().remove(ordine);
        DBInterface.getInstance().save();
    }

    void inviaOrdine(Ordine ordine) throws SendOrderException {
        int t = 0;
        for (ArticoloOrdine ao : ordine.getArticoliOrdine()) {
            if (!ao.isDisponibile())
                t++;
        }
        if (t == 0) {
            ordine.setInviato(true);
            DBInterface.getInstance().save();
        } else {
            throw new SendOrderException("Non tutti gli articoli dell'ordine sono disponibili");
        }
    }

    void evadiOrdine(Ordine ordine, Magazzino magazzino) throws EvasionException {

        List<ArticoloOrdine> listao = new ArrayList<ArticoloOrdine>();
        for (ArticoloOrdine ao : ordine.getArticoliOrdine())
            if (ao.getMagazzino().getNome().equals(magazzino.getNome()))
                listao.add(ao);

        float t = 0.0f;
        Set<Sede> sedi = new HashSet<Sede>();
        for (ArticoloOrdine ao : listao) {
            t += ao.getParziale();
            sedi.add(ao.getMagazzino().getSede());
        }
        for (Sede s : sedi) {
            t += s.calcolaSpedizionePer(ordine.getProgetto().getSede());
        }

        boolean evaso = true;
        for (ArticoloOrdine ao : listao) {
            evaso = evaso && ao.isEvaso();
        }

        if (t <= ordine.getProgetto().getBudget() && !evaso) {
            float appoggio = ordine.getProgetto().getBudget() - t;
            ordine.getProgetto().setBudget(appoggio);
            for (ArticoloOrdine ao : listao)
                for (ArticoloMagazzino am : ao.getMagazzino().getArticoliMagazzino()) {
                    if (ao.getArticolo().equals(am.getArticolo())) {
                        int appoggioqt = am.getDisponibilita() - ao.getQuantita();
                        am.setDisponibilita(appoggioqt);
                        ao.setEvaso(true);
                        DBInterface.getInstance().save();
                    }
                }
        } else {
            throw new EvasionException("Budget del progetto non sufficiente per evadere l'ordine");
        }
    }

    void aggiungiImmagineArticolo(Articolo articolo, byte[] bytes) {
        articolo.setImmagine(bytes);
        DBInterface.getInstance().save();
    }

    void modificaImmagineArticolo(ArticoloMagazzino articoloMagazzino, byte[] bytes) {
        articoloMagazzino.getArticolo().setImmagine(bytes);
        DBInterface.getInstance().save();
    }

    void modificaBudget(Progetto progetto, float budget) {
        if (budget >= 0)
            progetto.setBudget(budget);
        DBInterface.getInstance().save();
    }

    void modificaNomeArticolo(ArticoloMagazzino articoloMagazzino, String nome) {
        articoloMagazzino.getArticolo().setNome(nome);
        DBInterface.getInstance().save();
    }

    void modificaDescrizioneArticolo(ArticoloMagazzino articoloMagazzino, String descrizione) {
        articoloMagazzino.getArticolo().setDescrizione(descrizione);
        DBInterface.getInstance().save();
    }

    void modificaPrezzoArticolo(ArticoloMagazzino articoloMagazzino, float prezzo) {
        articoloMagazzino.getArticolo().setPrezzo(prezzo);
        DBInterface.getInstance().save();
    }

    void modificaFornitoreArticolo(ArticoloMagazzino articoloMagazzino, String nuovoFornitore, String vecchioFornitore) {
        List<Fornitore> listF = articoloMagazzino.getArticolo().getFornitori();
        List<Articolo> lista = ottieniListaArticoli();
        Fornitore fornitore;
        int i = -1;
        int g = -1;
        int k = -1;
        for (Articolo a : lista)
            for (Fornitore f : a.getFornitori())
                if (f.getNome().equals(nuovoFornitore)) {
                    i++;
                    g = a.getFornitori().indexOf(f);
                    k = lista.indexOf(a);
                }
        if (i > -1)
            fornitore = lista.get(k).getFornitori().get(g);
        else
            fornitore = new Fornitore(nuovoFornitore);
        for (Fornitore f : listF)
            if (f.getNome().equals(vecchioFornitore)) {
                listF.remove(f);
                listF.add(fornitore);
            }
        DBInterface.getInstance().save();
    }

    void modificaCategoriaArticolo(ArticoloMagazzino articoloMagazzino, String nomeCategoria) {
        List<Categoria> listC = articoloMagazzino.getArticolo().getProdotto().getCategorie();
        Categoria cat = new Categoria(nomeCategoria);
        listC.clear();
        int index = -10;
        for (Categoria cate : ottieniListaCategorie())
            if (cate.getNome().equals(cat.getNome())) {
                index = ottieniListaCategorie().indexOf(cate);
                //break;
            }
        if (index == -10)
            articoloMagazzino.getArticolo().getProdotto().getCategorie().add(cat);
        else
            articoloMagazzino.getArticolo().getProdotto().getCategorie().add(ottieniListaCategorie().get(index));
        DBInterface.getInstance().save();
    }

    void modificaProdottoArticolo(ArticoloMagazzino articoloMagazzino, String nomeProdotto, List<Categoria> listC) {
        Prodotto prodotto;
        List<Articolo> lista = ottieniListaArticoli();
        int i = -1;
        int k = -1;
        for (Articolo a : lista)
            if (a.getProdotto().getNome().equals(nomeProdotto)) {
                i++;
                k = lista.indexOf(a);
            }
        if (i > -1)
            prodotto = lista.get(k).getProdotto();
        else
            prodotto = new Prodotto(nomeProdotto, listC);
        articoloMagazzino.getArticolo().setProdotto(prodotto);
        DBInterface.getInstance().save();
    }

    void modificaProduttoreArticolo(ArticoloMagazzino articoloMagazzino, String nomeProduttore) {
        List<Articolo> lista = ottieniListaArticoli();
        int h = -1;
        int i = -1;
        Produttore produttore;
        for (Articolo a : lista)
            if (a.getProduttore().getNome().equals(nomeProduttore)) {
                h++;
                i = lista.indexOf(a);
            }
        if (h > -1)
            produttore = lista.get(i).getProduttore();
        else
            produttore = new Produttore(nomeProduttore);
        articoloMagazzino.getArticolo().setProduttore(produttore);
        DBInterface.getInstance().save();
    }

    void aggiungiFornitoreArticolo(ArticoloMagazzino articoloMagazzino, String nomeFornitore) {
        List<Articolo> lista = ottieniListaArticoli();
        int h = -1;
        int i = -1;
        int g = -1;
        Fornitore fornitore;
        for (Articolo a : lista)
            for (Fornitore f : a.getFornitori())
                if (f.getNome().equals(nomeFornitore)) {
                    h++;
                    i = lista.indexOf(a);
                    g = a.getFornitori().indexOf(f);
                }
        if (h > -1)
            fornitore = lista.get(i).getFornitori().get(g);
        else
            fornitore = new Fornitore(nomeFornitore);
        articoloMagazzino.getArticolo().getFornitori().add(fornitore);
        DBInterface.getInstance().save();
    }

    List<Articolo> ottieniListaArticoli() {
        List<Categoria> listC = ottieniListaCategorie();
        List<Articolo> listF = new ArrayList<Articolo>();
        for (Categoria c : listC)
            for (Articolo a : Sistema.getInstance().ottieniListaArticoliPerCategoria(c))
                if (!listF.contains(a))
                    listF.add(a);
        return listF;
    }

    ByteArrayOutputStream articoliToPDFBytes(String intestazione, List<ArticoloOrdine> aolist) throws ReportCreationFailedException {
        float sped = 0.0f;
        float pt = 0.0f;
        int qt = 0;
        String htmlOut = "<!DOCTYPE html> <html> <head> <style> table, th, td " +
                "{ border: 1px solid black; border-collapse: collapse;}" +
                " th, td { padding: 5px; text-align: left;} </style> </head> <body> " +
                "<h1>" + intestazione + "</h1>" +
                "<table style=\"width:100%\"> <tr> " +
                " <th>Articolo</th> <th>Quantità</th> <th> Prezzo Spedizione</th> <th>Prezzo Individuale</th> </tr> ";
        for (ArticoloOrdine ao : aolist) {
            float prezzo = ao.getParziale();
            float spedizione = ao.getMagazzino().getSede().calcolaSpedizionePer(ao.getOrdine().getProgetto().getSede());
            htmlOut += "<tr> <td>" + ao.getArticolo().getNome() + "</td> <td>" + ao.getQuantita() + "</td> <td>" +
                    String.format("%.2f", spedizione) + "€</td> <td>" + String.format("%.2f", prezzo) + "€</td> </tr>";
            pt += prezzo;
            sped += spedizione;
            qt += ao.getQuantita();
        }
        htmlOut += "<tr> <td>Totale</td> <td>" + qt + "</td> <td>" + sped + "€</td> <td>" + pt + "€</td> </tr>";
        htmlOut += "</table> </body></html>";

        Document document = new Document();
        ByteArrayOutputStream res = new ByteArrayOutputStream();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, res);
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(htmlOut));
            document.close();
            return res;
        } catch (Exception e) {
            throw new ReportCreationFailedException("Reason of failure: " + e.getMessage(), e);
        }
    }

    List<ArticoloOrdine> ottieniListaOrdine(Ordine ordine) {
        List<ArticoloOrdine> lista = new ArrayList<ArticoloOrdine>();
        for (ArticoloOrdine ao : ordine.getArticoliOrdine())
            lista.add(ao);
        return lista;
    }

    List<ArticoloOrdine> ottieniListaDipendente(Dipendente dipendente, Progetto progetto) {
        List<ArticoloOrdine> lista = new ArrayList<ArticoloOrdine>();
        for (Ordine o : progetto.getOrdini())
            if (dipendente == o.getDipendente())
                for (ArticoloOrdine ao : o.getArticoliOrdine())
                    lista.add(ao);
        return lista;
    }

    void creaNotifica(Articolo articolo, Progetto progetto, int quantita) {
        RichiestaArticolo richiestaArticolo = new RichiestaArticolo(articolo, progetto, quantita);
        progetto.getRichieste().add(richiestaArticolo);
        DBInterface.getInstance().save();
    }

    void aggiungiDipendente(String name, String surname, String username, String password)
            throws UserExistsException, UserLoadingException {
        try {
            Dipendente dip = UtenteDAO.getNewDipendente(username, hashPassword(password));
            dip.setCognome(surname);
            dip.setNome(name);
            DBInterface.getInstance().save();
        } catch (com.depvin.pps.dao.UserAlreadyExistsException e) {
            throw new UserExistsException(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(System.err);
            throw new UserLoadingException(e.getMessage(), e);
        }
    }

    void aggiungiMagazziniere(String name, String surname, Magazzino magazzino, String username, String password)
            throws UserExistsException, UserLoadingException {
        try {
            Magazziniere mag = UtenteDAO.getNewMagazziniere(username, hashPassword(password), magazzino);
            mag.setNome(name);
            mag.setCognome(surname);
            DBInterface.getInstance().save();
        } catch (com.depvin.pps.dao.UserAlreadyExistsException e) {
            throw new UserExistsException(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(System.err);
            throw new UserLoadingException(e.getMessage(), e);
        }
    }

    void aggiungiCapoProgetto(String name, String surname, String username, String Password)
            throws UserExistsException, UserLoadingException {
        try {
            CapoProgetto cap = UtenteDAO.getNewCapoProgetto(username, hashPassword(Password));
            cap.setNome(name);
            cap.setCognome(surname);
            DBInterface.getInstance().save();
        } catch (com.depvin.pps.dao.UserAlreadyExistsException e) {
            throw new UserExistsException(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(System.err);
            throw new UserLoadingException(e.getMessage(), e);
        }
    }

    void aggiungiAmministratore(String name, String surname, String username, String password)
            throws UserExistsException, UserLoadingException {
        try {
            Amministratore amm = UtenteDAO.getNewAmministratore(username, hashPassword(password));
            amm.setNome(name);
            amm.setCognome(surname);
            DBInterface.getInstance().save();
        } catch (com.depvin.pps.dao.UserAlreadyExistsException e) {
            throw new UserExistsException(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(System.err);
            throw new UserLoadingException(e.getMessage(), e);
        }
    }

    void modificaQuantitàArticolo(ArticoloMagazzino articoloMagazzino, int quantità, Magazzino magazzino) {
        articoloMagazzino.setDisponibilita(quantità);
        if (quantità == 0) {
            List<ArticoloMagazzino> listAM = articoloMagazzino.getArticolo().getInMagazzino();
            int index = -1;
            for (ArticoloMagazzino am : listAM)
                if (am.getMagazzino().getNome().equals(magazzino.getNome()))
                    index = listAM.indexOf(am);
            listAM.remove(listAM.get(index));
        }
        DBInterface.getInstance().save();
    }

    void aggiungiOrdineProgetto(Ordine ordine, Progetto progetto) {
        progetto.getOrdini().add(ordine);
        DBInterface.getInstance().save();
    }

    public Sessione login(String username, String password) throws UserNotFoundException, UserLoadingException {
        try {
            Utente utente = UtenteDAO.getUtenteWithUsernameAndHash(username, hashPassword(password));
            if (utente instanceof Dipendente) {
                /*((Dipendente) utente).addListener(new Dipendente.NotificaArticoloListener() {
                    public void articoloOrdineIsDisponibile(ArticoloOrdine articoloOrdine, Magazzino magazzino) {

                        // TODO: Chiamerà il Presenter
                    }
                });*/
                return new SessioneDipendente((Dipendente) utente);
            } else if (utente instanceof Amministratore) {
                return new SessioneAmministratore((Amministratore) utente);
            } else if (utente instanceof Magazziniere) {
                return new SessioneMagazziniere((Magazziniere) utente);
            } else {
                return new SessioneCapoProgetto((CapoProgetto) utente);
            }

        } catch (com.depvin.pps.dao.NoSuchUserException e) {
            throw new UserNotFoundException(e.getMessage(), e);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(System.err);
            throw new UserLoadingException(e.getMessage(), e);
        }
    }

    Utente ottieniUtente(String username, String password) throws UserNotFoundException, UserLoadingException {
        try {
            return UtenteDAO.getUtenteWithUsernameAndHash(username, hashPassword(password));
        } catch (com.depvin.pps.dao.NoSuchUserException e) {
            throw new UserNotFoundException(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(System.err);
            throw new UserLoadingException(e.getMessage(), e);
        }
    }

    private byte[] hashPassword(String password) throws NoSuchAlgorithmException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte byteData[] = md.digest();
            return byteData;
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException("No Provider supports a MessageDigestSpi implementation for the specified algorithm", e);
        }

    }

    List<CapoProgetto> ottieniListaCapoProgetto() {
        return UtenteDAO.getAllCapiProgetto();
    }

    List<Sede> ottieniListaSede() {
        return SedeDAO.getAllSedi();
    }

    List<Categoria> ottieniListaCategorie() {
        return CategoriaDAO.getAllCategorie();
    }

    List<ArticoloOrdine> ottieniListaArticoliOrdine(Magazzino magazzino) {
        return ArticoloOrdineDAO.getArticoliOrdinePerMagazzino(magazzino);
    }

    List<Articolo> ottieniListaArticoliPerCategoria(Categoria categoria) {
        return ArticoloDAO.getArticoliPerCategoria(categoria);
    }

    List<Articolo> ottieniListaArticoliPerProdotto(Prodotto prodotto) {
        return ArticoloDAO.getArticoliPerProdotto(prodotto);
    }

    List<Articolo> ottieniListaArticoliPerRicerca(String ricerca) {
        return ArticoloDAO.getArticoliPerRicerca(ricerca);
    }

    List<Prodotto> ottieniListaProdottiPerCategoria(Categoria categoria) {
        return ProdottoDAO.getProdottiPerCategoria(categoria);
    }

    void aggiungiArticoloOrdine(ArticoloOrdine articoloOrdine, Ordine ordine) {
        ordine.getArticoliOrdine().add(articoloOrdine);
        DBInterface.getInstance().save();
    }

    void modificaDisponibilitàArticoloOrdine(ArticoloOrdine articoloOrdine, int disponibilità) {
        articoloOrdine.setQuantita(disponibilità);
        DBInterface.getInstance().save();
    }

    List<RichiestaArticolo> ottieniListaRichiestaArticoliSede(Sede sede) {
        return RichiestaArticoloDAO.getArticoliRichiestiPerSede(sede);
    }

    List<Articolo> ottieniListaArticoliRichiestiProgetto(Progetto progetto) {
        return RichiestaArticoloDAO.getArticoliRichiestiDisponibiliPerProgetto(progetto);
    }

    void aggiungiDipendenteProgetto(Progetto progetto, Dipendente dipendente) {
        progetto.getDipendenti().add(dipendente);
        dipendente.getProgetti().add(progetto);
        DBInterface.getInstance().save();
    }

    void rimuoviRichiesta(RichiestaArticolo richiestaArticolo) {
        Set<RichiestaArticolo> setR = richiestaArticolo.getProgetto().getRichieste();
        setR.remove(richiestaArticolo);
        DBInterface.getInstance().save();
    }

    void sistemaSave() {
        DBInterface.getInstance().save();
    }

}