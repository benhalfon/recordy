package newdemo.hello.dummy.dal;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import newdemo.hello.dummy.data.DummyEntity;
import newdemo.hello.dummy.data.TypeEntityEnum;

//Create Read Update Delete - CRUD
public interface DummyDao extends PagingAndSortingRepository<DummyEntity, String>{ 
		//CrudRepository<DummyEntity, String>{

	// SELECT ... FROM DUMMIES WHERE ORIGIN_ID=?
	public List<DummyEntity> findAllByOrigin_id(
			@Param("originId") String originId, 
			Pageable pageable);

	// SELECT ... FROM DUMMIES WHERE TYPE=?
	public List<DummyEntity> findAllByType(
			@Param("type") TypeEntityEnum type, 
			Pageable pageable);	
	
//	// SELECT ... FROM DUMMIES
//	public Iterable<DummyEntity> findAll();
//
//	// SELECT ... FROM DUMMIES WHERE ID = ?
//	public Optional<DummyEntity> findById (String id);
//
//	// SELECT -> UPDATE/INSERT (upsert)
//	public DummyEntity save(DummyEntity entity);
//
//	// DELETE FROM DUMMIES
//	public void deleteAll();
}
