package com.SitStayCreate.GUI.ActionListeners;

import com.SitStayCreate.GUI.DevicesTable;
import com.SitStayCreate.CerealOSC.MonomeDevice.MonomeController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DropActionListener implements ActionListener {

    private MonomeController monomeController;
    private DevicesTable devicesTable;

    // TODO: Request server call notify listener

    public DropActionListener(DevicesTable devicesTable, MonomeController monomeController) {
        setMonomeController(monomeController);
        setDevicesTable(devicesTable);
    }

    private void setMonomeController(MonomeController monomeController) {
        this.monomeController = monomeController;
    }

    private void setDevicesTable(DevicesTable devicesTable) {
        this.devicesTable = devicesTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO: Delete grids from the Server, notify targetApps, free up grid resources,
        // remove from DevicesTable
        monomeController.close();
        devicesTable.dropRow(monomeController.getId());
    }
}
