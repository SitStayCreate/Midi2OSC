package com.SitStayCreate.MidiGrid.LEDListeners;

import com.SitStayCreate.CerealOSC.MonomeDevice.Dimensions;
import com.SitStayCreate.CerealOSC.LEDListeners.LEDLevelRowListener;
import com.SitStayCreate.MidiGrid.OSCTranslator;

import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class MGLEDLevelRowListener extends LEDLevelRowListener {
    private Dimensions dims;
    private Receiver receiver;
    private int channel;

    public MGLEDLevelRowListener(Dimensions dims, Receiver receiver, int channel){
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
    public void setLEDLevelRow(int xOffset, int gridY, int xCounter, int ledState) {
        if(dims.getWidth() == 8) {
            //xOffset should only be greater than 0 for 8x16 grids
            if(xOffset > 0){
                return;
            //8x8 grids can only support messages with 10 args, some will have 18
            } else if (xCounter >= 8) {
                return;
            }
        }

        //y cannot be more than 7 on grids with 8 rows
        if(dims.getHeight() == 8){
            if(gridY >= 8){
                return;
            }
        }

        ShortMessage shortMessage = OSCTranslator.translateGridLevelToMidi(xOffset + xCounter,
                gridY,
                ledState,
                dims,
                channel);
        receiver.send(shortMessage, -1);
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

}
