package election.model;

import java.util.ArrayList;

public class Party {
    private String partyID;
    private int amountVotes;
    private ArrayList<Vorzugskandidaten> vorzugskandidaten;

    public Party() {
    }

    public Party(String partyID, int amountVotes, ArrayList<Vorzugskandidaten> vorzugskandidaten) {
        this.partyID = partyID;
        this.amountVotes = amountVotes;
        this.vorzugskandidaten = vorzugskandidaten;
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
        this.vorzugskandidaten = vorzugskandidaten;
    }

}
