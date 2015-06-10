package com.adaptavant.gmail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GmailController {

	PersistenceManager pm;
	private int tempVariable=0;
	
	@RequestMapping(value = "/")
	public String goToIndex(){
		if (tempVariable < 1) {
			tempVariable++;

				Inbox inbox = new Inbox();
				inbox.getName();
				inbox.setListOfTabs(null);
				pm = PersitenceManagerFactoryClass.get().getPersistenceManager();
				try {
					pm.makePersistent(inbox);
				} catch (Exception e) {
					System.out.println(e);
				} finally {
					pm.close();
				}
		}
		return "redirect:/inbox";
	}
	
	@RequestMapping(value = "/inbox")
	public String getIndex(Model model){
				
		try{
		pm = PersitenceManagerFactoryClass.get().getPersistenceManager();
		Inbox inbox = pm.getObjectById(Inbox.class,"MailBox");
		
			if (inbox.getListOfTabs().size() != 0) {
				
				// Listing Tabs on left_panel
				List<Tab> tabList = inbox.getListOfTabs();
				String tabListObject = "[ ";
				for (int i = 0; i < tabList.size(); i++) {
					tabListObject += "{ tabName:\""
							+ tabList.get(i).getTabName()
							+ "\", tabDescription:\""
							+ tabList.get(i).getTabDescription()
							+ "\", tabNumberOfMail:\""
							+ tabList.get(i).getNumberOfMails() + "\"},";
				}
				tabListObject = tabListObject.substring(0,
						tabListObject.length() - 1)
						+ "]";
				
				model.addAttribute("tabListObject", tabListObject);
				return "index.jsp";
			}else{
			model.addAttribute("errorMessage","Create a Tab");
			return "index.jsp";
		}
		}catch(Exception e){
			System.out.println(e);
			model.addAttribute("errorMessage","Error");
			return "index.jsp";
		}
		finally{
			pm.close();
		}
		
	}
	
	@RequestMapping(value= "/mailbox", method=RequestMethod.GET)
	public @ResponseBody String mailBox(@RequestParam int tabIndex){

		String tabMailObject = "[ ";
		try{
			pm = PersitenceManagerFactoryClass.get().getPersistenceManager();
			Inbox inbox = pm.getObjectById(Inbox.class,"MailBox");
			List<Mail> mailList = inbox.getListOfTabs().get(tabIndex).getMailList();
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY - hh:mm a");
			
			
			for (int i = 0; i < mailList.size(); i++) {
				tabMailObject += "{\"tabIndex\":\"" + tabIndex
						+ "\",\"belongsToTab\":\""
						+ mailList.get(i).getBelongsToTab()
						+ "\", \"mailFromName\":\""
						+ mailList.get(i).getMailFromName()
						+ "\", \"mailToName\":\""
						+ mailList.get(i).getMailToName()
						+ "\",\"mailSubject\":\""
						+ mailList.get(i).getMailSubject()
						+ "\", \"mailBody\":\"" + mailList.get(i).getMailBody()
						+ "\", \"starFlag\":\"" + mailList.get(i).getStarFlag()
						+ "\",\"readStatusFlag\":\""
						+ mailList.get(i).getReadStatusFlag()
						+ "\", \"mailTime\":\""
						+ dateFormat.format(mailList.get(i).getMailTime())
						+ "\"},";
			}
			tabMailObject = tabMailObject.substring(0,tabMailObject.length() - 1)+ "]";
		}catch(Exception e){
			
		}
		return tabMailObject;
	}
	
	@RequestMapping(value = "/compose")
	public String composeMail(){
		return "Compose.jsp";
	}
	
	@RequestMapping(value = "/createtab")
	public String createTab(){
		return "CreateTab.jsp";
	}
	
	
	@RequestMapping(value="/mailsent", method= RequestMethod.POST)
	public String mailSent(@ModelAttribute Mail mail,Model model){
		
		if(mail.getMailFromName().length()==0){
			model.addAttribute("errorMessage","Error: Name Field Cannot be Empty");
			return "Compose.jsp";
		}
		
		if(mail.getBelongsToTab().length()==0){
			model.addAttribute("errorMessage","Error: Tab Field Cannot be Empty");
			return "Compose.jsp";
		}

		
		String newLine = System.getProperty("line.separator");
		if(mail.getMailBody().contains(newLine)){
			mail.setMailBody(mail.getMailBody().replaceAll(newLine, " "));
		}
		
		boolean checker = false;
		pm = PersitenceManagerFactoryClass.get().getPersistenceManager();
		try{
			Inbox inbox = pm.getObjectById(Inbox.class,"MailBox");
			List<Tab> tempList = inbox.getListOfTabs();
			for(int i=0;i<tempList.size();i++){
				Tab tempTab = tempList.get(i);
				if(tempTab.getTabName().equals(mail.getBelongsToTab())){
					tempTab.addMailList(mail);
					tempTab.setNumberOfMails();
					checker = true;
				}
			}
			
			if(!checker){
				model.addAttribute("errorMessage","Error: "+mail.getBelongsToTab()+" does not exists.");
				return "Compose.jsp";
			}
			
		}catch(Exception e){
			model.addAttribute("errorMessage","Error: "+mail.getBelongsToTab()+" does not exists.");
			return "Compose.jsp";
		}finally{
			pm.close();
		}
		return "redirect:/inbox";
	}
	
	@RequestMapping(value="/tabcreated", method= RequestMethod.POST)
	public String tabCreated(@ModelAttribute Tab tab, Model model){

		if(tab.getTabName().length()==0){
			model.addAttribute("errorMessage","Error: Tab Field Cannot Be Empty");
			return "CreateTab.jsp";
		}

		String newLine = System.getProperty("line.separator");
		if(tab.getTabDescription().contains(newLine)){
			tab.setTabDescription(tab.getTabDescription().replaceAll(newLine, " "));
		}
		
		pm = PersitenceManagerFactoryClass.get().getPersistenceManager();
		try {
			Inbox inbox = pm.getObjectById(Inbox.class,"MailBox");
			List<Tab> tempList = inbox.getListOfTabs();
			List<String> tabNameList = new ArrayList<String>();
			if (tempList.size() != 0) {
				for (int i = 0; i < tempList.size(); i++) {
					tabNameList.add(tempList.get(i).getTabName());
				}
				if (tabNameList.contains(tab.getTabName())) {
					model.addAttribute("errorMessage","Error: Tab " + tab.getTabName() + " Exists");
					return "CreateTab.jsp";
				}else{
					inbox.addListOfTabs(tab);
				}
			}else{
				inbox.addListOfTabs(tab);
			}
		}catch(Exception e){
			model.addAttribute("errorMessage","Error: Tab Creation Failed");
			return "CreateTab.jsp";
		} finally {
			pm.close();
		}
		return "redirect:/inbox";
	}
	
	@RequestMapping(value="/openmail/{tabIndex}/{mailIndex}", method= RequestMethod.GET)
	public String openMail(@PathVariable int tabIndex,@PathVariable int mailIndex,Model model){
		try{
			pm = PersitenceManagerFactoryClass.get().getPersistenceManager();
			
			Inbox inbox = pm.getObjectById(Inbox.class,"MailBox");
			List<Tab> tabList = inbox.getListOfTabs();
			String tabNamesList="[";
			for(int i=0;i<tabList.size();i++){
				tabNamesList+= "\""+tabList.get(i).getTabName() +"\",";
			}
			tabNamesList = tabNamesList.substring(0,tabNamesList.length() - 1)+ "]";
			
			List<Mail> mailList = tabList.get(tabIndex).getMailList();
			mailList.get(mailIndex).setReadStatusFlag(true);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY hh:mm a");
			model.addAttribute("tabNamesList",tabNamesList);
			model.addAttribute("mailFromName", mailList.get(mailIndex).getMailFromName());
			model.addAttribute("mailToName", mailList.get(mailIndex).getMailToName());
			model.addAttribute("mailSubject", mailList.get(mailIndex).getMailSubject());
			model.addAttribute("mailBody", mailList.get(mailIndex).getMailBody());
			model.addAttribute("starFlag", mailList.get(mailIndex).getStarFlag());
			model.addAttribute("readStatusFlag", mailList.get(mailIndex).getReadStatusFlag());
			model.addAttribute("mailTime", dateFormat.format(mailList.get(mailIndex).getMailTime()));
			model.addAttribute("tabIndex",tabIndex );
			model.addAttribute("mailIndex",mailIndex);
			model.addAttribute("maxNumberOfMails",mailList.size());
			return "OpenMail.jsp";
		}catch(Exception e){
			System.out.println(e);
			model.addAttribute("Error",e);
			return "redirect:/inbox";
		}finally{
			pm.close();
		}
	}
	
	@RequestMapping(value="/startoggle", method= RequestMethod.GET)
	public @ResponseBody String starToggle(@RequestParam int tabIndex,@RequestParam int mailIndex){
		String starStatus;
		try{
			
			pm = PersitenceManagerFactoryClass.get().getPersistenceManager();

			Inbox inbox = pm.getObjectById(Inbox.class,"MailBox");
			List<Mail> mailList = inbox.getListOfTabs().get(tabIndex).getMailList();
			if(mailList.get(mailIndex).getStarFlag()){
				mailList.get(mailIndex).setStarFlag(false);
				starStatus = "false";
			}
			else{
				mailList.get(mailIndex).setStarFlag(true);
				starStatus="true";
			}
		}catch(Exception e){
			System.out.println(e);
			return null;
		}finally{
			pm.close();
		}
		
		return starStatus;
	}
	
	@RequestMapping(value="/markunread", method= RequestMethod.GET)
	public String markUnread(@RequestParam int tabIndex,@RequestParam int mailIndex,Model model){
		try{
			
			pm = PersitenceManagerFactoryClass.get().getPersistenceManager();
			
			Inbox inbox = pm.getObjectById(Inbox.class,"MailBox");
			List<Tab> tabList = inbox.getListOfTabs();
			List<Mail> mailList = tabList.get(tabIndex).getMailList();
			mailList.get(mailIndex).setReadStatusFlag(false);

		}catch(Exception e){
			System.out.println(e);
			return "redirect:/inbox";
		}finally{
			pm.close();
		}
		return "redirect:/inbox";
	}
	
	@RequestMapping(value="/deletemail", method= RequestMethod.GET)
	public String deleteMail(@RequestParam int tabIndex,@RequestParam int mailIndex,Model model){
		try{
			pm = PersitenceManagerFactoryClass.get().getPersistenceManager();
			
			Inbox inbox = pm.getObjectById(Inbox.class,"MailBox");
			Tab tab = inbox.getListOfTabs().get(tabIndex);
			Mail mail = inbox.getListOfTabs().get(tabIndex).getMailList().get(mailIndex);
			tab.removeMailList(mailIndex);
			tab.setNumberOfMails();
			pm.deletePersistent(mail);

		}catch(Exception e){
			System.out.println(e);
			return "redirect:/inbox";
		}finally{
			pm.close();
		}
		return "redirect:/inbox";
	}
	
	@RequestMapping(value="/deletetab", method=RequestMethod.GET)
	public String deleteTab(@RequestParam int tabIndex,Model model){
		
		try{

			pm = PersitenceManagerFactoryClass.get().getPersistenceManager();
			Inbox inbox = pm.getObjectById(Inbox.class,"MailBox");
			
			Tab tab = inbox.getListOfTabs().get(tabIndex);
			List<Mail> mailList = tab.getMailList();
			
			List<Mail> tempMailList = (List<Mail>) pm.detachCopyAll(mailList);
			
			int tempIndex;
			if(tabIndex!=0)
				tempIndex=0;
			else
				tempIndex=1;
			
			inbox.getListOfTabs().get(tempIndex).addGroupMailList(tempMailList);
			inbox.getListOfTabs().get(tempIndex).setNumberOfMails();
			
			inbox.removeListOfTabs(tabIndex);
			
			pm.deletePersistentAll(mailList);
			pm.deletePersistent(tab);
			

		}catch(Exception e){
			System.out.println(e);
			return "redirect:/inbox";
		}finally{
			pm.close();
		}
		return "redirect:/inbox";
	}
	
	@RequestMapping(value="/movemail", method=RequestMethod.GET)
	public String moveMail(@RequestParam int tabIndex,@RequestParam int mailIndex ,@RequestParam int moveToTabIndex,Model model){
		try{
			
			//Removing from old tab
			pm = PersitenceManagerFactoryClass.get().getPersistenceManager();
			Inbox inbox = pm.getObjectById(Inbox.class,"MailBox");
			Mail mail = inbox.getListOfTabs().get(tabIndex).getMailList().get(mailIndex);
			
			Mail copyMail = (Mail) pm.detachCopy(mail);
			
			inbox.getListOfTabs().get(tabIndex).removeMailList(mailIndex);
			inbox.getListOfTabs().get(tabIndex).setNumberOfMails();
			pm.deletePersistent(mail);
			
			//adding to new tab
			inbox.getListOfTabs().get(moveToTabIndex).addMailList(copyMail);
			inbox.getListOfTabs().get(moveToTabIndex).setNumberOfMails();
		}catch(Exception e){
			System.out.println(e);
			return "redirect:/inbox";
		}finally{
			pm.close();
		}
		return "redirect:/inbox";
	}
	
	
	@RequestMapping(value= "/search", method=RequestMethod.GET)
	public @ResponseBody String searchItem(@RequestParam String searchText){
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY - hh:mm a");
		String tabMailObject = "[ ";
		try{
			pm = PersitenceManagerFactoryClass.get().getPersistenceManager();
			Inbox inbox = pm.getObjectById(Inbox.class, "MailBox");
			List<Tab> listOfTabs = inbox.getListOfTabs();
					
			for (int tabIndex = 0; tabIndex < listOfTabs.size(); tabIndex++) {
				List<Mail> listOfMails = listOfTabs.get(tabIndex).getMailList();
				for (int mailIndex = 0; mailIndex < listOfMails.size(); mailIndex++) {
					Mail mail = listOfMails.get(mailIndex);
					if (mail.getMailFromName().toLowerCase().contains(searchText.toLowerCase()) || mail.getMailSubject().toLowerCase().contains(searchText.toLowerCase()) || mail.getMailBody().toLowerCase().contains(searchText.toLowerCase())) {
						tabMailObject+="{\"tabIndex\":\"" + tabIndex
								+"\",\"mailIndex\":\"" + mailIndex
								+ "\",\"belongsToTab\":\""
								+ mail.getBelongsToTab()
								+ "\", \"mailFromName\":\""
								+ mail.getMailFromName()
								+ "\", \"mailToName\":\""
								+ mail.getMailToName()
								+ "\",\"mailSubject\":\""
								+ mail.getMailSubject()
								+ "\", \"mailBody\":\"" + mail.getMailBody()
								+ "\", \"starFlag\":\"" + mail.getStarFlag()
								+ "\",\"readStatusFlag\":\""
								+ mail.getReadStatusFlag()
								+ "\", \"mailTime\":\""
								+ dateFormat.format(mail.getMailTime())
								+ "\"},";
					}
				}
			}

		tabMailObject = tabMailObject.substring(0,tabMailObject.length() - 1)+ "]";
		}catch(Exception e){
			
		}
		return tabMailObject;
	}
}
