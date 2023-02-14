package com.SitStayCreate.MidiGrid.LEDListeners;

import com.SitStayCreate.CerealOSC.MonomeDevice.Dimensions;
import com.SitStayCreate.CerealOSC.LEDListeners.LEDLevelMapListener;
import com.SitStayCreate.MidiGrid.OSCTranslator;

import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import java.util.List;

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

    public void setChannel(int channel) {
        this.channel = channel;
    }

    @Override
    public void setLEDLevelMap(List oscList) {
        int xOffset = (int) oscList.get(0);
        int yOffset = (int) oscList.get(1);
        //xOffset should only be greater than 0 for 16x* grids
        if((dims.getWidth()) == 8) {
            if(xOffset > 0){
                return;
            }
        }

        //yOffset should only be greater than 0 for *x16 grids
        if((dims.getHeight()) == 8) {
            if(yOffset > 0){
                return;
            }
        }
        // loop through the list and send midi messages to light LEDs
        for(int i = 2; i < oscList.size(); i++){
            // Subtract 2 so count == 0
            int count = i - 2;
            int xCounter = count % 8; // increments to 7 then resets
            int yCounter = (int) Math.floor(count / 8); // 0-7 = 0, 8-15 = 1, ...

            int x = xOffset + xCounter;
            int y = yOffset + yCounter;

            ShortMessage shortMessage = OSCTranslator.translateGridLevelToMidi(x,
                    y,
                    (int) oscList.get(i),
                    dims, channel);
            receiver.send(shortMessage, -1);
        }
    }
}
