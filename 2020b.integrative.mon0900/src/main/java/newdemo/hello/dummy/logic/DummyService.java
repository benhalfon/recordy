package newdemo.hello.dummy.logic;

import java.util.List;
import java.util.Map;

import newdemo.hello.dummy.rest.boudanries.DummyBoundary;

// Update: REST API
// --------
// this layer: Business Logic Layer 
//
// -------
// TBD: Data Access Layer (DAL)

public interface DummyService {
	public Map<String, Object> getProjectName();
	public Map<String, Object> getCurrentTime();
	public Map<String, Object> getGreeting(String name);
	
	public List<DummyBoundary> getAllDummies();
	public DummyBoundary createNewDummy(DummyBoundary newDummy);
	public void deleteAll();
	public DummyBoundary getSpecificDummy(String id);
	public DummyBoundary updateDummy(String id, DummyBoundary update);
}
