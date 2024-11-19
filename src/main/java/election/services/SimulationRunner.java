package election.services;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class SimulationRunner implements CommandLineRunner {

    @Autowired
    private ElectionDataSender electionDataSender;

    @Override
    public void run(String... args) {
        // Simulate sending data for multiple polling stations
        electionDataSender.sendSimulatedData("Station_1", 101);
        electionDataSender.sendSimulatedData("Station_2", 102);
        electionDataSender.sendSimulatedData("Station_3", 103);
    }
}
