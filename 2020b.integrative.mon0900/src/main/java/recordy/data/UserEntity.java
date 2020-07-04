package recordy.data;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class UserEntity {
	
	@Id
	private String userId;
	
	@Column(nullable = false)
	private String userName;
	
	@Enumerated(EnumType.STRING)
	private UserRoles role;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Column(nullable = false)
	private Boolean isActive;
	
	public UserEntity() {
		super();
	}
	public UserEntity(String userId, String userName, UserRoles role, Date createdDate,Boolean isActive) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.role = role;
		this.createdDate = createdDate;
		this.isActive = isActive;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public UserRoles getRole() {
		return role;
	}
	public void setRole(UserRoles role) {
		this.role = role;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	
	
	
	
}
