package election.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FeedbackListener {

    @KafkaListener(topics = "election_feedback", groupId = "polling_station_group")
    public void consumeFeedback(String message) {
        System.out.println("Feedback received: " + message);
    }
}
