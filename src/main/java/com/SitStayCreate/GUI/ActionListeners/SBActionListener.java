package com.SitStayCreate.GUI.ActionListeners;

import com.SitStayCreate.GUI.DTPane;
import com.SitStayCreate.GUI.GridPanel;
import com.SitStayCreate.GUI.MidiPanel;
import com.SitStayCreate.MidiGrid.HardwareDevice;
import com.SitStayCreate.MidiGrid.MidiGridAdapter;
import com.SitStayCreate.Serialosc.*;
import com.SitStayCreate.Constants;

import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SBActionListener implements ActionListener {
    private GridPanel gridPanel;
    private MidiPanel midiPanel;
    private RequestServer requestServer;
    private DTPane devicePane;

    public SBActionListener(GridPanel gridPanel,
                            MidiPanel midiPanel,
                            RequestServer requestServer,
                            DTPane devicePane) {
        setGridPanel(gridPanel);
        setMidiPanel(midiPanel);
        setRequestServer(requestServer);
        setDevicePane(devicePane);
    }

    public void setGridPanel(GridPanel gridPanel) {
        this.gridPanel = gridPanel;
    }

    public void setMidiPanel(MidiPanel midiPanel) {
        this.midiPanel = midiPanel;
    }

    public void setRequestServer(RequestServer requestServer) {
        this.requestServer = requestServer;
    }

    public void setDevicePane(DTPane devicePane) {
        this.devicePane = devicePane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Validate portIn is not already in use by another grid or the system
        int portIn = gridPanel.getPortIn();

        List<GridController> controllers = requestServer.getGridControllers();
        //Check that the port is not already in use, if it is, do not create a device
        for(MonomeController controller : controllers){
            if(controller.getDecoratedOSCPortIn().getPortIn() == portIn){
                gridPanel.setErrorLabel(Constants.ERROR_LABEL);
                return;
            }
        }

        gridPanel.clearErrorLabel();


        HardwareDevice hardwareDevice = midiPanel.createHardwareDevice();

        //create OscDevice
        try {
            MidiGridAdapter grid = new MidiGridAdapter(new MonomeApp(Constants.DEFAULT_PORT),
                    midiPanel.getDims(),
                    portIn,
                    hardwareDevice);

            //give the request server a reference to the oscDevice to serve to apps
            requestServer.addMonomeController(grid);
            //Notify apps that a new device exists
            requestServer.notifyListeners(grid);

            devicePane.addRow(grid);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
