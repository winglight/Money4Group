package com.omdasoft.money4group.db;

public class ActivityModel {

	private long id = -1;
	private String actName;
	private int actType; // 1 - long term ; 2 - short term ; 3 - long term and repeat
	private long startAt;
	private long endAt=-1; //if no input, let it be -1
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
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public int getActType() {
		return actType;
	}
	public void setActType(int actType) {
		this.actType = actType;
	}
	public long getStartAt() {
		return startAt;
	}
	public void setStartAt(long startAt) {
		this.startAt = startAt;
	}
	public long getEndAt() {
		return endAt;
	}
	public void setEndAt(long endAt) {
		this.endAt = endAt;
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
	public void setCreastedBy(String caretedBy) {
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
