package election.services;

import election.model.ElectionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ElectionConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ElectionConsumer.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ElectionService electionService;

    @Autowired
    private ElectionFeedbackProducer feedbackProducer;

    @KafkaListener(topics = "election_topic", groupId = "election_group")
    public void consume(String message) {
        try {
            // Deserialize the JSON message to ElectionData
            ElectionData data = objectMapper.readValue(message, ElectionData.class);

            logger.info("Received data from Polling Station ID {}: {}", data.getPollingStationId(), message);

            // Add data to ElectionService for aggregation
            electionService.addData(data);

            // Send feedback to the polling station after successful processing
            feedbackProducer.sendFeedback(data.getPollingStationId(), "SUCCESS");
        } catch (Exception e) {
            logger.error("Error processing election data from polling station", e);
            // Optionally, handle retries or send failure feedback
        }
    }
}
