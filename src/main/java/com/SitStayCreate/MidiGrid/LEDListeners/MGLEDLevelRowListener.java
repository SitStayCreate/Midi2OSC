package com.SitStayCreate.MidiGrid.LEDListeners;

import com.SitStayCreate.CerealOSC.MonomeDevice.Dimensions;
import com.SitStayCreate.CerealOSC.LEDListeners.LEDLevelRowListener;
import com.SitStayCreate.MidiGrid.OSCTranslator;

import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import java.util.List;

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

    public void setChannel(int channel) {
        this.channel = channel;
    }

    @Override
    public void setLEDLevelRow(List oscList) {
        int xOffset = (int) oscList.get(0);
        int y = (int) oscList.get(1);
        if(dims.getWidth() == 8) {
            //xOffset should only be greater than 0 for *x16 grids
            if(xOffset > 0){
                return;
            }
        }
        for (int i = 2; i < oscList.size(); i++){
            // Subtract 2 from i so count starts at 0
            int count = i - 2;
            int x = xOffset + count;
            ShortMessage shortMessage = OSCTranslator.translateGridLevelToMidi(x,
                    y,
                    (int) oscList.get(i),
                    dims,
                    channel);
            receiver.send(shortMessage, -1);
        }
    }
}
