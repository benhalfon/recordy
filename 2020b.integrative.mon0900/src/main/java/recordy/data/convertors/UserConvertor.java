package recordy.data.convertors;

import org.springframework.stereotype.Component;

import recordy.data.UserEntity;
import recordy.data.UserRoles;
import recordy.userBoundaries.UserBoundary;
@Component
public class UserConvertor {
	public UserBoundary toBoundary(UserEntity entity) {
		UserBoundary ub = new UserBoundary();
		ub.setUserId(entity.getUserId());
		if (entity.getRole() != null) {
			ub.setRole(entityToBoundaryRole(entity.getRole()));
		}
		ub.setUserName(entity.getUserName());
		return ub;
	}
	
	public UserEntity toEntity(UserBoundary boundary) {
		UserEntity ue = new UserEntity();
		ue.setUserId(boundary.getUserId());
		if (boundary.getRole() != null) {
			ue.setRole(boundaryToEntityRole(boundary.getRole()));
		}
		ue.setUserName(boundary.getUserName());
		return ue;
	}
	
	public UserRoles boundaryToEntityRole(String role) {
		return UserRoles.valueOf(role);
	}
	public String entityToBoundaryRole(UserRoles role) {
		return role.toString();
	}

}
