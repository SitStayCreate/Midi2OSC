package com.SitStayCreate.MidiGrid.LEDListeners;

import com.SitStayCreate.CerealOSC.MonomeDevice.Dimensions;
import com.SitStayCreate.CerealOSC.LEDListeners.LEDLevelMapListener;
import com.SitStayCreate.MidiGrid.OSCTranslator;

import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class MGLEDLevelMapListener extends LEDLevelMapListener {

    private Dimensions dims;
    private Receiver receiver;
    private int channel;

    public MGLEDLevelMapListener(Dimensions dims, Receiver receiver, int channel){
        setDims(dims);
        setReceiver(receiver);
        setChannel(channel);
    }

    public Dimensions getDims() {
        return dims;
    }

    public void setDims(Dimensions dims) {
        this.dims = dims;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public int getChannel() {
        return channel;
    }

    @Override
    public void setLEDLevelMap(int xOffset, int yOffset, int ledState, int counter) {
        //xOffset should only be greater than 0 for *x16 grids
        if((dims.getWidth()) == 8) {
            if(xOffset > 0){
                return;
            }
        }

        //yOffset should only be greater than 0 for 16x* grids
        if((dims.getHeight()) == 8) {
            if(yOffset > 0){
                return;
            }
        }

        int xCounter = counter % 8; // increments to 7 then resets
        int yCounter = (int) Math.floor(counter / 8); // 0-7 = 0, 8-15 = 1, ...

        int x = xOffset + xCounter;
        int y = yOffset + yCounter;

        ShortMessage shortMessage = OSCTranslator.translateGridLevelToMidi(x,
                y,
                ledState,
                dims, channel);
        receiver.send(shortMessage, -1);
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }
}
