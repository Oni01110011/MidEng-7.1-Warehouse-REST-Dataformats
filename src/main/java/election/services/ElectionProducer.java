package election.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ElectionProducer {

    private static final String TOPIC = "election_topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendElectionData(String data) {
        kafkaTemplate.send(TOPIC, data);
        System.out.println("Election data sent to Kafka topic: " + data);
    }
}
