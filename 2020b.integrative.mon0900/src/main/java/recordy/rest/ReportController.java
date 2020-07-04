package recordy.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import recordy.MessageBoundary;
import recordy.NameNotFoundException;
import recordy.reportBoundaries.NewReportBoundary;
import recordy.reportBoundaries.ReportBoundary;
import recordy.reportLogic.ReportService;
import recordy.userBoundaries.NewUserDetails;
import recordy.userBoundaries.UserBoundary;
import recordy.userLogic.UserService;
import utils.StringUtils;

@RestController
public class ReportController {
	ReportService reportService;
	
	@Autowired
	public ReportController() {
	}
	
	public ReportController(ReportService reportService) {
		super();
		this.reportService = reportService;
	}
	
	@Autowired
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
	

	@RequestMapping(path="/recordy/report/{userId}",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ReportBoundary createNewReport(@RequestBody  NewReportBoundary report,
			@PathVariable("userId") String userId) {
		ReportBoundary rb = new ReportBoundary();
		rb.setPriority(report.getPriority());
		rb.setSeverity(report.getSeverity());
		rb.setDescription(report.getDescription());
		rb.setProject(report.getProject());
		rb.setMainSubject(report.getMainSubject());
		rb.setSubSubject(report.getSubSubject());
		rb.setTags(report.getTags());
		rb.setCreatedDate(new Date());
		rb.setCreatorId(userId);
		rb.setTags(report.getTags());
		rb.setTitle(report.getTitle());
		rb.setReportAttributes(new HashMap<String,Object>() {{
			put("users",new ArrayList<String>() {{add(userId);}});
			put("updateds",new HashMap<Date,String>());
		}});
		return reportService.createReport(rb);
	}


	
	
}
