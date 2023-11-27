package com.scotiaBank.ScotiaBank.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface AuditLog {
	
	 static final Logger LOG = LoggerFactory.getLogger(AuditLog.class);
	
	public static void auditLog(String username, String action , Long timeStamp, String comments, String input) {
		
		LOG.info("username="+username
				 + "  action="+action
				 + "  input="+input
				 + "  timeStamp="+timeStamp
				 + "  comments="+comments
				);
		
	}

}
