package com.SitStayCreate.MidiGrid.LEDListeners;

import com.SitStayCreate.CerealOSC.MonomeDevice.Dimensions;
import com.SitStayCreate.CerealOSC.LEDListeners.LEDColListener;
import com.SitStayCreate.MidiGrid.OSCTranslator;

import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class MGLEDColListener extends LEDColListener {
    private Dimensions dims;
    private Receiver receiver;
    private int channel;

    public MGLEDColListener(Dimensions dims, Receiver receiver, int channel){
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
    public void setLEDColState(String binaryString, int x, int yOffset, int yCounter) {

        //ignore second argument for 8x8 grids - this is for leds 8 15
        if(dims.getHeight() == 8) {
            if(yOffset > 0){   //yOffset should only be greater than 0 for 8x16 grids
                return;
            } else if (yCounter >= 8) { //8x8/16 grids can only support messages with 10 args, some will have 18
                return;
            }
        }

        //Add leading 0s if fewer than 8 bits
        for (int j = 7; j >= binaryString.length(); j--) {
            ShortMessage shortMessage = OSCTranslator.translateGridLedToMidi(x,
                    yOffset + yCounter,
                    0,
                    dims, channel);

            receiver.send(shortMessage, -1);

            yCounter++;
        }

        byte[] bytes = binaryString.getBytes();

        //add data from message
        for (int j = 0; j < binaryString.length(); j++) {
            byte b = bytes[j];
            int gridZ = 0;
            if ((b & 1) != 0) {
                gridZ = 1;
            }

            ShortMessage shortMessage = OSCTranslator.translateGridLedToMidi(x,
                    yOffset + yCounter,
                    gridZ,
                    dims, channel);

            receiver.send(shortMessage, -1);
            yCounter++;
        }
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }
}
