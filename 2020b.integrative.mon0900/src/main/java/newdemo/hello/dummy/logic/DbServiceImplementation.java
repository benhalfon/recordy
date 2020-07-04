package newdemo.hello.dummy.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
//import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import newdemo.aop.MyLogger;
import newdemo.hello.dummy.dal.DummyDao;
import newdemo.hello.dummy.data.DummyConverter;
import newdemo.hello.dummy.data.DummyEntity;
import newdemo.hello.dummy.data.TypeEntityEnum;
import newdemo.hello.dummy.rest.boudanries.DummyBoundary;
import newdemo.hello.dummy.rest.boudanries.Type;

@Service
public class DbServiceImplementation implements EnhancedDummyService{
	private String projectName;
	private DummyDao dummyDao;
	private DummyConverter converter; 
	
//	private Log logger = LogFactory.getLog(DbServiceImplementation.class);
	
	@Autowired
	public DbServiceImplementation(DummyDao dummyDao, DummyConverter converter) {
		this.dummyDao = dummyDao;
		this.converter = converter;
	}
	
	// injection of value from the spring boot configuration
	@Value("${spring.application.name:demo}")
	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
	@Transactional(readOnly = true)
	@MyLogger
	public List<DummyBoundary> getAllDummies() {
//		System.err.println("DbServiceImplementation.getAllDummies()");
//		this.logger.trace("*********** DbServiceImplementation.getAllDummies()");
		
		
		// get entity objects from database
		Iterable<DummyEntity> all = this.dummyDao
			.findAll();

		List<DummyBoundary> rv = new ArrayList<>(); 
		for (DummyEntity entity : all) {
			// map entities to boundaries
			rv.add(this.converter.fromEntity(entity));
		}
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true)
	@MyLogger
	public List<DummyBoundary> getAllDummies(int size, int page) {
//		System.err.println("DbServiceImplementation.getAllDummies()");
//		this.logger.trace("*********** DbServiceImplementation.getAllDummies()");
		
		return this.dummyDao.findAll(
				PageRequest.of(page, size, Direction.DESC, "type", "id")) // Page<DummyEntity>
				.getContent() // List<DummyEntity>
				.stream() // Stream<DummyEntity>
				.map(this.converter::fromEntity) // Stream<DummyBoundary>
				.collect(Collectors.toList()); // List<DummyBoundary>
	}

	@Override
	@Transactional//(readOnly = false)
	@MyLogger
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
		
		
		return this.converter
			.fromEntity(
				this.dummyDao.save(entity));
	}
	
	@Override
	@Transactional//(readOnly = false)
	public void deleteAll() {
		this.dummyDao
			.deleteAll();		
	}
	
	@Override
	@Transactional(readOnly = true)
	public DummyBoundary getSpecificDummy(String id) {
		// Init new Transaction
		
		Optional<DummyEntity> existing = this.dummyDao.findById(id);
		
		if (existing.isPresent()) {
			return this.converter
				.fromEntity(
					existing.get());
			
			// commit transaction 
		}else {
			throw new DummyNotFoundException("could not find object by id: " + id);
			
			// rollback transaction
		}
	}
	
	@Override
	@Transactional//(readOnly = false)
	public DummyBoundary updateDummy(String id, DummyBoundary update) {
		DummyEntity existing = this.dummyDao.findById(id)
				.orElseThrow(()->new DummyNotFoundException("could not find object by id: " + id));
		
		if (update.getMessage() != null) {
			existing.setMessage(update.getMessage());
		}
		
		if (update.getDetails() != null) {
			existing.setDetails(update.getDetails());
		}
		
		if (update.getType() != null) {
			existing.setType(this.converter.toEntity(update.getType()));
		}
		
		if (update.getIsDeleted() != null) {
			existing.setDeleted(update.getIsDeleted());
		}
		
		if (update.getName() != null) {
			existing.setName(this.converter.toEntity(update.getName()));
		}
		
		// DAO - Data Access Object
		return this.converter
				.fromEntity(
						this.dummyDao.save(existing));
	}
	
	// origin<----->response
	@Transactional
	public void connectDummies (String originId, String responseId) {
		DummyEntity origin = this.dummyDao.findById(originId)
				.orElseThrow(()->
						new DummyNotFoundException("could not find origin by id:" + originId));
		
		DummyEntity response = this.dummyDao.findById(responseId)
				.orElseThrow(()->
						new DummyNotFoundException("could not find response by id:" + responseId));

		origin.addResponse(response);
		this.dummyDao.save(origin);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<DummyBoundary> getAllResponses (String originId, int size, int page) {
		return
			this.dummyDao
				.findAllByOrigin_id(
						originId, 
						PageRequest.of(page, size, Direction.DESC, "timestamp", "id"))
				.stream()
				.map(this.converter::fromEntity)
				.collect(Collectors.toList());
				
		
//		DummyEntity origin = this.dummyDao.findById(originId)
//				.orElseThrow(()->
//						new DummyNotFoundException("could not find origin by id:" + originId));
//		
//		return origin
//			.getResponses()
//			.stream()
//			.map(this.converter::fromEntity)
//			.collect(Collectors.toSet());
	}

	@Override
	@Transactional(readOnly = true)
	// page = 0, size = 8
	public Collection<DummyBoundary> getOrigin(String responseId, int size, int page) {
		DummyEntity response = this.dummyDao.findById(responseId)
				.orElseThrow(()->
						new DummyNotFoundException("could not find response by id:" + responseId));
		if (size < 1) {
			throw new RuntimeException("size must be not less than 1"); 
		}
		
		if (page < 0) {
			throw new RuntimeException("page must not be negative");
		}
		
		DummyEntity origin = response.getOrigin();
		
		Collection<DummyBoundary> rv = new HashSet<>();
		// page = 0 && size >= 1
		if (origin != null && page == 0) {
			DummyBoundary rvBoundary = this.converter.fromEntity(origin);
			rv.add(rvBoundary);
		}// else { // page > 0 && size >= 1
		//}
		return rv;
	}

	@Override
	@Transactional(readOnly = true)
	public List<DummyBoundary> getDummiesByType(Type boundaryType, int size, int page) {
		TypeEntityEnum entityType = this.converter.toEntity(boundaryType);
		return this.dummyDao
				.findAllByType(entityType, PageRequest.of(page, size, Direction.DESC, "id"))
				.stream()
				.map(this.converter::fromEntity)
				.collect(Collectors.toList());
	}
}
