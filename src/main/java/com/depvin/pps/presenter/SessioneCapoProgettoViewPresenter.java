package com.depvin.pps.presenter;

import com.depvin.pps.business.SessioneCapoProgetto;
import com.depvin.pps.model.CapoProgetto;
import com.depvin.pps.model.Progetto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by costantino on 24/05/16.
 */
public class SessioneCapoProgettoViewPresenter {
    SessioneCapoProgetto sessione;

    private JFrame view;
    private JPanel rootPanel;
    private JList list1;
    private JButton confermaButton;
    private JComboBox comboBox1;


    public SessioneCapoProgettoViewPresenter(SessioneCapoProgetto sessione) {
        this.sessione = sessione;
        CapoProgetto cp = sessione.getUtente();
        view = new JFrame("Sessione: " + cp.getNome() + " " + cp.getCognome());
        rootPanel.setPreferredSize(new Dimension(300, 300));
        view.setLocation(300, 300);

        final DefaultListModel listModel;
        listModel = new DefaultListModel();
        List<Progetto> prog = cp.getProgetti();
        listModel.addElement(prog);

        list1 = new JList(listModel);
        list1.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list1.setVisible(true);

        view.setContentPane(rootPanel);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        view.pack();

        confermaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {

                //if(actionEvent.getSource() == confermaButton) {
                //int index = list1.getSelectedIndex();
                //System.out.println(index);
                //Progetto p = (Progetto) listModel.getElementAt(index);
                //}
            }
        });
    }

    public void show() {
        view.setVisible(true);
    }

    public JFrame getView() {
        return view;
    }


}
