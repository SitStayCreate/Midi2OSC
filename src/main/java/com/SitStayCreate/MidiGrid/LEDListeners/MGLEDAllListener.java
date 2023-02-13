package com.SitStayCreate.MidiGrid.LEDListeners;

import com.SitStayCreate.CerealOSC.MonomeDevice.Dimensions;
import com.SitStayCreate.CerealOSC.LEDListeners.LEDAllListener;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

//TODO: support 16 channels
public class MGLEDAllListener extends LEDAllListener {

    private Dimensions dims;
    private Receiver receiver;
    private int channel;

    public MGLEDAllListener(Dimensions dims, Receiver receiver, int channel){
        setDims(dims);
        setReceiver(receiver);
        setChannel(channel);
    }

    public void setDims(Dimensions dims) {
        this.dims = dims;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public Dimensions getDims() {
        return dims;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    @Override
    public void setLedAllState(int s) {
        int status = 144;
        //Sets all LEDs off or all LEDs on
        for(int i = 0; i < dims.getArea(); i++){
            try {
                ShortMessage shortMessage = new ShortMessage(channel + status, i, s);
                receiver.send(shortMessage, -1);
            } catch(InvalidMidiDataException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }
}
