package com.SitStayCreate.GUI;

import com.SitStayCreate.MidiGrid.MidiGridAdapter;
import com.SitStayCreate.Constants;

import javax.swing.*;
import java.awt.*;

public class DTPane extends JScrollPane {
    private DevicesTable devicesTable;

    public DTPane(DevicesTable devicesTable) {
        super(devicesTable);
        setMaximumSize(new Dimension(Constants.DTPANE_WIDTH, Constants.DTPANE_HEIGHT));
        getViewport().setBackground(Color.GRAY);
        setDevicesTable(devicesTable);
    }

    public void addRow(MidiGridAdapter grid){
        devicesTable.addRow(grid);
        updateUI();
    }

    public void dropRow(int index){
        //TODO: Remove rows from the table when closing
        devicesTable.remove(index);
    }

    public void setDevicesTable(DevicesTable devicesTable) {
        this.devicesTable = devicesTable;
    }
}
