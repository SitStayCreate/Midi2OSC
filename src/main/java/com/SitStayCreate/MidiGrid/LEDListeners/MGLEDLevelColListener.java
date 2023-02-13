package com.SitStayCreate.MidiGrid.LEDListeners;

import com.SitStayCreate.CerealOSC.MonomeDevice.Dimensions;
import com.SitStayCreate.CerealOSC.LEDListeners.LEDLevelColListener;
import com.SitStayCreate.MidiGrid.OSCTranslator;

import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class MGLEDLevelColListener extends LEDLevelColListener {

    private Dimensions dims;
    private Receiver receiver;
    private int channel;

    public MGLEDLevelColListener(Dimensions dims, Receiver receiver, int channel){
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
    public void setLEDLevelCol(int gridX, int yOffset, int ledState, int yCounter) {

        if(dims.getHeight() == 8) {
            if(yOffset > 0){   //yOffset should only be greater than 0 for 8x16 grids
                return;
            } else if (yCounter >= 8) { //8x8/16 grids can only support messages with 10 args, some will have 18
                return;
            }
        }

        ShortMessage shortMessage = OSCTranslator.translateGridLevelToMidi(gridX,
                yOffset + yCounter,
                ledState,
                dims, channel);
        receiver.send(shortMessage, -1);
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }
}
