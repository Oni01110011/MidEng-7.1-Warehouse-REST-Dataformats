package election.services;

import election.model.Party;
import election.model.ElectionData;
import election.model.Vorzugskandidaten;

import java.util.ArrayList;
import java.util.Random;

public class ElectionSimulator {

    private final Random random = new Random();

    private int generateVotesForStation(String pollingStationId) {
        int baseOffset = Math.abs(pollingStationId.hashCode() % 500);
        return random.nextInt(200) + baseOffset;
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
        oevpCandidates.add(new Vorzugskandidaten("OEVP", 1, "Danilo Stoilovski", generateVotesForStation(pollingStationId)));
        oevpCandidates.add(new Vorzugskandidaten("OEVP", 2, "Daniela Dorian", generateVotesForStation(pollingStationId)));

        ArrayList<Vorzugskandidaten> fpoeCandidates = new ArrayList<>();
        fpoeCandidates.add(new Vorzugskandidaten("FPOE", 1, "Wichtiger Mann", generateVotesForStation(pollingStationId)));
        fpoeCandidates.add(new Vorzugskandidaten("FPOE", 2, "Aran Yildirim", generateVotesForStation(pollingStationId)));

        Party party1 = new Party("OEVP", generateVotesForStation(pollingStationId), oevpCandidates);
        Party party2 = new Party("FPOE", generateVotesForStation(pollingStationId), fpoeCandidates);
        Party party3 = new Party("NEOS", generateVotesForStation(pollingStationId), null);

        ArrayList<Party> parties = new ArrayList<>();
        parties.add(party1);
        parties.add(party2);
        parties.add(party3);

        data.setCountingData(parties);

        return data;
    }
}
