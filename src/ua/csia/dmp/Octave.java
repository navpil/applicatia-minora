package ua.csia.dmp;

import static ua.csia.dmp.Interval.QUART;
import static ua.csia.dmp.Interval.QUINT;
import static ua.csia.dmp.Interval.TER;

public class Octave {
    private final Note DO ;
    private final Note RE ;
    private final Note MI ;
    private final Note FA ;
    private final Note SOL;
    private final Note LA ;
    private final Note TI ;

    public Octave(Note DO, Note RE, Note MI, Note FA, Note SOL, Note LA, Note TI) {
        this.DO = DO;
        this.RE = RE;
        this.MI = MI;
        this.FA = FA;
        this.SOL = SOL;
        this.LA = LA;
        this.TI = TI;
    }

    @Override
    public String toString() {
        return "DO: " + DO +
                " RE: " + RE
        +" MI: " + MI
        +" FA: " + FA
        +" SOL: " + SOL
        +" LA: " + LA
        +" TI: " + TI;}
}
