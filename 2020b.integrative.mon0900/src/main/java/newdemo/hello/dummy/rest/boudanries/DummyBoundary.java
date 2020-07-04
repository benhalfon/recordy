package newdemo.hello.dummy.rest.boudanries;

import java.util.Date;
import java.util.Map;

public class DummyBoundary {
	private String id;
	private String message;
	private Boolean isDeleted;
	private Date timestamp;
	private Map<String, Object> details;
	private Type type;
	private NameBoundary name;
	
	public DummyBoundary() {
		this.name = new NameBoundary("dummy", "user");
	}

	public DummyBoundary(String id, String message) {
		this();
		this.id = id;
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Map<String, Object> getDetails() {
		return details;
	}

	public void setDetails(Map<String, Object> details) {
		this.details = details;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public NameBoundary getName() {
		return name;
	}
	
	public void setName(NameBoundary name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "DummyBoundary [id=" + id + ", message=" + message + ", isDeleted=" + isDeleted + ", timestamp="
				+ timestamp + ", details=" + details + ", type=" + type + ", name=" + name + "]";
	}

	
	
}
