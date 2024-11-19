package election.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import election.model.ElectionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
