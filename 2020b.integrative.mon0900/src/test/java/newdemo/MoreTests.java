package newdemo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import newdemo.hello.dummy.rest.boudanries.DummyBoundary;
import newdemo.hello.dummy.rest.boudanries.Type;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MoreTests {
	private String url;
	private int port;
	private RestTemplate restTemplate;
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}

	@PostConstruct
	public void init() {
		this.url = "http://localhost:" + port + "/dummy";
		this.restTemplate = new RestTemplate();		
	}
	
	@AfterEach 
	public void teardown() {
		this.restTemplate
			.delete(this.url);
	}
	
	@Test
	public void testDummy() {
		
	}
	
	@Test
	public void test_Init_Server_With_4_Messages_When_We_Get_All_Messages_We_Receive_The_Same_Messages_Initialized() throws Exception {
		// GIVEN the server is up
		// AND the server contains 4 messages
		List<DummyBoundary> allMessagesInDb = 
		  IntStream.range(1, 5)
			.mapToObj(i->(i == 4)?"message x":("message #" + i))/*{
				if (i == 4) {
					return "message x";
				}else {
					return "message " + i;
				}
			}*/
			.map(message->new DummyBoundary(null, "message #1"))
			.map(boundary-> this.restTemplate
						.postForObject(
								this.url, 
								boundary, 
								DummyBoundary.class))
			.collect(Collectors.toList()); 
		
		// WHEN I GET /dummy
		DummyBoundary[] results = this.restTemplate
			.getForObject(this.url, 
					DummyBoundary[].class);

		// THEN The server returns the same 3 messages initialized
		assertThat(results)
//			.hasSize(allMessagesInDb.size())
			.usingRecursiveFieldByFieldElementComparator()
			.containsExactlyInAnyOrderElementsOf(allMessagesInDb);
	}
	
	@Test
	public void testGetDummiesByTypeWithBadSize() throws Exception{
		// GIVEN the database contains 10 dummies with type: REGULAR
		IntStream.range(0, 10)
			.mapToObj(i->new DummyBoundary(null, "message " + (i+1)))
			.peek(dummy->dummy.setType(Type.REGULAR))
			.forEach(dummy->
				this.restTemplate
					.postForObject(this.url, 
							dummy, 
							DummyBoundary.class));
			
		// WHEN I invoke GET /dummy/byType/REGULAR?page=5&size=-1
		// THEN the server responds with status <> 2xx
		assertThrows(Exception.class, ()->
			this.restTemplate
				.getForObject(this.url + "/byType/{type}?page={page}&size={size}", 
						DummyBoundary[].class, 
						
						Type.REGULAR, // type
						5, // page
						-1 // size
					)); 
		
	}
	
	@Test
	public void testGetDummiesByTypeWithWithPagination() throws Exception{
		// GIVEN the database contains 10 dummies with type: REGULAR
		IntStream.range(0, 10)
			.mapToObj(i->new DummyBoundary(null, "message " + (i+1)))
			.peek(dummy->dummy.setType(Type.REGULAR))
			.forEach(dummy->
				this.restTemplate
					.postForObject(this.url, 
							dummy, 
							DummyBoundary.class));

		// AND the database contains 15 dummies with type: SPECIAL
		IntStream.range(10, 25)
			.mapToObj(i->new DummyBoundary(null, "message " + (i+1)))
			.peek(dummy->dummy.setType(Type.SPECIAL))
			.forEach(dummy->
				this.restTemplate
					.postForObject(this.url, 
							dummy, 
							DummyBoundary.class));

		// WHEN I invoke GET /dummy/byType/REGULAR?page=0&size=100
		DummyBoundary[] actualResults = 
		  this.restTemplate
			.getForObject(this.url + "/byType/{type}?page={page}&size={size}", 
					DummyBoundary[].class, 
					
					Type.REGULAR, // type
					0, // page
					100 // size
				); 

		// THEN the server responds with status 2xx
		// AND the response body contains all 10 REGULAR messages
		assertThat(actualResults)
			.hasSize(10);
		
		assertThat( 
		  Stream.of(actualResults)
			.filter(res->res.getType() != null && res.getType().equals(Type.REGULAR))
			.collect(Collectors.toList()))
		.hasSize(10);
	}
}







