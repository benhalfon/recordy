package newdemo.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAspect {
	
	private Log logger = LogFactory.getLog(LoggerAspect.class);
	
	@Before("@annotation(newdemo.aop.MyLogger)")
	public void logAdvice(JoinPoint jp){
		String methodName = jp.getSignature().getName();
		String targetClassName = jp.getTarget().getClass()
			.getName(); // java reflection
		this.logger.trace("***********" + targetClassName + "." + methodName + "()");
	}
	
	// TODO enhance aspect to validate method final operation:
	// 1. ended successfully
	// 2. ended with exception 
}







