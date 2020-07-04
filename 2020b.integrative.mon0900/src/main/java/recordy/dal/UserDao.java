package recordy.dal;

import org.springframework.data.repository.PagingAndSortingRepository;

import recordy.data.UserEntity;

public interface UserDao extends PagingAndSortingRepository<UserEntity, String> {

}
