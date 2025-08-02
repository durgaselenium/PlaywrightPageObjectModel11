package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class LoginPageTest extends BaseTest{

@Test(priority = 1)	
public void loginPageNavigationTest() {
loginpage = homepage.navigateToLoginPage();
String actualtitle = loginpage.getLoginPageTitle();
Assert.assertEquals(actualtitle, AppConstants.LOGIN_PAGE_TITLE);
}	

@Test(priority = 2)
public void forgotPwdLinkExistTest() {
Assert.assertTrue(loginpage.isForgetPwdLinkExist());
}

@Test(priority = 3)
public void appLoginTest() {
 Assert.assertTrue(loginpage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim()));	
}

}
