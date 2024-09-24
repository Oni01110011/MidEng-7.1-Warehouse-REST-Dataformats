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

    public ElectionData getData(int wID) {
        ElectionData data = new ElectionData();

        data.setRegionID(wID);
        data.setRegionName("TGM - Technologisches Gewerbe Museum");
        data.setRegionAddress("Wexstraße 19-23");
        data.setRegionPostalCode("Vienna");
        data.setFederalState("Austria");

        ArrayList<Vorzugskandidaten> vz = new ArrayList<>();
        vz.add(new Vorzugskandidaten("OEVP", 212 ,"Max Mustermann", generateRandomNumber()));
        vz.add(new Vorzugskandidaten("OEVP", 212 ,"Daniela Dorian", generateRandomNumber()));
        vz.add(new Vorzugskandidaten("OEVP", 212 ,"Chen Son Goku", generateRandomNumber()));

        ArrayList<Vorzugskandidaten> vz2 = new ArrayList<>();
        vz2.add(new Vorzugskandidaten("FPOE", 212 ,"Wichitger man 2", generateRandomNumber()));
        vz2.add(new Vorzugskandidaten("FPOE", 212 ,"Aran Yilidirn", generateRandomNumber()));
        vz2.add(new Vorzugskandidaten("FPOE", 212 ,"Tom Ristic", generateRandomNumber()));
        vz2.add(new Vorzugskandidaten("FPOE", 212 ,"Sandip Martin Saran", generateRandomNumber()));

        ArrayList<Vorzugskandidaten> vz3 = new ArrayList<>();
        vz3.add(new Vorzugskandidaten("GRUENE", 212 ,"Franz Puerto", generateRandomNumber()));
        vz3.add(new Vorzugskandidaten("GRUENE", 212 ,"Simon Chladeck", generateRandomNumber()));
        vz3.add(new Vorzugskandidaten("GRUENE", 212 ,"Marko Ekmedzic", generateRandomNumber()));
        vz3.add(new Vorzugskandidaten("GRUENE", 212 ,"Dennis Kozac", generateRandomNumber()));

        Party party1 = new Party("OEVP", generateRandomNumber(), vz);
        Party party2 = new Party("FPOE", generateRandomNumber(), vz2);
        Party party3 = new Party("NEOS", generateRandomNumber(),null);
        Party party4 = new Party("GRUENE", generateRandomNumber(),vz3);

        ArrayList<Party> partys = new ArrayList<>();
        partys.add(party1);
        partys.add(party2);
        partys.add(party3);
        partys.add(party4);

        data.setCountingData(partys);
        return data;
    }
}
