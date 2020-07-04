package recordy.userLogic;

import java.util.List;

import recordy.userBoundaries.UserBoundary;

public interface UserService {
	public UserBoundary	createUser(UserBoundary user);
	public UserBoundary	login(String userId);
	public UserBoundary	updateUser(String userId, UserBoundary update);
	public List<UserBoundary> getAllUsers(String userId);
	public void	deleteAllUsers(String userId);	
}

