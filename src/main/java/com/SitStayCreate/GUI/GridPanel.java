package com.SitStayCreate.GUI;

import com.SitStayCreate.GUI.ActionListeners.SBActionListener;
import com.SitStayCreate.CerealOSC.RequestServer.RequestServer;
import com.SitStayCreate.Constants;

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel {

    private final JLabel errorLabel;
    private final JTextField portInTextField;

    public GridPanel(MidiPanel midiPanel,
                     int font,
                     RequestServer requestServer,
                     DTPane devicePane) {
        int bigFont = font;

        Font labelFont = new Font(Font.MONOSPACED, Font.PLAIN, font);
        Font tfieldFont = new Font(Font.MONOSPACED, Font.PLAIN, bigFont);
        Font buttonFont = new Font(Font.MONOSPACED, Font.PLAIN, font);

        setBackground(Color.DARK_GRAY);
        GridBagLayout bagLayout = new GridBagLayout();
        setLayout(bagLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 5, 0);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        //OSC Port In
        JLabel portInLabel = new JLabel(Constants.PORT_IN_LABEL);
        portInLabel.setForeground(Color.WHITE);
        portInLabel.setFont(labelFont);
        constraints.gridx = 0;
        constraints.gridy = 0;
        bagLayout.setConstraints(portInLabel, constraints);
        add(portInLabel);

        portInTextField = new JTextField(Constants.DEFAULT_PORT_NUMBER);
        portInTextField.setFont(tfieldFont);
        portInTextField.setColumns(6);
        constraints.gridx = 1;
        constraints.gridy = 0;
        bagLayout.setConstraints(portInTextField, constraints);
        add(portInTextField);

        //Create Button
        JButton createButton = new JButton(Constants.CREATE_BUTTON_LABEL);
        createButton.setFont(buttonFont);
        createButton.setPreferredSize(new Dimension(Constants.CREATE_BUTTON_WIDTH, Constants.CREATE_BUTTON_HEIGHT));
        createButton.addActionListener(new SBActionListener(this,
                midiPanel,
                requestServer,
                devicePane));
        constraints.insets = new Insets(0, 10, 5, 0);
        constraints.gridx = 2;
        constraints.gridy = 0;
        bagLayout.setConstraints(createButton, constraints);
        add(createButton);

        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(labelFont);
        constraints.gridwidth = 2;
        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 10, 5, 0);
        bagLayout.setConstraints(errorLabel, constraints);
        add(errorLabel);
    }

    public int getPortIn(){
        return Integer.parseInt(portInTextField.getText());
    }

    //TODO: more getters for getting data from components

    public void setErrorLabel(String text){
        errorLabel.setText(text);
        errorLabel.setVisible(true);
    }

    public void clearErrorLabel(){
        errorLabel.setVisible(false);
    }
}
