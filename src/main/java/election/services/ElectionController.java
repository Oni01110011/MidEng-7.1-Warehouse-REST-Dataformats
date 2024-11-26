package election.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import election.model.ElectionData;
import election.model.Party;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// GK V
@RestController
@RequestMapping("/election2024")
public class ElectionController {

	@Autowired
	private ElectionProducer electionProducer;

	@Autowired
	private ElectionService electionService;

	@PostMapping("/send")
	public String sendElectionData(@RequestBody ElectionData electionData) {
		ObjectMapper om = new ObjectMapper();
		try {
			String jsonData = om.writeValueAsString(electionData);
			electionProducer.sendElectionData(jsonData);
			return "Election data sent successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed to send election data!";
		}
	}

	@GetMapping("/results/json")
	public String getAggregatedResultsAsJson() throws JsonProcessingException {
		return electionService.getAggregatedDataAsJson();
	}

	@GetMapping("/results/xml")
	public String getAggregatedResultsAsXml() throws JsonProcessingException {
		return electionService.getAggregatedDataAsXml();
	}

	@GetMapping("/results/{pollingStationId}")
	public ResponseEntity<String> getResultsForPollingStation(@PathVariable String pollingStationId) {
		ElectionData data = electionService.getDataByPollingStationId("Station_" + pollingStationId);

		if (data == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Polling Station not found for ID: Station_" + pollingStationId + "\"}");
		}

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(data);

			return ResponseEntity.ok(json);
		} catch (JsonProcessingException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Error processing JSON data\"}");
		}
	}


}
