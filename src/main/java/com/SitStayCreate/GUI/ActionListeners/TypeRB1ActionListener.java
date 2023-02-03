package com.SitStayCreate.GUI.ActionListeners;

import com.SitStayCreate.GUI.MidiPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TypeRB1ActionListener implements ActionListener {

    private MidiPanel midiPanel;

    public TypeRB1ActionListener(MidiPanel midiPanel) {
        setMidiPanel(midiPanel);
    }

    private void setMidiPanel(MidiPanel midiPanel) {
        this.midiPanel = midiPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        midiPanel.setEnabled(true);
    }
}
