package newdemo.hello.dummy.data;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import newdemo.hello.dummy.dal.MapToJsonConverter;


@Entity
@Table(name="DUMMIES")
public class DummyEntity { 
	private String id;
	private String message;
	private boolean isDeleted;
	private Date timestamp;
	private Map<String, Object> details;
	private TypeEntityEnum type;
	private NameEntity name;
	private Set<DummyEntity> responses;
	private DummyEntity origin;

	public DummyEntity() {
		this.responses = new HashSet<>();
	}

	public DummyEntity(String id, String message) {
		this();
		this.id = id;
		this.message = message;
	}

	@Id
//	@Column(name="THE_ID", length = 20)
	public String getId() { // VARCHAR(255)
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

	public boolean getDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Convert(converter = MapToJsonConverter.class)
	@Lob
	public Map<String, Object> getDetails() {
		return details;
	}

	public void setDetails(Map<String, Object> details) {
		this.details = details;
	}

	@Enumerated(EnumType.STRING)
	public TypeEntityEnum getType() {
		return type;
	}

	public void setType(TypeEntityEnum type) {
		this.type = type;
	}

	@Embedded
	public NameEntity getName() {
		return name;
	}
	
	public void setName(NameEntity name) {
		this.name = name;
	}
	
	@Transient
	public String getDummyDetails() {
		return "details of dummy: " +this.message;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public DummyEntity getOrigin() {
		return origin;
	}
	
	public void setOrigin(DummyEntity origin) {
		this.origin = origin;
	}
	
	@OneToMany(mappedBy = "origin", fetch = FetchType.LAZY)
	public Set<DummyEntity> getResponses() {
		return responses;
	}
	
	public void setResponses(Set<DummyEntity> responses) {
		this.responses = responses;
	}
	
	public void addResponse (DummyEntity response) {
		this.responses.add(response);
		response.setOrigin(this);
	}
}
