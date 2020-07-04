package newdemo;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import newdemo.hello.dummy.rest.boudanries.DummyBoundary;
import newdemo.hello.dummy.rest.boudanries.Type;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTests {
	private RestTemplate restTemplate;
	private String url;
	private int port;
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}
	
	@PostConstruct
	public void init() {
		this.restTemplate = new RestTemplate();
		this.url = "http://localhost:" + this.port + "/dummy";
	}
	
	@BeforeEach
	public void setup() {
		
	}
	
	@AfterEach
	public void teardown() {
		this.restTemplate
			.delete(this.url);
	}
	
	@Test
	public void testContext() {
		
	}
	
	@Test
	public void testPostNewMessageThenTheDatabaseContainsAMessageWithTheSameMessageAttribute() throws Exception{
		// GIVEN the server is up
		// do nothing
		
		// WHEN I POST new message with message attribute: "test"
//		MessageBoundary output = 
		  this.restTemplate
			.postForObject(this.url, 
					new DummyBoundary("???", "test"),
					DummyBoundary.class);
		
		// THEN the database contains a message with the message attribute "test"
		DummyBoundary[] allMessages = 
				this.restTemplate
					.getForObject(this.url, DummyBoundary[].class);
		boolean containsTestMessage = false;
		for (DummyBoundary m : allMessages) {
			if (m.getMessage().equals("test")) {
				containsTestMessage = true;
			}
		}
		
		if (!containsTestMessage) {
			throw new Exception("failed to locate message with proper content");
		}
	}
	
	@Test
	public void testPostNewMessageThenTheDatabaseContainsASingleMessage() throws Exception{
		// GIVEN the server is up
		// do nothing
		
		// WHEN I POST new message
		this.restTemplate
			.postForObject(this.url, 
					new DummyBoundary("???", "test"),
					DummyBoundary.class);
		
		// THEN the database contains a single message
		assertThat(this.restTemplate.getForObject(this.url, DummyBoundary[].class))
			.hasSize(1);
	}
	
	@Test
	public void testPostNewMessageThenTheDatabaseContainsAMessageWithSameIdAsPosted() throws Exception{
		// GIVEN the server is up
		// do nothing
		
		// WHEN I POST new message
		String postedId = 
		  this.restTemplate
			.postForObject(this.url, 
					new DummyBoundary("???", "test"),
					DummyBoundary.class)
			.getId();
		
		// THEN the database contains a single message with same id as posted
		String actualId = 
		  this.restTemplate
			.getForObject(this.url + "/{id}", DummyBoundary.class, postedId)
			.getId();
		
		assertThat(actualId)
			.isNotNull()
			.isEqualTo(postedId);
	}
	
	@Test
	public void testPutOfMessageAndUptesItTypeToSpecialThenTypeIsUpdatedInTheDatabase() throws Exception{
		// GIVEN the server is up 
		// AND the database contains a single message with type: NONE
		DummyBoundary newBoundary  = new DummyBoundary(null, "dummy");
		newBoundary.setType(Type.NONE);

		DummyBoundary boundaryOnServer = 
		  this.restTemplate
			.postForObject(
					this.url, 
					newBoundary, 
					DummyBoundary.class);
		
		String id = boundaryOnServer 
			.getId();
		
		// WHEN I PUT with update of type to be SPECIAL
		DummyBoundary update = new DummyBoundary();
		update.setType(Type.SPECIAL);
		
		this.restTemplate
			.put(this.url + "/{id}", update, id);
		
		// THEN the database contains a message with same id and type: SPECIAL
		assertThat(this.restTemplate
				.getForObject(this.url + "/{id}", DummyBoundary.class, id))
			.extracting("id", "type")
			.containsExactly(id, update.getType());
		
	}
	
	@Test
	public void testTheDatabaseIsEmptyByDefault() throws Exception {
		// GIVEN the server is up
		
		// WHEN I GET for all messages
		DummyBoundary[] actual
			= this.restTemplate
				.getForObject(this.url, DummyBoundary[].class);
		
		// THEN the returned value is an empty array
		assertThat(actual)
			.isEmpty();
	}
	
	
}
