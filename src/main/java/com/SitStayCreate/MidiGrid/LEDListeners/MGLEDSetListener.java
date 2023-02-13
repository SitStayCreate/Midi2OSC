package com.SitStayCreate.MidiGrid.LEDListeners;

import com.SitStayCreate.MidiGrid.OSCTranslator;
import com.SitStayCreate.CerealOSC.MonomeDevice.Dimensions;
import com.SitStayCreate.CerealOSC.LEDListeners.LEDSetListener;

import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class MGLEDSetListener extends LEDSetListener {
    private Dimensions dims;
    private Receiver receiver;
    private int channel;

    public MGLEDSetListener(Dimensions dims, Receiver receiver, int channel){
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
    public void setLEDState(int x, int y, int z) {

        //prevents index out of bounds errors when the grid height is < 16
        if(dims.getHeight() == 8){
            if(y >= 8){
                return;
            }
        }

        //prevents index out of bounds errors when the grid width is < 16
        if(dims.getWidth() == 8){
            if(x >= 8){
                return;
            }
        }

        ShortMessage shortMessage = OSCTranslator.translateGridLedToMidi(x, y, z, dims, channel);
        receiver.send(shortMessage, -1);
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }
}
