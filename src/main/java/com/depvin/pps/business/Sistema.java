package com.depvin.pps.business;

import com.depvin.pps.dao.UtenteDAO;
import com.depvin.pps.model.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by costantino on 05/12/15.
 */
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
    }

    void rimuoviArticoloOrdine(Ordine ordine, ArticoloOrdine articoloOrdine) {
        ordine.getArticoliOrdine().remove(articoloOrdine);
    }

    void rimuoviOrdine(Ordine ordine) {
        Dipendente d = ordine.getDipendente();
        d.getOrdini().remove(ordine);
    }

    void confermaOrdine(Ordine ordine) {
        int t = 0;
        for (ArticoloOrdine ao : ordine.getArticoliOrdine()) {
            if (!ao.isDisponibile())
                t++;
        }
        if (t == 0) {
            if (ordine.getTotale() <= ordine.getProgetto().getBudget()) {
                float appoggio = ordine.getProgetto().getBudget() - ordine.getTotale();
                ordine.getProgetto().setBudget(appoggio);
                ordine.setEvaso(true);
            }
        }
        for (ArticoloOrdine ao : ordine.getArticoliOrdine()) {
            for (ArticoloMagazzino am : ao.getMagazzino().getArticoliMagazzino()) {
                if (ao.getArticolo().equals(am.getArticolo())) {
                    int appoggioqt = am.getDisponibilita() - ao.getQuantita();
                    am.setDisponibilita(appoggioqt);
                }
            }
        }
    }

    void modificaBudget(Progetto progetto, float budget) {
        float variab = progetto.getBudget() + budget;
        if (variab >= 0)
            progetto.setBudget(variab);
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

    ArrayList<ArticoloOrdine> ottieniListaOrdine(Ordine ordine) {
        ArrayList<ArticoloOrdine> lista = new ArrayList<ArticoloOrdine>();
        for (ArticoloOrdine ao : ordine.getArticoliOrdine())
            lista.add(ao);
        return lista;
    }

    ArrayList<ArticoloOrdine> ottieniListaDipendente(Dipendente dipendente, Progetto progetto) {
        ArrayList<ArticoloOrdine> lista = new ArrayList<ArticoloOrdine>();
        for (Ordine o : progetto.getOrdini())
            if (dipendente == o.getDipendente())
                for (ArticoloOrdine ao : o.getArticoliOrdine())
                    lista.add(ao);
        return lista;
    }

    void richiediNotifica(ArticoloOrdine articoloOrdine) {
        articoloOrdine.setRichiesto(true);
    }

    void aggiungiDipendente(String name, String surname, String username, String password)
            throws UserExistsException, UserLoadingException {
        try {
            Dipendente dip = UtenteDAO.getNewDipendente(username, hashPassword(password));
            dip.setCognome(surname);
            dip.setNome(name);
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
        } catch (com.depvin.pps.dao.UserAlreadyExistsException e) {
            throw new UserExistsException(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(System.err);
            throw new UserLoadingException(e.getMessage(), e);
        }
    }

    void modificaQuantitàArticolo(ArticoloMagazzino articoloMagazzino, int quantità) {
        articoloMagazzino.setDisponibilita(quantità);
    }

    void aggiungiOrdineProgetto(Ordine ordine, Progetto progetto) {
        progetto.getOrdini().add(ordine);
    }

    void eliminaArticoloMagazzino(ArticoloMagazzino articoloMagazzino, Magazzino magazzino) {
        if (articoloMagazzino.getDisponibilita() == 0)
            magazzino.getArticoliMagazzino().remove(articoloMagazzino);
    }// Deve inoltre avvisare il dipendente che il prodotto non sarà più disponibile in quel magazzino

    public Sessione login(String username, String password) throws UserNotFoundException, UserLoadingException {
        try {
            Utente utente = UtenteDAO.getUtenteWithUsernameAndHash(username, hashPassword(password));
            if (utente instanceof Dipendente) {
                ((Dipendente) utente).addListener(new Dipendente.NotificaArticoloListener() {
                    public void articoloOrdineIsDisponibile(ArticoloOrdine articoloOrdine, Magazzino magazzino) {

                        // TODO: Chiamerà il Presenter
                    }
                });
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

}