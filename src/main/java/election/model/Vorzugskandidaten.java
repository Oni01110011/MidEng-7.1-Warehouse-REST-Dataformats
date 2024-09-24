package election.model;

public class Vorzugskandidaten {
    private String partyID;
    private int listenNR;
    private String name;
    private int stimmen;
    public Vorzugskandidaten(String partyID, int listenNR, String name, int stimmen) {
        this.partyID = partyID;
        this.listenNR = listenNR;
        this.name = name;
        this.stimmen = stimmen;
    }

    public String getPartyID() {
        return partyID;
    }

    public void setPartyID(String partyID) {
        this.partyID = partyID;
    }

    public int getListenNR() {
        return listenNR;
    }

    public void setListenNR(int listenNR) {
        this.listenNR = listenNR;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStimmen() {
        return stimmen;
    }

    public void setStimmen(int stimmen) {
        this.stimmen = stimmen;
    }

}
