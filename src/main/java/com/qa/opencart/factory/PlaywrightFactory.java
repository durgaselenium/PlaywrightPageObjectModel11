package com.qa.opencart.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightFactory {
 Playwright playwright;
 Browser browser;
 BrowserContext browserContext;
 Page page;
 Properties prop;
 
 private static ThreadLocal<Browser>tlBrowser = new ThreadLocal<>();
 private static ThreadLocal<BrowserContext>tlBrowserContext = new ThreadLocal<>();
 private static ThreadLocal<Page>tlPage = new ThreadLocal<>();
 private static ThreadLocal<Playwright>tlPlaywright = new ThreadLocal<>();
	
 public static Playwright getPlaywright() {
	 return tlPlaywright.get();
 }
 public static Browser getBrowser() {
	 return tlBrowser.get();
 }
 public static BrowserContext getBrowserContext() {
	 return tlBrowserContext.get();
 }
 public static Page getPage() {
	 return tlPage.get();
 }
 
public Page initBrowser(Properties prop) {
String browserName = prop.getProperty("browser").trim();
System.out.println("browser name is : "+ browserName);

//  playwright = Playwright.create();
  tlPlaywright.set(Playwright.create());
 
 switch (browserName.toLowerCase()) {
case "chromium":
//	browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
	tlBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)));
	break;
	
case "firefox":
//	browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
	tlBrowser.set(getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(false)));
	break;
	
case "safari":
//	browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
  tlBrowser.set(getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(false)));
	break;
	
case "chrome":
//	browser = playwright.chromium().launch(new LaunchOptions().setChannel("chrome").setHeadless(false));
	tlBrowser.set(getPlaywright().chromium().launch(new LaunchOptions().setChannel("msedge").setHeadless(false)));
	break;
	
case "edge":
   tlBrowser.set(getPlaywright().chromium().launch(new LaunchOptions().setChannel("chrome").setHeadless(false)));
	break;
default:
	System.out.println("please Pass the right browser Name .......");
	break;
} 
//  browserContext = browser.newContext();
 // page = browserContext.newPage();
//  page.navigate("https://naveenautomationlabs.com/opencart/");
 // page.navigate(prop.getProperty("url").trim());
//return page;
 
 
 tlBrowserContext.set(getBrowser().newContext());
 tlPage.set(getBrowserContext().newPage()); 

 getPage().navigate(prop.getProperty("url").trim());
 return getPage();
 
}	
	
/**
 * this method is used to initialize the properties from config file
 * @return 
 */
public Properties init_prop() {

try {
FileInputStream	fis = new FileInputStream("./src/test/resources/config/config.properties");
	prop = new Properties();
	prop.load(fis);
} catch (FileNotFoundException e) {
	e.printStackTrace();
} catch (IOException e) {
	e.printStackTrace();
}
return prop;

}

/**
 * take screenshot
 */
 public static String takescreenshot() {
  String path =	 System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
  getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
  return path;
 }

	
}
