package blog.integrations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import blog.dtos.SummaryDTO;

@Component
public class SearchClient {

	private final String uri = "http://localhost:8086/";
	
	public List<SummaryDTO> searchSummaries(String query) {
		
		List<SummaryDTO> summaries = new ArrayList<>();
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(uri, Object[].class);
		for (Object object : responseEntity.getBody()) {
			summaries.add((SummaryDTO)object);
		}
		return summaries;
	}
	
}
