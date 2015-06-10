package com.adaptavant.gmail;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class Mail{
	
	@Persistent
	private String belongsToTab;
	@Persistent
	private String mailFromName;
	@Persistent
	private String mailToName;
	@Persistent
	private String mailSubject;
	@Persistent
	private String mailBody;
	@Persistent
	private boolean starFlag;
	@Persistent
	private boolean readStatusFlag;
	@Persistent
	private long mailTime =System.currentTimeMillis();
	
 	
	public String getBelongsToTab() {
		return belongsToTab;
	}
	public void setBelongsToTab(String belongsToTab) {
		this.belongsToTab = belongsToTab;
	}
	public String getMailFromName() {
		return mailFromName;
	}
	public void setMailFromName(String mailFromName) {
		this.mailFromName = mailFromName;
	}
	public String getMailToName() {
		return mailToName;
	}
	public void setMailToName(String mailToName) {
		this.mailToName = mailToName;
	}
	
	public long getMailTime() {
		return mailTime;
	}

	public String getMailSubject() {
		return mailSubject;
	}
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}
	public String getMailBody() {
		return mailBody;
	}
	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}
	
	public void setStarFlag(boolean starFlag) {
		this.starFlag = starFlag;
	}
	public void setReadStatusFlag(boolean readStatusFlag) {
		this.readStatusFlag = readStatusFlag;
	}
	public boolean getStarFlag() {
		return starFlag;
	}

	public boolean getReadStatusFlag() {
		return readStatusFlag;
	}
	
	
}
