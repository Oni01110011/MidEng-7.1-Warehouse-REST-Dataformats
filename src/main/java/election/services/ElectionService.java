package election.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import election.model.ElectionData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ElectionService {

    private final List<ElectionData> electionDataList = new ArrayList<>();
    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final XmlMapper xmlMapper = new XmlMapper();

    public void addData(ElectionData data) {
        electionDataList.add(data);
    }

    public String getAggregatedDataAsJson() throws JsonProcessingException {
        return jsonMapper.writeValueAsString(electionDataList);
    }

    public String getAggregatedDataAsXml() throws JsonProcessingException {
        return xmlMapper.writeValueAsString(electionDataList);
    }
}
