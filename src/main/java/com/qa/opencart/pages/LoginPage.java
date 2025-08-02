package com.qa.opencart.pages;

import com.microsoft.playwright.Page;

public class LoginPage {

 private Page page;

//1. String locators ---OR
 private String emailId   = "//input[@name='email']";
 private String password  = "//input[@name='password']";
 private String loginBtn  = "//input[@value='Login']";
 private String forgetpwdLink = "(//a[normalize-space()='Forgotten Password'])[1]2222222"; //wrong xpath
 private String logOutLnk     = "//div[@class='list-group']/a[13]";
 
//2. page constructor:
	public LoginPage(Page page) {
		this.page = page;
}
	
//3. page actions/methods:	
public String getLoginPageTitle() {
	String title = page.title();
	System.out.println("loginpage title : " + title);
	return title;
}

public boolean isForgetPwdLinkExist() {
 return page.isVisible(forgetpwdLink);
}

public boolean doLogin(String appUserName,String appPassword) {
System.out.println("App Creds :" + appUserName + ": " + appPassword);
 page.fill(emailId, appUserName);
 page.fill(password, appPassword);
 page.click(loginBtn);
 
 if (page.isVisible(logOutLnk)) {
	System.out.println("user is logged is successfully .......");
	return true;
}
 return false;
}
		
}
