package newdemo.hello.dummy.logic;

import java.util.Collection;
import java.util.List;

import newdemo.hello.dummy.rest.boudanries.DummyBoundary;
import newdemo.hello.dummy.rest.boudanries.Type;

public interface EnhancedDummyService extends DummyService{
	public void connectDummies (String originId, String responseId);

	public Collection<DummyBoundary> getAllResponses(String originId, int size, int page);

	public Collection<DummyBoundary> getOrigin(String responseId, int size, int page);

	public List<DummyBoundary> getAllDummies(int size, int page);

	public List<DummyBoundary> getDummiesByType(Type type, int size, int page);
	
}
