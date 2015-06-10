package com.adaptavant.gmail;

import java.util.List;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Inbox {

	@PrimaryKey
	@Persistent
	private String name="MailBox";
	@Persistent
	private List<Tab> listOfTabs;
	
//	public void setListOfTabs() {
//		this.listOfTabs = null;
//	}
	public void setListOfTabs(List<Tab> listOfTabs) {
		this.listOfTabs = listOfTabs;
	}

	public  List<Tab> getListOfTabs() {
		return listOfTabs;
	}

	public void addListOfTabs(Tab tab){
		this.listOfTabs.add(tab);
	}
	
	public void removeListOfTabs(int tabIndex){
		this.listOfTabs.remove(tabIndex);
	}
	
	public String getName(){
		return name;
	}
	
	
}
