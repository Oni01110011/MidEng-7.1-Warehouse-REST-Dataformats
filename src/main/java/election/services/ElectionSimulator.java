package election.services;

import election.model.Party;
import election.model.ElectionData;
import election.model.Vorzugskandidaten;

import java.util.ArrayList;
import java.util.Random;

public class ElectionSimulator {

    public int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(501);
    }

    public ElectionData generateElectionData(String pollingStationId, int regionID) {
        ElectionData data = new ElectionData();

        data.setPollingStationId(pollingStationId);
        data.setRegionID(regionID);
        data.setRegionName("TGM - Technologisches Gewerbe Museum");
        data.setRegionAddress("Wexstraße 19-23");
        data.setRegionPostalCode("Vienna");
        data.setFederalState("Austria");

        ArrayList<Vorzugskandidaten> oevpCandidates = new ArrayList<>();
        oevpCandidates.add(new Vorzugskandidaten("OEVP", 212, "Danilo Stoilovski", generateRandomNumber()));
        oevpCandidates.add(new Vorzugskandidaten("OEVP", 212, "Daniela Dorian", generateRandomNumber()));

        ArrayList<Vorzugskandidaten> fpoeCandidates = new ArrayList<>();
        fpoeCandidates.add(new Vorzugskandidaten("FPOE", 212, "Wichtiger Mann", generateRandomNumber()));
        fpoeCandidates.add(new Vorzugskandidaten("FPOE", 212, "Aran Yildirim", generateRandomNumber()));

        Party party1 = new Party("OEVP", generateRandomNumber(), oevpCandidates);
        Party party2 = new Party("FPOE", generateRandomNumber(), fpoeCandidates);
        Party party3 = new Party("NEOS", generateRandomNumber(), null);

        ArrayList<Party> parties = new ArrayList<>();
        parties.add(party1);
        parties.add(party2);
        parties.add(party3);

        data.setCountingData(parties);

        return data;
    }
}
