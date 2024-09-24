package election.services;

import org.springframework.stereotype.Service;
import election.model.ElectionData;

@Service
public class ElectionService {
    public ElectionData getElectionData(int eID) {
        ElectionSimulator whs = new ElectionSimulator();
        return whs.getData(eID);
    }
}
