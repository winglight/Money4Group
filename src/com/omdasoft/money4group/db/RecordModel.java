package com.omdasoft.money4group.db;

public class RecordModel {

	private long id = -1;
	private long actId = -1; // the activity as owner
	private String recordDesc; // null is permitted
	private double amount; // accuracy to 2 bit after dot
	private long spentOn; // spent date1
	private String paidBy; //user's name
	private String consumedBy; // array of users' names
	private String tag; //only support one tag by now
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
	public long getActId() {
		return actId;
	}
	public void setActId(long actId) {
		this.actId = actId;
	}
	public String getRecordDesc() {
		return recordDesc;
	}
	public void setRecordDesc(String recordDesc) {
		this.recordDesc = recordDesc;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public long getSpentOn() {
		return spentOn;
	}
	public void setSpentOn(long spentOn) {
		this.spentOn = spentOn;
	}
	public String getPaidBy() {
		return paidBy;
	}
	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}
	public String getConsumedBy() {
		return consumedBy;
	}
	public void setConsumedBy(String consumedBy) {
		this.consumedBy = consumedBy;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
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
