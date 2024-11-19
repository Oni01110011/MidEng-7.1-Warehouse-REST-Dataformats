package election.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import election.model.ElectionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElectionDataSender {

    private final ElectionProducer electionProducer;  // Sends data to Kafka
    private final ObjectMapper objectMapper;  // Serializes ElectionData to JSON

    @Autowired
    public ElectionDataSender(ElectionProducer electionProducer) {
        this.electionProducer = electionProducer;
        this.objectMapper = new ObjectMapper();
    }

    public void sendSimulatedData(String pollingStationId, int regionID) {
        ElectionSimulator simulator = new ElectionSimulator();  // Create an instance of ElectionSimulator
        ElectionData data = simulator.generateElectionData(pollingStationId, regionID);  // Generate sample ElectionData

        try {
            // Convert ElectionData to JSON format
            String jsonData = objectMapper.writeValueAsString(data);

            // Send JSON data to Kafka using ElectionProducer
            electionProducer.sendElectionData(jsonData);

            System.out.println("Sent data for Polling Station " + pollingStationId);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error sending data for Polling Station " + pollingStationId);
        }
    }
}
