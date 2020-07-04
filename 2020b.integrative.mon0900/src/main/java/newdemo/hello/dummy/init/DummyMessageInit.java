package newdemo.hello.dummy.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import newdemo.hello.dummy.logic.EnhancedDummyService;
import newdemo.hello.dummy.rest.boudanries.DummyBoundary;

@Component
@Profile("manualTests")
public class DummyMessageInit implements CommandLineRunner{
	private EnhancedDummyService dummyService;
	
	@Autowired	
	public DummyMessageInit(EnhancedDummyService dummyService) {
		this.dummyService = dummyService;
	}



	@Override
	public void run(String... args) throws Exception {
		DummyBoundary m1 = this.dummyService
				.createNewDummy(new DummyBoundary(null, "origin massage"));
		
		DummyBoundary m2 = this.dummyService
				.createNewDummy(new DummyBoundary(null, "response #1"));

		DummyBoundary m3 = this.dummyService
				.createNewDummy(new DummyBoundary(null, "response #2"));

		this.dummyService
			.connectDummies(m1.getId(), m2.getId());

		this.dummyService
			.connectDummies(m1.getId(), m3.getId());

	}

}





