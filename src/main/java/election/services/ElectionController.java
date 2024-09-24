package election.services;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import election.model.ElectionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ElectionController {

	@Autowired
	private ElectionService whs = new ElectionService();

	@RequestMapping("/")
	public String warehouseMain() {
		String mainPage = "This is the election application! (DEZSYS_Election_REST) <br/><br/>" +
				"<a href='http://localhost:3389/election/001/json'>Link to Election/001/json</a><br/>" +
				"<a href='http://localhost:3389/election/001/xml'>Link to Election/001/xml</a><br/>";
		return mainPage;
	}

	@RequestMapping(value="/election/{eID}/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ElectionData electionDataJSON(@PathVariable int eID ) {
		return whs.getElectionData(eID);
	}

	@RequestMapping(value="/election/{eID}/xml", produces = MediaType.APPLICATION_XML_VALUE)
	public ElectionData electionDataXML( @PathVariable int eID ) {
		return whs.getElectionData(eID);
	}
}