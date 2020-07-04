package recordy.logicImplementation.userLogic;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import recordy.dal.UserDao;
import recordy.data.UserEntity;
import recordy.data.UserRoles;
import recordy.data.convertors.UserConvertor;
import recordy.userBoundaries.UserBoundary;
import recordy.userLogic.UserService;
import utils.StringUtils;

@Service
public class UserServiceImplementation implements UserService
{
	private UserConvertor converter;
	private UserDao userDatabase;
	
	@Autowired
	public UserServiceImplementation(UserConvertor convertor, UserDao userDatabase) {
		this.converter = convertor;
		this.userDatabase = userDatabase;
	}
	
	@Override
	public UserBoundary createUser(UserBoundary user) {
		if(user.getUserName().isEmpty())
			throw new RuntimeException("username empty");
		if (user.getRole() == null) {
			user.setRole(UserRoles.PLAYER.toString());
		}
		UserEntity newUserEntity = this.converter.toEntity(user);
		Optional<UserEntity> exiting = this.userDatabase.findById(newUserEntity.getUserId());
		if (exiting.isPresent()) {
			if (!exiting.get().getIsActive())
				exiting.get().setIsActive(true);
			else
				throw new RuntimeException("user already exists");
		}else {
			newUserEntity.setIsActive(true);
			newUserEntity.setCreatedDate(new Date());
		}
		this.userDatabase.save(newUserEntity);
		return user;
	}

	@Override
	@Transactional(readOnly = true)
	public UserBoundary login(String userId) {
		Optional<UserEntity> getUser = this.userDatabase.findById(userId);
		
		if (getUser.isPresent() && getUser.get().getIsActive()) {
			return this.converter.toBoundary(getUser.get());
		} else {
			throw new RuntimeException("User Not Found With This userId");
		}
	}

	@Override
	public UserBoundary updateUser(String userId, UserBoundary update) {
		if(userId.equals(update.getUserId()) || login(userId).getRole().equals(UserRoles.MANAGER))
		{
			// THE USER TO UPDATE
			Optional<UserEntity> existingUser = this.userDatabase.findById(this.converter.toEntity(update).getUserId());
			if (!existingUser.isPresent() || existingUser.get().getIsActive()==false) {
				throw new RuntimeException("Update Fail, User to update is not exist");
			}
			if (!StringUtils.isNullOrEmpty(update.getRole())) {
				existingUser.get().setRole(this.converter.boundaryToEntityRole(update.getRole()));
			}
			
			if (!StringUtils.isNullOrEmpty(update.getUserName())) {
				existingUser.get().setUserName(update.getUserName());
			}


		    this.userDatabase.save(existingUser.get());
			return this.converter.toBoundary(existingUser.get());
		}
		else
			throw new RuntimeException("No permission to update user");
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserBoundary> getAllUsers(String userId) {
		Optional<UserEntity> admin = this.userDatabase.findById(userId);

		if (admin.isPresent() && admin.get().getIsActive()) {   // if the user that create the request is exist
			if (admin.get().getRole() == UserRoles.ADMIN) {
				return StreamSupport.stream(this.userDatabase.findAll().spliterator(),false).
						map(this.converter::toBoundary).collect(Collectors.toList());
			} else
				throw new RuntimeException("This user is not a admin");
		}
		throw new RuntimeException("user is not exist in the system");
	}

	@Override
	public void deleteAllUsers(String userId) {
		Optional<UserEntity> admin = this.userDatabase.findById(userId);

		if (admin.isPresent()&&admin.get().getIsActive()) {
			if (admin.get().getRole() == UserRoles.ADMIN) {
				this.userDatabase.deleteAll();
			} else
				throw new RuntimeException("This user is not a admin");
		} else
			throw new RuntimeException("user is not exist in the system");
		
	}
	
}
