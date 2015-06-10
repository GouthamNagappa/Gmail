package com.adaptavant.gmail;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Tab{

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	@Persistent
	private String tabName;
	@Persistent
	private int numberOfMails;
	@Persistent
	private String tabDescription;
    @Persistent
	private List<Mail> mailList;
	
	public void addMailList(Mail mail){
		this.mailList.add(0,mail);
	}
	
	public void removeMailList(int mailIndex){
		this.mailList.remove(mailIndex);
	}

	public List<Mail> getMailList() {
		return mailList;
	}

	public void setMailList(List<Mail> mailList) {
		this.mailList = mailList;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName.toLowerCase();
	}

	public int getNumberOfMails() {
		return numberOfMails;
	}

	public void setNumberOfMails() {
		this.numberOfMails = mailList.size();
	}

	public String getTabDescription() {
		return tabDescription;
	}

	public void setTabDescription(String tabDescription) {
		this.tabDescription = tabDescription;
	}

	public void addGroupMailList(List<Mail> tempMailList) {
		this.mailList.addAll(tempMailList);
		
	}

}

