	package com.blog.services;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * This class stores error and info messages in a list in the httpsession.
 * 
 */
@Service
public class NotificationServiceImpl implements NotificationService {

	public static final String NOTIFY_MSG_SESSION_KEY = "siteNotificationMessages";
	
	@Autowired
	private HttpSession httpSession;
	
	@Override
	public void addInfoMessage(String msg) {
		addNotificationMessage(NotificationMessageType.INFO, msg);
	}

	@Override
	public void addErrorMessafe(String msg) {
		addNotificationMessage(NotificationMessageType.ERROR, msg);
	}
	
	@SuppressWarnings("unchecked")
	private void addNotificationMessage(NotificationMessageType type, String text) {
		List<NotificationMessage> messages = (List<NotificationMessage>) httpSession.getAttribute(NOTIFY_MSG_SESSION_KEY);	
		if(messages == null) {
			messages = new ArrayList<NotificationMessage>();
		}
		messages.add(new NotificationMessage(type, text));
		httpSession.setAttribute(NOTIFY_MSG_SESSION_KEY, messages);
	}
	
	public enum NotificationMessageType {
		INFO, ERROR
	}
	
	public class NotificationMessage {
		NotificationMessageType type;
		String text;
		
		public NotificationMessage(NotificationMessageType type, String text) {
			this.type = type;
			this.text = text;
		}

		public NotificationMessageType getType() {
			return type;
		}

		public String getText() {
			return text;
		}
		
	}
	
}
