package election.services;

import election.model.Party;
import election.model.ElectionData;
import election.model.Vorzugskandidaten;

import java.util.ArrayList;

public class ElectionSimulator {
    public ElectionData getData(int wID) {
        ElectionData data = new ElectionData();

        data.setRegionID(wID);
        data.setRegionName("TGM - Technologisches Gewerbe Museum");
        data.setRegionAddress("Wexstraße 19-23");
        data.setRegionPostalCode("Vienna");
        data.setFederalState("Austria");

        ArrayList<Vorzugskandidaten> vz = new ArrayList<>();
        vz.add(new Vorzugskandidaten("NJHGF", 212 ,"Danilo Stoili", 2));
        vz.add(new Vorzugskandidaten("NJHGF", 212 ,"Danilo Stoili", 2));
        vz.add(new Vorzugskandidaten("NJHGF", 212 ,"Danilo Stoili", 2));

        Party party1 = new Party("OJEVB", 102, null);
        Party party2 = new Party("NJHGF", 202, vz);
        Party party3 = new Party("HJKFD", 234,null);
        Party party4 = new Party("KOQWJ", 403,null);

        ArrayList<Party> partys = new ArrayList<>();
        partys.add(party1);
        partys.add(party2);
        partys.add(party3);
        partys.add(party4);

        data.setCountingData(partys);
        return data;
    }
}
