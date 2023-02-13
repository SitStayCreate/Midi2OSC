package com.SitStayCreate.CerealOSC.MonomeDevice;

import com.SitStayCreate.CerealOSC.OSC.DecoratedOSCPortIn;
import com.SitStayCreate.CerealOSC.OSC.DecoratedOSCPortOut;
import com.SitStayCreate.CerealOSC.MonomeApp.MonomeApp;

import java.io.IOException;

public abstract class MonomeController {

    protected DecoratedOSCPortIn decoratedOSCPortIn;
    protected DecoratedOSCPortOut decoratedOSCPortOut;
    protected String id, prefix;
    //Where are we calling setMonomeApp in the old version?
    private MonomeApp monomeApp;
    private static int count = 1;

    public MonomeController(MonomeApp monomeApp,
                            DecoratedOSCPortIn decoratedOSCPortIn,
                            DecoratedOSCPortOut decoratedOSCPortOut) {
        this.monomeApp = monomeApp;
        setId(String.format("ssc-0%d", count));
        // Dummy value - app can crash without this
        setPrefix("/SSC");
        setDecoratedOSCPortIn(decoratedOSCPortIn);
        setDecoratedOSCPortOut(decoratedOSCPortOut);
        count++;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MonomeApp getMonomeApp() {
        return monomeApp;
    }

    public void setMonomeApp(MonomeApp monomeApp) throws IOException {
        this.monomeApp = monomeApp;
        decoratedOSCPortOut.setOscPortOut(monomeApp.getPortNumber());
    }

    public DecoratedOSCPortIn getDecoratedOSCPortIn() {
        return decoratedOSCPortIn;
    }

    private void setDecoratedOSCPortIn(DecoratedOSCPortIn decoratedOSCPortIn) {
        this.decoratedOSCPortIn = decoratedOSCPortIn;
    }

    public DecoratedOSCPortOut getDecoratedOSCPortOut() { return decoratedOSCPortOut;}

    private void setDecoratedOSCPortOut(DecoratedOSCPortOut decoratedOSCPortOut) {
        this.decoratedOSCPortOut = decoratedOSCPortOut;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix){
        this.prefix = prefix;
    };

    public abstract void addSysListeners();

    public abstract void close();
}
