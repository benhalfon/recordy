package newdemo.hello.dummy.rest;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import newdemo.hello.dummy.logic.DummyNotFoundException;
import newdemo.hello.dummy.logic.EnhancedDummyService;
import newdemo.hello.dummy.rest.boudanries.DummyBoundary;
import newdemo.hello.dummy.rest.boudanries.Type;

//@RestController
public class DummyController {
	private EnhancedDummyService dummyService;
	
	@Autowired
	public DummyController(EnhancedDummyService dummyService) {
		super();
		this.dummyService = dummyService;
	}

	
	// invoked by spring after singleton is created and after injections
	@PostConstruct
	public void init() {
		System.err.println("***** " + this.dummyService.getProjectName().get("projectName"));
	}
	
	// invoked by spring when it is shutting down gracefully
	@PreDestroy
	public void byeBye() {
		System.err.println("controller is about to be destroyed...");
	}
	
	/*
	{
		"projectName":"2020b.demo"
	}
	 */
	@RequestMapping(path = "/dummy", method = RequestMethod.GET,
			//produces = MediaType.TEXT_PLAIN_VALUE) // text/plain
			produces = MediaType.APPLICATION_JSON_VALUE)
//	public String dummy() {
	public DummyBoundary[] getAllDummies(
			@RequestParam (name = "size", required = false, defaultValue = "10") int size,
			@RequestParam (name = "page", required = false, defaultValue = "0") int page) {
		return this.dummyService
				.getAllDummies(size, page)
				.toArray(new DummyBoundary[0]);
//				.getProjectName();
//				.getGreeting("dummy");
//				.getCurrentTime();
				
	}
	
	@RequestMapping(path="/dummy", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public DummyBoundary createNewDummy (
			@RequestBody DummyBoundary newDummy) {
		return this.dummyService
				.createNewDummy (newDummy);
	}
	
	@RequestMapping(path = "/dummy", method = RequestMethod.DELETE)
	public void deleteAll() {
		this.dummyService
			.deleteAll();
	}
	
	@RequestMapping(path="/dummy/{id}", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public DummyBoundary getSpecificDummy (@PathVariable("id") String id) {
		return this.dummyService
				.getSpecificDummy(id);
	}
	
	@RequestMapping(path="/dummy/{id}", method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateDummy (
			@PathVariable("id") String id, 
			@RequestBody DummyBoundary update) {
		this.dummyService
			.updateDummy(id, update);
	}
	
	@RequestMapping(
			path="/dummy/{id}/responses", 
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void connectDummies (
			@PathVariable("id") String originId, 
			@RequestBody Map<String, String> responseIdWrapper) { // {"responseId":"123"}
		this.dummyService
			.connectDummies(originId, responseIdWrapper.get("responseId"));
	}

	@RequestMapping(
			path="/dummy/{id}/responses", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public DummyBoundary[] getAllResponses(
			@PathVariable("id") String originId,
			@RequestParam (name = "size", required = false, defaultValue = "10") int size,
			@RequestParam (name = "page", required = false, defaultValue = "0") int page){
		return this.dummyService
				.getAllResponses(originId, size, page)
				.toArray(new DummyBoundary[0]);
	}
	
	@RequestMapping(
			path="/dummy/{id}/origins", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public DummyBoundary[] getOrigin(
			@PathVariable("id") String responseId,
			@RequestParam (name = "size", required = false, defaultValue = "10") int size,
			@RequestParam (name = "page", required = false, defaultValue = "0") int page){
		return this.dummyService
				.getOrigin(responseId, size, page)
				.toArray(new DummyBoundary[0]);
	}
	
	@RequestMapping(
			path="/dummy/byType/{type}", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public DummyBoundary[] getDummiesByType (
			@PathVariable("type") Type type,
			@RequestParam(name = "size", required = false, defaultValue = "10") int size, 
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		return this.dummyService
				.getDummiesByType(type, size, page)
				.stream()
				.collect(Collectors.toList())
				.toArray(new DummyBoundary[0]);
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map<String, Object> handleException (DummyNotFoundException e){
		return Collections.singletonMap("error", 
				(e.getMessage() == null)?
						"Dummy was not found":
						e.getMessage());
	}
	
}

