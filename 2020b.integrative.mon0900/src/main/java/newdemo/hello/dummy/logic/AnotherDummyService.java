package newdemo.hello.dummy.logic;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import newdemo.hello.dummy.rest.boudanries.DummyBoundary;

//import org.springframework.stereotype.Service;

//@Service
public class AnotherDummyService implements DummyService {

	@Override
	public Map<String, Object> getProjectName() {
		// TODO Auto-generated method stub
		return Collections.singletonMap("wrongAttribute", "the project name");
	}

	@Override
	public Map<String, Object> getCurrentTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getGreeting(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DummyBoundary> getAllDummies() {
		throw new RuntimeException("unimplemented method");
	}

	@Override
	public DummyBoundary createNewDummy(DummyBoundary newDummy) {
		throw new RuntimeException("implemented yet");
	}

	@Override
	public void deleteAll() {
		throw new RuntimeException("implemented yet");
	}

	@Override
	public DummyBoundary getSpecificDummy(String id) {
		throw new RuntimeException("implemented yet");
	}

	@Override
	public DummyBoundary updateDummy(String id, DummyBoundary update) {
		throw new RuntimeException("implemented yet");
	}

}
