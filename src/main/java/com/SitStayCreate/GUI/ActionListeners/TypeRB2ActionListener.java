package com.SitStayCreate.GUI.ActionListeners;

import com.SitStayCreate.GUI.MidiPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TypeRB2ActionListener implements ActionListener {
    private MidiPanel midiPanel;
    public TypeRB2ActionListener(MidiPanel midiPanel) {
        setMidiPanel(midiPanel);
    }

    public void setMidiPanel(MidiPanel midiPanel) {
        this.midiPanel = midiPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        midiPanel.setEnabled(false);
    }
}
