package com.omdasoft.money4group.db;

public class UserModel {

	private long id = -1;
	private String userName;
	private String contact; // from phone contact book,maybe an id or serialized object
	private long createdAt; 
	private String createdBy; 
	private long modifiedAt; 
	private String modifiedBy;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public long getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(long caretedAt) {
		this.createdAt = caretedAt;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String caretedBy) {
		this.createdBy = caretedBy;
	}
	public long getModifiedAt() {
		return modifiedAt;
	}
	public void setModifiedAt(long modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
}
