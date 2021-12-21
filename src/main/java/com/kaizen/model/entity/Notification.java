package com.kaizen.model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Notification Entity for Notification System
 * NOTE: ONLY TO BE IMPLEMENTED WHEN EVERYTHING IS DONE!
 * Notifications are company-based — it is shared among staff in same company.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-18
 */

@Entity
public class Notification {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer notificationId;
	
	private String message;
	
	private Date createdAt;
	
	private boolean isRead;
	
	@ManyToOne
	private Company company;
	
	public Notification(){}
	
	public Notification(String message,Date createdAt,Company company){
		this.message = message;
		this.createdAt = createdAt;
		this.company = company;
		this.isRead = false;
	}


	public Integer getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

}