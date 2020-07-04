package newdemo.init;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import newdemo.hello.dummy.logic.DummyService;
import newdemo.hello.dummy.rest.boudanries.DummyBoundary;

@Component
@Profile("production")
public class MessageCreator implements CommandLineRunner{
	private DummyService dummyService;
	
	@Autowired
	public MessageCreator(DummyService dummyService) {
		this.dummyService = dummyService;
	}

	@Override
	public void run(String... args) throws Exception {
		IntStream.range(1, 4) // Stream<Integer> 1,2,3
			.mapToObj(i->"message #" + i) // Stream<String>
			.map(message->new DummyBoundary(null, message)) // Stream<DummyBoundary>
			.map(this.dummyService::createNewDummy) // Stream<DummyBoundary>
			.forEach(System.err::println); // print each DummyBoundary in System.err
	}
}




