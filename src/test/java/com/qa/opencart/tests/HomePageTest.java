package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class HomePageTest extends BaseTest {

	@Test
	public void homepageTitleTest() {
		String actualTitle = homepage.getHomePageTitle();
		Assert.assertEquals(actualTitle, AppConstants.HOME_PAGE_TITLE);
	}

	@Test
	public void homepageURLTest() {
		String actualURL = homepage.getHomePageURL();
		// Assert.assertEquals(actualURL, "https://naveenautomationlabs.com/opencart/");
		Assert.assertEquals(actualURL, prop.getProperty("url"));
	}

	@DataProvider
	public Object[][] getDataProductData() {
		return new Object[][] { 
			{"Macbook"}, 
			{"iMac"},
			{"Samsung"} 
			};
	}

	@Test(dataProvider = "getDataProductData")
	public void searchTest(String productName) {
// String actualSearchHeader = homepage.doSearch("Macbook");
		String actualSearchHeader = homepage.doSearch(productName);
		// Assert.assertEquals(actualSearchHeader, "Search - " + productName);Search -
		// Macbook
		Assert.assertEquals(actualSearchHeader, "Search - " + productName);

	}
}
