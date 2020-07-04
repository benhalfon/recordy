package recordy.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import recordy.MessageBoundary;
import recordy.NameNotFoundException;
import recordy.userBoundaries.NewUserDetails;
import recordy.userBoundaries.UserBoundary;
import recordy.userLogic.UserService;
import utils.StringUtils;

@RestController
public class UserController {
	UserService userService;
	
	@Autowired
	public UserController() {
	}
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(path = "/recordy/{name}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public MessageBoundary helloUser1 (@PathVariable("name") String name) {
		if (name != null && !name.trim().isEmpty()) {
			return new MessageBoundary("Hello " + name + "!");
		}else {
			throw new NameNotFoundException("Invalid name");
		}
	}
	

	@RequestMapping(path="/recordy/users",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary createNewUser(@RequestBody  NewUserDetails user) {
		UserBoundary ub = new UserBoundary();
		ub.setUserName(user.getUserName());
		ub.setRole(user.getRole());
		ub.setUserId(user.getUserId());
		
		return userService.createUser(ub);
	}
	
	@RequestMapping(path="/recordy/users/login/{userId}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary login(@PathVariable("userId") String userId) {
		if(StringUtils.isNullOrEmpty(userId))
		{
			throw new RuntimeException("userId null/empty");
		}
		return this.userService.login(userId);
	}
	
	@RequestMapping(path="/recordy/users/{userId}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser(@RequestBody  UserBoundary user, 
			@PathVariable("userId") String userId) {
		if(StringUtils.isNullOrEmpty(userId))
		{
			throw new RuntimeException("domain or email null/empty");
		}
		this.userService.updateUser(userId, user);
	}

	
	
}
