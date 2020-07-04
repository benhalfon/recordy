package recordy.data.convertors;

import org.springframework.stereotype.Component;

import recordy.data.ReportEntity;
import recordy.data.UserEntity;
import recordy.reportBoundaries.ReportBoundary;
import recordy.userBoundaries.UserBoundary;

@Component
public class ReportConvertor {
	public ReportBoundary toBoundary(ReportEntity entity) {
		ReportBoundary rb = new ReportBoundary();
	
		return rb;
	}
}
