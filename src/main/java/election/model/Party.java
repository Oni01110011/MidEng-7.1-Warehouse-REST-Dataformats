package election.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Party {
    private String partyID;
    private int amountVotes;
    private ArrayList<Vorzugskandidaten> vorzugskandidaten;

    public Party() {
        this.vorzugskandidaten = new ArrayList<>();
    }

    public Party(String partyID, int amountVotes, ArrayList<Vorzugskandidaten> vorzugskandidaten) {
        this.partyID = partyID;
        this.amountVotes = amountVotes;
        this.vorzugskandidaten = (vorzugskandidaten != null) ? vorzugskandidaten : new ArrayList<>();
        sortiereVorzugskandidatenNachStimmen();
    }

    public String getPartyID() {
        return partyID;
    }

    public void setPartyID(String partyID) {
        this.partyID = partyID;
    }

    public int getAmountVotes() {
        return amountVotes;
    }

    public void setAmountVotes(int amountVotes) {
        this.amountVotes = amountVotes;
    }

    public ArrayList<Vorzugskandidaten> getVorzugskandidaten() {
        return vorzugskandidaten;
    }

    public void setVorzugskandidaten(ArrayList<Vorzugskandidaten> vorzugskandidaten) {
        this.vorzugskandidaten = (vorzugskandidaten != null) ? vorzugskandidaten : new ArrayList<>();
        sortiereVorzugskandidatenNachStimmen();
    }

    public void sortiereVorzugskandidatenNachStimmen() {
        if (vorzugskandidaten != null && !vorzugskandidaten.isEmpty()) {
            Collections.sort(vorzugskandidaten, (k1, k2) -> Integer.compare(k2.getStimmen(), k1.getStimmen()));

            for (int i = 0; i < vorzugskandidaten.size(); i++) {
                vorzugskandidaten.get(i).setListenNR(i + 1);
            }
        }
    }
}
