package recordy.reportBoundaries;

import java.util.Collection;

public class NewReportBoundary {
	private String title;
	private String project;
	private String mainSubject;
	private String subSubject;
	private String description;
	private String creatorId;
	private Collection<String> tags;
	private String severity;
	private String priority;
	public NewReportBoundary(String title, String project, String mainSubject, String subSubject, String description,
			String creatorId, Collection<String> tags, String severity, String priority) {
		super();
		this.title = title;
		this.project = project;
		this.mainSubject = mainSubject;
		this.subSubject = subSubject;
		this.description = description;
		this.creatorId = creatorId;
		this.tags = tags;
		this.severity = severity;
		this.priority = priority;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getMainSubject() {
		return mainSubject;
	}
	public void setMainSubject(String mainSubject) {
		this.mainSubject = mainSubject;
	}
	public String getSubSubject() {
		return subSubject;
	}
	public void setSubSubject(String subSubject) {
		this.subSubject = subSubject;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public Collection<String> getTags() {
		return tags;
	}
	public void setTags(Collection<String> tags) {
		this.tags = tags;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
}
