package election.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ElectionFeedbackProducer {

    private static final String FEEDBACK_TOPIC = "election_feedback";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendFeedback(String pollingStationId, String status) {
        String feedbackMessage = "Polling Station " + pollingStationId + ": " + status;
        kafkaTemplate.send(FEEDBACK_TOPIC, feedbackMessage);
        System.out.println("Feedback sent: " + feedbackMessage);
    }
}
