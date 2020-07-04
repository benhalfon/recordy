package newdemo.hello.dummy.logic;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;

import newdemo.hello.dummy.data.DummyConverter;
import newdemo.hello.dummy.data.DummyEntity;
import newdemo.hello.dummy.rest.boudanries.DummyBoundary;
import newdemo.hello.dummy.rest.boudanries.Type;

//@Service
public class DummyServiceImplementation implements DummyService{
	private String projectName;
	private Map<String, DummyEntity> dummiesDatabase;
	private DummyConverter converter; 
	
	@Autowired
	public DummyServiceImplementation(DummyConverter converter) {
		this.converter = converter;
	}
	
	// injection of value from the spring boot configuration
	@Value("${spring.application.name:demo}")
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@PostConstruct
	public void init() {
		// since this class is a singleton, we generate a thread safe collection
		this.dummiesDatabase = Collections.synchronizedMap(new TreeMap<>());
	}
	
	@Override
	public Map<String, Object> getProjectName() {
		return Collections.singletonMap("projectName", projectName);
	}

	@Override
	public Map<String, Object> getCurrentTime() {
		return Collections.singletonMap("currentTimestamp", new Date());
	}

	@Override
	public Map<String, Object> getGreeting(String name) {
		if (name != null && !name.trim().isEmpty()) {
			return Collections.singletonMap("greeting", "Hello " + name + "!");
		}else {
			throw new RuntimeException("name must not be empty or null");
		}
	}
	
	@Override
	public List<DummyBoundary> getAllDummies() {
		// get entity objects from database mockup
		// map entities to boundaries
		return this.dummiesDatabase // Map<String, DummyEntity>
				.values()           // Collection<DummyEntity>
				.stream()		    // Stream<DummyEntity>
				
				.map(this.converter::fromEntity)	// Stream<DummyBoundaries>
//				.map(e->this.converter.fromEntity(e))	// Stream<DummyBoundaries>
				
				.collect(Collectors.toList()); // List<DummyBoundaries>
	}

	@Override
	public DummyBoundary createNewDummy(DummyBoundary newDummy) {
		newDummy.setId(UUID.randomUUID().toString());
		if (newDummy.getType() == null) {
			newDummy.setType(Type.NONE);
		}
		if (newDummy.getDetails() == null) {
			newDummy.setDetails(new HashMap<>());
		}
		newDummy.setIsDeleted(false);
		
		DummyEntity entity = this.converter.toEntity(newDummy);
		entity.setTimestamp(new Date());
		this.dummiesDatabase.put(entity.getId(), entity);
		return this.converter.fromEntity(entity);
	}
	
	@Override
	public void deleteAll() {
		this.dummiesDatabase
			.clear();		
	}
	
	@Override
	public DummyBoundary getSpecificDummy(String id) {
		DummyEntity existing = this.dummiesDatabase.get(id);
		if (existing != null) {
			return this.converter
				.fromEntity(
					existing);
		}else {
			throw new DummyNotFoundException("could not find object by id: " + id);
		}
	}
	
	@Override
	public DummyBoundary updateDummy(String id, DummyBoundary update) {
		DummyEntity existing = this.dummiesDatabase.get(id);
		if (existing == null) {
			throw new DummyNotFoundException("could not find object by id: " + id);
		}
		
		boolean dirty = false;
		
		if (update.getMessage() != null) {
			existing.setMessage(update.getMessage());
			dirty = true;
		}
		
		if (update.getDetails() != null) {
			existing.setDetails(update.getDetails());
			dirty = true;
		}
		
		if (update.getType() != null) {
			existing.setType(this.converter.toEntity(update.getType()));
			dirty = true;
		}
		
		if (update.getIsDeleted() != null) {
			existing.setDeleted(update.getIsDeleted());
			dirty = true;
		}
		
		if (dirty) {
			this.dummiesDatabase.put(id, existing);
		}

		return this.converter
				.fromEntity(existing);
	}

}
