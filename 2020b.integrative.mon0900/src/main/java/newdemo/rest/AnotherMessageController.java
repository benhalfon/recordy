package newdemo.rest;

import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import recordy.ComplexMessagBoundary;

@RestController
@Profile("production")
public class AnotherMessageController {
	@RequestMapping(path = "/hello/{id}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateMessage (
			@PathVariable("id") String id, 
			@RequestBody ComplexMessagBoundary update) {
		// TODO implement this method to update message details		
	}
	
	@RequestMapping(path = "/hello/{id}",
			method = RequestMethod.DELETE)
	public void deleteSpecificMessage (
			@PathVariable("id") String id) {
		// TODO implement this stub later
	}
}

